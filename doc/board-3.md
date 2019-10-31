# Step-03 게시판 Controller 만들기 : 표현영역 효율적으로 관리하기

# 표현 영역

표현 영역 -> 응용 영역(서비스) -> 도메인 -> 인프라스트럭쳐 영역으로 시작합니다.
표현 영역은 클라이언트에 요청을 받아 알맞은 응용서비스를 호출한 후 결과값을 클라이언트에게 보내주는 역할을 합니다. 표현 영역은 어플리케이션 시작인 만큼 아주 중요한 역할을 하는 영역이라고 생각합니다. 단순 컨트롤러로써 Request Data 값을 받아서 대충 서비스로 던지는 역할이라고 생각할 수도 있습니다.

하지만 표현영역에서 Request Data 값을 받을 때 아주 잘 받아야 합니다. 대충 받는 순간 그 뒤쪽 서비스 영역, 도메인 영역, 인프라스트럭쳐 영역이 힘들어지기 때문입니다.
특히 서비스 영역이 어려워집니다. 

* Request 값을 DTO로 명시적으로 받아서 응용 영역으로 넘깁니다.
* 클라이언트로 부터 넘어오는 데이터 validate 확실하게 하기
* 표현영역에서 사용되는 객체들을 Service에 넘기지 않기(ex : HttpServletRequest)
* 도메인 객체 바로 프론트엔드로 넘기지 않기

# 1. Request Data값 DTO로 명시적으로 받아서 응용 영역으로 넘기기
먼저 Request Data값을 명시적으로 받아야합니다. 이 문제는 같이 협업하는 사람뿐만 아니라 나중에 유지보수할때도 서비스 영역에서 힘들어 집니다. 컨트롤러에서 HttpServletRequest 값을 통해서 Request Body 값을 받거나, Map을 이용하거나,
RequestParam으로 모든 데이터를 받아서 처리하게 되면 그당시 소스를 코딩할  때는 기억하지만 금방 그 데이터들이 어떤 용도에 데이터들인지 잊어버리기 때문입니다.

```java
//Map으로 받는예제
 public Map<String, Object> postBoard(@RequestBody Map<String, Object> params){
     
     //something
 }
```


예를 들어서 다른 개발자가 이것을 유지 보수한다고 가정하면, 유지 보수하는 사람은 그 Map 객체가 어떻게 사용되는지 모르기 때문에 하나하나의 키값 등을 추적해야 이 Map 객체의 Request 데이터를 명세할 수 있습니다.

심지어 클라이언트로부터 넘겨받은 값을 Map을 이용하여 아래 코드와 같이 키 값이 state인 value를 꺼낸다고 해보겠습니다. 클라이언트에 요구 사항으로 인해 이 키값이 status로 바뀌었습니다. 그렇게 되면 서비스 단과 컨트롤러에서 2군 대 이상 쓰인다고 하면 이것을 하나하나 다 찾아서 수정해야 합니다. 이것은 type형이 아닌 String 값이기 때문에 추적하기도 힘듭니다. 검색해서 찾을 수도 있지만 프로젝트 내에 같은 단어가 있으면 이 또한 매우 까다롭게 됩니다.

```java
request.getParameter("state");
```

그렇기 때문에 아래와 같이 명시적으로 DTO를 만들고 데이터를 받는게 좋습니다. 이렇게 명시적으로 정의하게 되면 유지 보수 하는 사람 입장에서도 게시글을 등록하기 위해서 이러한 값들은 필수고 이러한 값은 선택인 것을 알 수 있습니다. 또한 요구 사항 등 변경에 의해 데이터가 추가되거나 하더라도 유연하게 변경할 수 있습니다. 위에서 말한 Request Body key 값이 변경된다고 해도 해당 키값이 변수이기 때문에 IDE를 이용해 전체 변경을 하면 되기 때문에 아주 쉽게 변경할 수 있습니다.

다음은 게시글을 등록하기 위한 BoardForm을 정의합니다.

```java
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "member")
public class BoardForm {

    private Long id;

    private String writer;

    @NotBlank(message = "제목을 넣으셔야 합니다.")
    private  String title;

    private  String content;

    private Member member;


    public Board toEntity(){
        Board board = Board.createBoard(this.writer ,this.title, this.content, this.member);
        return board;
    }

}
```
```java
@Getter
@Entity
@Setter
@ToString(exclude = "board")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    private String content;

    private String replier;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updateAt;

}
```


