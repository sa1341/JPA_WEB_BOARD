# Step-02 게시판 및 댓글 엔티티 정의

## 1. 게시판 엔티티 정의

![스크린샷 2019-10-20 오전 1 19 33](https://user-images.githubusercontent.com/22395934/67148204-c051fd80-f2d7-11e9-8c50-09169988c220.png)


게시글을 등록하기 위해서는 Board 엔티티를 정의해야 합니다. 위 그림은 테이블을 기반으로 Board 엔티티를 정의하였습니다.

```java
package com.jun.jpacommunity.domain;

import com.jun.jpacommunity.domain.enums.BoardType;
import com.jun.jpacommunity.dto.BoardForm;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(exclude = {"replies", "member"})
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String writer;

    @NotEmpty(message = "제목을 넣으셔야 합니다.")
    @Column(nullable = false)
    private String title;

    @NotEmpty(message = "내용을 넣어주셔야 합니다.")
    @Column(length = 500)
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Reply> replies = new ArrayList<Reply>();

    @CreationTimestamp
    @Column(name = "sys_creation_date", nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "sys_update_date", nullable = false)
    private Timestamp updatedAt;


    public static Board createBoard(final Long id, final String writer ,final String title, final String content, final Member member){
        Board board = new Board();
        board.id = id;
        board.writer = writer;
        board.title = title;
        board.content = content;
        board.member = member;
        return board;
    }

    // 게시글 업데이트를 수행하는 로직
    public void updateBoard(BoardForm boardForm){
        this.title = boardForm.getTitle();
        this.content = boardForm.getContent();
    }

    public void setMember(final Member member) {
        this.member = member;

        member.getBoards().add(this);

    }
}
```

## 댓글 엔티티 정의

```java
package com.jun.jpacommunity.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

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



위의 정의되어 있는 Board 클래스와 Reply 클래스의 어노테이션들이 어떻게 정의되었는지, 어떤 용도인지 알아보겠습니다.




## @Entity
JPA를 통해 ORM으로 관리할 클래스들을 명시적으로 Entity라고 선언하는 것입니다. 도메인을 Entity로 사용하기 위해서 필수로 입력해줘야 합니다.

## @Table
테이블 어노테이션은 엔티티와 매핑할 실제 데이터베이스 테이블을 지정합니다. 위에서 정의한 엔티티에서는 해당 어노테이션을 지정하지 않았기 때문에 ddl 자동 생성 기능을 사용시에 테이블에는 엔티티 명으로 Board 테이블이 생성이 됩니다.


### @NoArgsConstructor(access = AcessLevel.PROTECTED)
다음은 파라미터가 없는 기본생성자를 만들어주는 어노테이션입니다. Spring JPA의 구현체인 하이버네이트를 사용하기 위해서 반드시 기본생성자가 필요합니다. 하이버네이트는 해당 테이블의 데이터를 엔티티로 생성할때 리플렉션을 이용하여 해당 엔티티 타입정보를 알아내기 때문에 반드시 기본생성자가 필요합니다. 롬복을 사용하지 않고 Java로 표현하면 다음과 같이 protected로 선언하는 것과 같습니다.

```java
protected Board(){

}
```

여기에서 굳이 접근지정자 레벨을 Protected로 하는 이유는 어플리케이션 내에서 이유없이 도메인을 생성하는 것을 막기 위해서 입니다. Board가 만들어지기 위해서는 필수적인 정보가 있을 텐데 기본 생성자를 이용해서 객체를 만들면 불완전한 객체가 생성되기 때문입니다.

이러한 방법으로 Protected 생성자를 지정하면 같이 협업하는 개발자분들이 이 객체는 적어도 기본생성자로 생성하면 안되는 객체인지를 인식합니다.

## @Id
JPA에서 해당 필드에 기본키를 지정하기 위해서 해당 어노테이션을 지정해야 합니다.
참고로 JPA에서 엔티티를 관리하는 영역인 영속 컨텍스트에서 실제 엔티티를 Map처럼 key,value 방식으로 해당 엔티티들을 관리하는데 key 값이 @Id입니다. 이 값으로 해당 엔티티의 인스턴스를 조회합니다.

## @GeneratedValue(strategy = GenerationType.IDENTITY)
@GeneratedValue(strategy = GenerationType.IDENTITY) 지정하면 Mysql의 AUTO_INCREMENT 기능으로 데이터베이스가 기본키를 생성합니다. 예를 들어 게시글을 2개 생성하면 게시글을 생성할 때는 id가 1,2 값으로 순서대로 게시글 엔티티를 생성해줍니다.


## @Embedded
해당 어노테이션은 실제로 쓰진 않았지만 데이터의 응집력과 데이터 타입 규합을 높여주기 때문에 객체지향적으로 프로그래밍을 가능하게 해줍니다. 어떻게 응집력을 높이고, 데이터를 규합시키는지 아래 간단한 예를 설명하겠습니다.

## BoardType class

```java
package com.jun.jpacommunity.domain.enums;

import lombok.Getter;

@Getter
public enum BoardType {

    notice("공지사항"), free("자유게시판");

    private String value;

    BoardType(String value) {
        this.value = value;
    }
}
```

위의 코드같이 BoardType 클래스가 있습니다. 이것은 실제로 Board 엔티티에 Embedded 되어 있지 않지만 만약에 Embedded 되어 있다면 객체타입이기 때문에 JPA가 아무리 강력한 기능을 제공한다고 해도 쉽게 데이터베이스에 데이터를 CRUD 할 수는 없습니다. 

그리고 단순히 String이 아닌 BoardType를 클래스로 하는 것에 대해 과한편이 아니냐고 생각할 수 있지만 만약 BoardType를 검증하기 위해 @NotEmpty 같은 어노테이션을 사용하여 스프링에서 클라이언트로 넘어온 데이터가 해당 어노테이션 조건을 만족하는지 validation 체크를 해주고 유효하지 않으면 Exception을 발생시켜 클라이언트한테 유효하지 않다고 알려준다고 해봅시다.

또한 String으로 선언했고, Board 클래스가 아닌 다른 클래스에서 BoardType 클래스를 맴버 필드로 참조한다고 가정을 한다면 BoardType 클래스나 다른 클래스에도 똑같이 validation 체크 어노테이션을 설정해줘야 하는 번거로움이 있습니다.

그렇다고 해서 모든 것을 Embedded 타입으로 가져가자는 의미는 아닙니다. 의미있는 데이터끼리 또는 위와같이 기능의 확장성이 필요로하는 데이터를 잘 선별하여 Embedded 타입으로 뺄 수 있다고 생각합니다. 


## @CreationTimestamp, @UpdateTimestamp
어노테이션은 만들어진 시간과 데이터 변경시간을 자동으로 처리해주는 어노테이션입니다.


## @Column(name = "sys_creation_date", nullable = false, unique = true)
다음은 Spring JPA에서 제공해주는 DDL 생성 기능입니다. Spring JPA에서는 애플리케이션을 다시 시작할 때 도메인을 기반으로 이러한 DDL 문을 통해 테이블을 새롭게 만들 수 있습니다. 물론 이런 DDL 설정을 auto-create로 하지 않는다면 필요 없는 기능일 수 있습니다. 하지만 데이터베이스와 Entity에 DDL 설정을 동기화하는 것을 추천드립니다.

그래야 크리티컬한 버그 줄일 수 있다고 생각하기 때문입니다. 예를 들어 NULL이 들어가면 안 되는 칼럼인데 개발자도 모르게 칼럼에 NULL이 들어가고 있다고 가정해보겠습니다. 위와 같은 제약조건들이 없으면 이것을 인식하는데 오래 걸려 치명적인 버그로 이어질 수 있습니다. 차라리 DBMS 제약조건 Exception 나서 빠르게 버그를 수정하는 것이 낫습니다.

 Reply 엔티티에서 @JoinColumn은 외래키와 매핑할 컬러명을 지정하는 어노테이션입니다. 연관관계를 설정할때는 반드시 연관관계의 주인을 명시해줘야 합니다.
 저같은 경우에는 Board와 Reply 엔티티를 일대다 양방향 관계로 매핑하였지만.....
 객체 그래프 탐색을 어떻게 할지에 따라서 굳이 양방향 매핑을 할 필요가 없습니다. 왜냐하면 테이블에서는 외래키 하나로 양방향 참조가 가능하기 때문입니다.

 JPA 연관관계 매핑에 대해서는 제 블로그에 자세하게 나와있으니 참고하시면 될거같습니다.

[JPA - 엔티티 매핑](https://webcoding-start.tistory.com/13?category=812494)


## 마치며
이장에서 엔티티 및 도메인을 정의하는 방법에 대해서 설명하였습니다. 사실 이장에서 객체지향도 중요하지만 JPA가 정확히는 JPA 구현체인 하이버네이트가 엔티티와 관계형 데이터베이스 사이에서 ORM 기술을 어떻게 구현하는지 설명하고 싶었습니다.

JPA가 런닝커브가 높은 편이지만 그만큼 강력한 기능을 지원하기 때문에 배워두면 정말 유용하게 사용할 수 있는 기술이라고 생각합니다. Mybatis로 실제 게시판과 댓글기능을 구현해 보았다면 JPA를 사용할때 얼마나 갓 기술인지 실감하게 됩니다. 물론 연관관계를 고민없이 무턱대고 사용하면 오히려 Mybatis 프레임워크보다 쓰는 것보다 못할수 있기 때문에 영속 컨텍스트와 Fetch 전략, 연관관계 매핑에 대한 이론과 실습을 충분히 해야된다고 생각합니다.

JPA를 충분히 익힌다면 객체지향적으로 프로그래밍을 하는데 최고의 서포터가 될 것 입니다.
