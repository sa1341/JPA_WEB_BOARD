# Step-04 게시판 페이징 처리 구현하기 

# 응용 영역 : 페이징처리 기능

이 단계에서는 게시판에서 페이징 처리를 어떻게 객체 지향적으로 작성할지 알아보겠습니다.
페이징 처리는 주로 사용자가 게시판에 글을 작성하고 작성한 게시글 목록을 프론트엔드쪽으로 게시글을 몇건을 보여주고 페이지 화면을 어떻게 보여줄 것인지에 대한 모든것을 의미합니다.

먼저, 클라이언트(웹 브라우저)쪽에서 게시글 목록을 요청하는 Request Data가 오면 페이징 처리에 대한 정보를 PageVO 객체로 바인딩을 합니다. 만약 요청 URI에 페이징 처리에 대한 파라미터 없이 전송된다면 PageVO는 기본 생성자 호출시에 기본적으로 페이지 번호는 1, 페이지 크기는 10으로 초기화가 됩니다.

```java
@Getter
public class PageVO {

    private static final int DEFAULT_SIZE = 10;
    private static final int DEFAULT_MAX_SIZE = 50;

    private int page;
    private int size;

    public PageVO(){
        this.page = 1;
        this.size = DEFAULT_SIZE;
    }

    //파라미터로 페이지 번호가 넘어오면 설정자로 인스턴스 변수에 값을 대입
    public void setPage(int page) {
        this.page = page;
    }

    // 페이지 크기에 대한 파라미터 값이 넘어오면 해당 값으로 사이즈를 설정
    // 최대 크기는 50을 넘을 수가 없도록 설계하였습니다.
    public void setSize(int size) {
        this.size = size < DEFAULT_SIZE || size > DEFAULT_MAX_SIZE ? DEFAULT_SIZE : size;
    }

    public Pageable makePageable(int direction, String... props){

        Sort.Direction dir = direction == 0 ? Sort.Direction.DESC : Sort.Direction.ASC;
        
        // 실제 페이지 번호는 내부적으로 0번 index부터 시작합니다.
        // 사용자 입장에서는 페이지 번호가 직관적이지 않기 때문에 -1로 리턴
        return PageRequest.of(this.page - 1, this.size, dir, props);
    }
}
```

이 프로젝트는 웹 서버에서 간단한 게시판 커뮤니티를 구현하는게 목적이기 때문에 페이징 처리에 유용한 Pageable 인터페이스를 사용하였습니다. 

> 스프링 부트 2.0의 경우 Pageable 인터페이스를 구현하는 PageRequest 객체를     생성할때 PageRequest.of() 메소드를 사용해야 합니다.

PageVO 객체는 페이지 번호와 크기를 설정하고, 정렬 방향과 정렬 기준 정보를 가진 Pageable 인터페이스를 구현하는 PageRequest 객체를 생성해주는 메소드를 가지고 있습니다.


```java
 @GetMapping("/list")
    public String boardList(PageVO pageVO, Model model, @ModelAttribute("boardSearch") BoardSearch boardSearch){


        // 페이징 처리에 필요한 정보를 제공함.
        // 표현계층에서 정렬 방향과 정렬 대상을 처리하는 부분
        Pageable page = pageVO.makePageable(0,"id");

        //Page<T> 타입을 이용하면 Spring MV와 연동할 때 상당한 편리함을 제공해줍니다. 단순 데이터 추출용도가 아니고 웹에서 필요한 데이터들을 추가적으로 처리해줍니다.
        Page<Board> boards = boardService.findAll(boardService.makePredicate(boardSearch), page);

        log.info("" + page);
        log.info("" + boards);
        log.info("" + boardSearch.getType());
        log.info("" + boardSearch.getKeyword());

        model.addAttribute("boardList", new PageMaker<>(boards));

        return "board/list";
    }
```

이제 페이징 처리에 대한 정보를 가진 객체를 만들었으니 실제로 Repository에서 Board 엔티티를 조회할때 해당 페이징 처리정보를 참조하여 JPA에서 쿼리를 날리때 limit이 적용되는 것을 확인 할 수 있습니다. 

Repository의 findAll() 메소드는 Pageable 타입이 매게변수일때 다음과 같이 Page<T> 타입의 객체를 리턴하게 되는데 Spring Data JPA에서 결과 데이터가 여러 개인 경우 List<T> 타입을 이용하기도 하지만, Page<T> 타입을 이용하면 Spirng MVC와 연동할 때 상당한 편리함을 제공합니다. 

