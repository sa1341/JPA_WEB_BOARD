package com.jun.jpacommunity.repository;


import com.jun.jpacommunity.domain.Board;
import com.jun.jpacommunity.domain.Book;
import com.jun.jpacommunity.domain.Member;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class JpaTest {

    private final static String BOOT_TEST_TITLE = "Spring Boot Test Book";

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void Book_저장하기_테스트() throws Exception {
        //given
        Book book1 = Book.builder()
                .title("쉑쉑버거")
                .publishedAt(LocalDateTime.now())
                .build();

        //when
        testEntityManager.persist(book1);

        //then
        assertEquals(book1, testEntityManager.find(Book.class, book1.getIdx()));

     }

     @Test
     public void BookList_저장하고_검색_테스트() throws Exception {

         //given
         Book book1 = Book.builder()
                 .title(BOOT_TEST_TITLE +"1")
                 .publishedAt(LocalDateTime.now())
                 .build();

         testEntityManager.persist(book1);

         Book book2 = Book.builder()
                 .title(BOOT_TEST_TITLE +"2")
                 .publishedAt(LocalDateTime.now())
                 .build();

         testEntityManager.persist(book2);

         Book book3 = Book.builder()
                 .title(BOOT_TEST_TITLE +"3")
                 .publishedAt(LocalDateTime.now())
                 .build();

         testEntityManager.persist(book3);


         //when
         List<Book> bookList = bookRepository.findAll();

         //then
         assertEquals(bookList.size(), 3);
         assertEquals(book1, bookList.get(0));
      }


    @Test
    public void BookList_저장하고_삭제_테스트() throws Exception {

        //given
        Book book1 = Book.builder().title(BOOT_TEST_TITLE + "1").
                publishedAt(LocalDateTime.now()).build();

        testEntityManager.persist(book1);

        Book book2 = Book.builder().title(BOOT_TEST_TITLE + "2").
                publishedAt(LocalDateTime.now()).build();

        testEntityManager.persist(book2);

        //when
        bookRepository.deleteAll();
        //then
        assertThat(bookRepository.findAll(), IsEmptyCollection.empty());
    }





 /*   @Test
    public void Board_저장하기_테스트() throws Exception {

        //given
        Member member = new Member();
        member.setId("sa1341");
        member.setPassword("1234");
        member.setAge(28);
        member.setName("junyoung");

        Board board = Board.createBoard(member, "벚꽃엔딩", "사랑이란게 영원할것같았던");
        board.setMember(member);


        //when
        testEntityManager.persist(member);
        testEntityManager.persist(board);


        //then
        assertEquals(member, testEntityManager.find(Member.class,member.getId()));
        assertEquals(board, testEntityManager.find(Board.class,board.getId()));

     }*/


}