댓글 같은 경우에는 DTO로 Request Data를 받을지 도메인으로 값을 받을지 고민하다가 굳이 화면단에서 보여줄 데이터와 크게 다르지 않아서 도메인으로 값을 받기로 결정했습니다. 사실 위의 게시판 또한 크게 다르지 않아서 도메인으로 받으려고 했지만 유지 보수성이 좋은 코드를 작성하기 위해서 요청 데이터를 받은 DTO, 데이터베이스에 저장하기 위한 도메인모델, 화면단에 보여줄 Reponse 객체 등 총 3개의 영역으로 나누었습니다. 



 
# 2. 클라이언트로 부터 넘어오는 데이너 validate 확실하게 하기 
다음은 클라이언트로부터 넘어오는 값에 대해 필수 값과 값의 형태를 표현 영역에서 확실하게 validate를 진행해야 표현 영역 이후에 영역들이 안 힘들어 집니다. 
만약 표현 영역에서 필수 값, 값의 형식에 대한 validate 검사들이 제대로 되지 않는다면 서비스 영역과 도메인 영역은 계속해서 비즈니스 로직을 구현하는 도중에 NULL 체크를 하는 소스를 작성해야 합니다. 이렇게 되면 비즈니스 로직을 집중할 수 없게 되고 코드의 가독성이 떨어지게 되어 버그로 이어질 가능성이 커집니다.

```java
public void postBoard(final BoardForm boardForm){
    if(boardForm == null)
        throw new NullPointException("boardForm is null!");
}
```

물론 응용서비스를 실행하는 주체가 같은 응용서비스이거나, 파라미터로 전달받은 값이 응용영역에서 validate 처리를 해야 할 수도 있습니다. 하지만 이 문제도 처음 표현 영역에서부터 무결한 데이터를 받거나, 서비스 자체에서도 NULL을 지양하는 코드를 작성해나간다면 응용서비스 내에 validate 코드 자체를 많이 줄여 나가실 수 있습니다.

이러한 무결한 데이터를 보장하기 위해 표현영역에서부터 NULL이 들어오는 것을 방어해야 합니다. NULL을 방어하기 위해서 다음과 같이 Request Data에 대한 Validate 어노테이션을 사용하는 방법이 있습니다. @NotBlank라는 어노테이션을 사용하게 되면 NULL, "", 빈칸 값들이 서비스 영역으로 들어오게 하는 것을 막을 수 있습니다.

저 같은 경우에는 BindingResult 객체를 사용하여 클라이언트로부터 요청데이터를 받게 될때 스프링에서 @valid 어노테이션을 사용하여 해당 요청 데이터를 스프링이 검증을 하게 됩니다. 

```java
    @NotBlank(message = "제목을 넣으셔야 합니다.")
    private  String title;
```

```java
    @PostMapping
    public ResponseEntity<?> postBoard(@RequestBody @Valid final BoardForm boardForm, final BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return new ResponseEntity<>("제대로 입력하세요", HttpStatus.BAD_REQUEST);
        }

        Board board = boardForm.toEntity();
        boardService.save(board);

        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }
```

위 NotBlank 어노테이션을 사용하게 되면 invalid 한 값이 넘어오면 프론트엔드로 BadRequest(400)을 처리하게 됩니다.
이때 bindingResult 객체를 프론트엔드 쪽으로 함께보내주는데 이것을 세부적으로 처리 할 수 있도록 Error code를 함께 보내는 게 좋습니다.

만약 Request Data를 정상적으로 프론트엔드 쪽에서 넘어온다면 응답코드를 201로 보내 줍니다.


# 3. Domain 객체를 바로 프론트엔드로 넘기지 않고 DTO로 사용하기
도메인을 바로 클라이언트 쪽으로 넘기게 되면 다음과 같은 문제점들을 가집니다.

* 도메인의 중요한 데이터가 외부에 노출되어 질 수 있습니다.

