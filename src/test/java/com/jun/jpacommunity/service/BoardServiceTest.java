package com.jun.jpacommunity.service;


import com.jun.jpacommunity.domain.Board;
import com.jun.jpacommunity.domain.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Test
    public void connectedH2() throws Exception {

        //given
        Member member = Member.builder()
                .name("junyoung")
                .password("test")
                .age(28)
                .email("test@gmail.com")
                .build();


        Board board = Board.createBoard("안녕하세요","잘가", member);
        //when
        memberService.save(member);
        boardService.save(board);

        //Board findBoard = boardService.findOne(board.getId());
        //System.out.println(findBoard.getId()+" "+findBoard.getTitle()+" "+findBoard.getContent()+" "+findBoard.getMember().getName());
        //System.out.println(findBoard.getCreatedAt() +" " + findBoard.getUpdatedAt());


        //then



     }
}