```java
Pageable page = boardRepository.findAll(Pageable page)
```

### **Page\<T>** 가 제공하는 메소드 입니다.

| 메소드               | 설명                                                                                                                                                                                                       |
| ------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **int getNumber()**           | 현재 페이지의 정보 |
| **int geSize()**           | 한 페이지의 크기                                                                                              |
| **int getTotalPages()**         | 전체 페이지의 수                              


# 응용 영역 : 검색 기능
두번째로 검색 기능에 대해서 살펴보겠습니다. 클라이언트에서 요청한 검색 정보를 바인딩할 BoardSearch 클래스를 정의하였습니다.

```java
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardSearch {

    private String type;
    private String keyword;
}

```


![스크린샷 2019-10-23 오전 1 05 56](https://user-images.githubusercontent.com/22395934/67306058-4fa61d80-f531-11e9-9ce0-f0f5f7607c64.png)


제목, 내용, 작성자 같은 검색 타입을 선택하고 키워드를 입력하여 검색을 요청하면 BoardSearch 객체에 바인딩 됩니다.

바인딩 되어진 boardSearch 객체는 응용 계층으로 값이 전달 됩니다.
응용 계층(서비스)에서는 검색정보를 가진 boardSearch를 아큐먼트로 받아서 Predicate 타입의 인터페이스를 구현하는 BooleanBuilder 객체를 리턴하는데 이 객체의 역할은 동적 쿼리를 생성하는 책임을 가지고 있는 객체입니다.

동적쿼리 생성부분은 아래 URL을 참조하시면 됩니다.

[동적 쿼리 : QueryDSL](https://jojoldu.tistory.com/372)

```java
Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    public Predicate makePredicate(final BoardSearch boardSearch){
        return boardRepository.makePredicate(boardSearch);
    }
```

실제로 아래 코드에서 BoardRepository가 QueryDSL 의존성 라이브러리를 이용하여 동적으로 쿼리를 생성하는 부분입니다. 

```java
public interface BoardRepository extends JpaRepository<Board, Long>, QuerydslPredicateExecutor<Board> {

    @Query("select count(r) from Board b " + " JOIN b.replies r where b.id = ?1")
    public int getReplyCount(Long count);

    // 자바 8부터 인터페이스에 디폴트 메소드가 추가가 가능해졌습니다.
    public default Predicate makePredicate(BoardSearch boardSearch) {

        String type = boardSearch.getType();
        String keyword = boardSearch.getKeyword();

        // 메소드 체인형식으로 동적으로 조건절을 추가해주는 BooleanBuilder 객체 생성
        BooleanBuilder builder = new BooleanBuilder();

        QBoard board = QBoard.board;

        builder.and(board.id.gt(0));

        System.out.println( "type:" + type);
        System.out.println( "keyword:" + keyword);


        if(type == null){
            return builder;
        }
        // type 값에 따른 switch 문 수행
        switch(type){
            case "t":
                builder.and(board.title.like("%" + keyword + "%"));
                break;

            case "c":
                builder.and(board.content.like("%" + keyword + "%"));
                break;

            case "w":
                builder.and(board.writer.like("%" + keyword + "%"));
                break;
        }
        return builder;
    }
}
```

# QueryDSL 장점

1. QueryDsl을 사용하면 문법적으로 잘못된 쿼리를 허용하지 않습니다.
2. IDE 코드 자동완성 기능 사용
3. 도메인 타입과 Property를 안전하게 참조할 수 있습니다.
4. 도메인 타입의 리펙토링을 더 잘 할 수 있습니다.


> 한마디로 QueryDSL은 타입에 안전한 방식으로 쿼리를 실행하기 위한 목적으로         만들어졌습니다. 그것이 가능한 이유는 and(), like()와 같은 문자열이 아닌 메서드   호출 방식으로 쿼리가 수행되기 때문입니다.


이렇게 Predicate, Pageable 인터페이스를 구현한 객체들을 Repository 메소드의 매게변수로 넣어주므로써 페이징처리와 검색처리를 하는 기능이 대부분 구현되었습니다.

이제 클라이언트(웹 브라우저)쪽으로 페이징 화면을 어떻게 보여주는지 살펴보겠습니다.

```java
page<Board> boards = boardService.findAll(boardService.makePredicate(boardSearch), page);

    
model.addAttribute("boardList", new PageMaker<>(boards));
```

model 객체를 이용해서 뷰에 보내줄 PageMaker 객체를 생성하여 보내줍니다. PageMaker는 페이지 화면을 클라이언트가 전송한 페이지 번호에 따라서 페이지 화면 계산을 처리해주는 역할을 하는 객체입니다.

```java
@Getter
@ToString(exclude = "pageList")
public class PageMaker<T> {

    
    private Page<T> result;
    //이전 페이징처리 정보
    private Pageable prevPage;
    //다음 페이징처리 정보
    private Pageable nextPage;
    //현재 페이지 번호
    private int currentPageNum;
    //전체 페이지 개수
    private int totalPageNum;
    //현재 페이징처리 정보
    private Pageable currentPage;

    //실제 화면에서 보여줄 페이지 번호들을 가진 Collection 타입의 객체
    private List<Pageable> pageList;

    public PageMaker(Page<T> result) {
        this.result = result;

        this.currentPage = result.getPageable();

        this.currentPageNum = result.getNumber() + 1;

        this.totalPageNum = result.getTotalPages();

        this.pageList = new ArrayList<>();
        calcPages();
    }
    
    // 페이지 화면 처리하는 부분
    private void calcPages(){

        // 페이지 끝 번호를 나타냅니다.
        int tempEndNum = (int)(Math.ceil(this.currentPageNum/10.0)* 10);
        // 페이지 시작번호 
        int startNum = tempEndNum -9;

        Pageable startPage = this.currentPage;

        //뷰 화면에서 보여줄 페이지 번호를 계산하는 부분
        for(int i = startNum; i < this.currentPageNum; i++){
            startPage = startPage.previousOrFirst();
        }

        // 계산한 페이지 번호가 0이하면 prevPage 객체는 null값으로 
        // 이전 페이지 버튼을 보여주지 않기 위해 처리하는 부분입니다.
        this.prevPage = startPage.getPageNumber() <= 0? null :startPage.previousOrFirst();

        // 만약 클라이언트에서 전체 페이지 번호보다 큰 번호를 요청했을 시
        // 끝 페이지 번호가 전체 페이지보다 커지기 때문에 필요한 로직입니다.
        if(this.totalPageNum < tempEndNum){
            tempEndNum = this.totalPageNum;
            this.nextPage = null;
        }

        // 실제로 화면에 보여줄 페이지 번호들을 가지고 있는 List 객체에 
        // 페이징 처리 정보를 넣어주는 로직입니다.
        for(int i = startNum ; i <= tempEndNum; i++){
            pageList.add(startPage);
            startPage = startPage.next();
        }
        //끝 번호까지 보여준 후에 다음 페이지 번호를 보여주기 위한 로직
        //startPage가 전체 페이지 번호 이상이면 끝이기 때문에 null 값을 넣어줍니다.
        this.nextPage = startPage.getPageNumber() +1 < totalPageNum ? startPage: null;

    }
}
```

실제로 calc() 메소드는 클라이언트에서 요청한 페이지 번호에 따른 페이지 화면을 처리해주는 메소드입니다. 페이징 처리를 할때 기본적으로 10개씩 처리하기로 설정했기 때문에 시작페이지와 끝페이지는 기본적으로 9정도 차이가 납니다.


# 마치며
## Tell, don't ask
`Tell, don't ask`는 객체지향 및 캡슐화를 잘 표현하는 문장 같습니다. 응용영역에서 특별한 경우를 제외하고는 객체한테 묻지 말고 시키는 것이 코드의 응집력을 높입니다. 
페이징 처리 역할을 하는 객체와 검색처리 역할을 하는 객체한테 요청정보를 넘겨주어서 원하는 값만 받았습니다. 만약 검색기능에 날짜로 검색하는 기능이 추가된다고 해도 Repository의 makePredicate() 메소드에 변경사항이 발생하기 때문에 표현영역에서는 영향을 받지 않고 원하는 데이터를 얻을 수가 있습니다.

이렇게 객체지향적인 프로그래밍을 하기 위해서 항상 자신이 객체에게 묻고 있는지, 시키고 있는지를 생각하면서 개발해야하는 중요성을 조금이나마 이해하게 되었습니다.