현재는 간단하게 누구나 회원가입 없이 게시판을 등록하여 댓글을 작성할 수 있는 커뮤니티이지만 만약 회원가입을 통해서 글을 남기도록 한다고 가정한다면, 도메인 객체 자체를 리턴하게 된다면 회원의 개인정보, 패스워드 등이 로그, 클라이언트에 노출이 되어집니다. 물론 어노테이션을 통해서 중요한 필드가 노출되는것을 막을 수 있습니다. 하지만 어노테이션은 실수할 확률이 큽니다. 실수로 어노테이션을 삭제하게 되면 이 문제에 대해 쉽게 인지할 수 없기 때문입니다.

반면 DTO를 사용하게 되면 현저히 실수할 확률이 줄어들게 됩니다. 그 이유는 어노테이션의 방법은 DTO 방법과는 반대로 클라이언트에 표시되지 않기 위한 데이터에 대해 어노테이션을 표시하는 반면 DTO는 필요로 하는 데이터를 필드에 추가해야 하기 때문에 실수를 할 확률이 줄어들게 됩니다. 예를 들어 화면에 보여줄 데이터가 안 보이는 것은 DTO에 추가를 안 한 것이기 때문에 쉽게 에러를 잡을 수 있습니다. 또 한 화면에 보여야 할 데이터가 안보인다는 것은 대부분 Test Code 등으로 잡히는 문제입니다.


* 댓글 정보를 리턴시 무한순환참조 Exception을 발생하게 됩니다.

```java
@Getter
@Entity
@Setter
@ToString(exclude = "board")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    private String content;

    private String replier;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updateAt;
}
```

위의 코드를 살펴보면 Rest API를 통해 클라이언트(웹브라우저)가 게시글의 댓글을 조회하는 기능이 있습니다. 이 Reply 엔티티를 클라이언트로 넘겨주기 위해서는 JSON 포맷으로 직렬화가 되어야 합니다. 문제는 Board 엔티티와 서로 다른 다대일 양방향 관계를 가지고 있기 때문에 서로 계속 참조하게 되어 무한 순환 참조 Exception이 발생하게 됩니다. 물론 이러한 문제를 어노테이션등을 통해 방지 할 수 도 있습니다. 


저 같은 경우에는 Reply 엔티티 같은 경우에 직렬화를 할때  Board 엔티티를  @JsonIgnore 어노테이션을 이용하여 JSON 포맷에서 빼도록 하였습니다. 하지만 이것도 좋은 방법은 아닙니다. 이유는 도메인은 하나인데 그 도메인 하나로 필드를 제어하려고 했기 때문입니다.
 
그렇기 때문에 실무에서는 더욱 복잡한 엔티티 연관관계를 가지고 있다면 DTO를 사용하는 것을 추천드립니다.


다음은 게시판에 게시글을 등록 후 Board 객체를 리턴하기 위해 사용되는 DTO입니다.

```java
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardResponse {


    private Long id;

    private String writer;

    private String title;

    private String content;

    private Member member;

    private List<Reply> replies = new ArrayList<Reply>();

    private Timestamp createdAt;

    private Timestamp updatedAt;


    public BoardResponse(Board board){
        this.id = board.getId();
        this.writer = board.getWriter();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.member = board.getMember();
        this.replies = board.getReplies();
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
    }
}
```


# 마치며
표현 영역에 대해 간단하게 알아보고 어떻게 하면 효율적으로 표현 영역을 관리할지 알아보았습니다. 위에서 말했듯이 표현 영역에서는 필수 값, 값의 형식 등을 검증을 확실히 해야만 응용서비스에서 반복된 로직이 나오지 않습니다. 그리고 응용 영역은 NULL에 대한 걱정, 데이터 format에 대한 걱정을 하지 않게 됨으로써 소스의 비지니스로직에 집중할 수 있고, 가독성이 높아집니다.

물론 응용서비스를 실행하는 주체가 같은 응용서비스이거나, 파라미터로 전달받은 값이 불안정하다면 응용 영역에서 validate 처리를 해야 할 수도 있습니다. 하지만 처음부터 무결한 데이터를 받거나, 응용서비스 자체에서도 최소한 NULL을 지양하는 코드를 작성해나간다면 NULL에 대한 검사 코드는 거의 없어질것입니다. 
