package com.jun.jpacommunity.service;


import com.jun.jpacommunity.domain.Board;
import com.jun.jpacommunity.domain.Member;
import com.jun.jpacommunity.domain.QBoard;
import com.jun.jpacommunity.domain.Reply;
import com.jun.jpacommunity.repository.BoardRepository;
import com.jun.jpacommunity.repository.BoardSearch;
import com.jun.jpacommunity.repository.ReplyRepository;
import com.querydsl.core.BooleanBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

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

    @Autowired
    ReplyRepository replyRepository;

    @Test
    @Rollback(false)
    public void connectedH2() throws Exception {

        //given
        Member member = Member.builder()
                .name("junyoung")
                .password("test")
                .age(28)
                .email("test@gmail.com")
                .build();

        memberService.save(member);

        Board board = Board.createBoard(member.getName(), "제목" + 1, "내용" + 1, member);
        boardRepository.save(board);

        Reply reply1 = new Reply();
        reply1.setContent("내용" + 1);
        reply1.setReplier("작성자" + 1);
        reply1.setBoard(board);

        Reply reply2 = new Reply();
        reply2.setContent("내용" + 2);
        reply2.setReplier("작성자" + 2);
        reply2.setBoard(board);

        //board.getReplies().add(reply1);
        //board.getReplies().add(reply2);

        //when
        replyRepository.save(reply1);
        replyRepository.save(reply2);

        Optional<Board> optBoard = boardRepository.findById(board.getId());
        Board findBoard = optBoard.get();

        List<Reply> replies = findBoard.getReplies();

        System.out.println(findBoard);
        System.out.println(findBoard.getReplies().getClass());

        for(Reply reply : replies){
            System.out.println(reply.getId() +" " + reply.getContent() +" " + reply.getReplier());
        }

        System.out.println(boardRepository.getReplyCount(findBoard.getId()));

        //then
        assertThat(findBoard.getTitle(), is("제목1"));

    }

    @Test
    public void 검색_쿼리수행() throws Exception {

        //given
        String title = "제목200";

        BooleanBuilder builder = new BooleanBuilder();

        QBoard board = QBoard.board;
        if (title.equals("제목200")) {
            builder.and(board.title.like("%" + title + "%"));
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

        BoardSearch boardSearch = new BoardSearch("t", "10");


        //when
        Page<Board> result = boardRepository.findAll(boardRepository.makePredicate(boardSearch), pageable);
        System.out.println("PAGE: " + result.getPageable());

        result.getContent().forEach(board -> System.out.println("" + board.getTitle()));

        //then
    }







}
