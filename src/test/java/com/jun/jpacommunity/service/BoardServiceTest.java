package com.jun.jpacommunity.service;


import com.jun.jpacommunity.domain.Board;
import com.jun.jpacommunity.domain.Member;
import com.jun.jpacommunity.domain.QBoard;
import com.jun.jpacommunity.repository.BoardRepository;
import com.jun.jpacommunity.repository.BoardSearch;
import com.querydsl.core.BooleanBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BoardServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;

    @Test
    public void connectedH2() throws Exception {

        //given
        Member member = Member.builder()
                .name("junyoung")
                .password("test")
                .age(28)
                .email("test@gmail.com")
                .build();


        //BoardForm boardForm = new BoardForm("빡센하루","내일도 화이팅", BoardType.free);
       // Board board = boardForm.toEntity();
        //board.setMember(member);

        //when
        memberService.save(member);
        //boardService.save(board);

        //Board findBoard = boardService.findOne(board.getId());
        //System.out.println(findBoard.getId()+" "+findBoard.getTitle()+" "+findBoard.getContent()+" "+findBoard.getMember().getName());
        //System.out.println(findBoard.getCreatedAt() +" " + findBoard.getUpdatedAt());


        //then

     }

     @Test
     public void 검색_쿼리수행() throws Exception {

         //given
         String title = "제목200";

         BooleanBuilder builder = new BooleanBuilder();

         QBoard board = QBoard.board;
         if(title.equals("제목200")){
            builder.and(board.title.like("%" + title +"%"));
         }

         builder.and(board.id.gt(0));
         Pageable pageable = PageRequest.of(0, 10);

         Page<Board> boards = boardRepository.findAll(builder, pageable);

         System.out.println(boards);
        /* Page<Board> result = boardService.findAll(pageable);

         System.out.println("PAGE SIZE: " + result.getSize());
         System.out.println("TOTAL PAGES: " + result.getTotalPages());
         System.out.println("TOTAL COUNT: " + result.getTotalElements());
         System.out.println("NEXT: " + result.nextPageable());

         List<Board> list = result.getContent();

         list.forEach(b -> System.out.println(b));*/
         //when


         //then
      }


       @Test
       public void testList2() throws Exception {

           //given
           Pageable pageable = PageRequest.of(0, 20, Sort.Direction.DESC, "id");

           BoardSearch boardSearch = new BoardSearch();
           boardSearch.setType("t");
           boardSearch.setKeyword("10");



           //when
           Page<Board> result = boardRepository.findAll(boardRepository.makePredicate(boardSearch), pageable);
           System.out.println("PAGE: " + result.getPageable());

           result.getContent().forEach(board -> System.out.println("" + board.getTitle()));

           //then
        }


}
