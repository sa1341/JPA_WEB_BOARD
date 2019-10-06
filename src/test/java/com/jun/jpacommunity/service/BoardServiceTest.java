package com.jun.jpacommunity.service;


import com.jun.jpacommunity.domain.Board;
import com.jun.jpacommunity.domain.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    BoardService boardService;

    @Test
    public void connectedH2() throws Exception {

        //given
        Member member = new Member();
        member.setId("sa1341");
        member.setPassword("djalkwnsd");
        member.setName("임준영");
        member.setAge(28);


        Board board = new Board();
        board.setMember(member);
        board.setTitle("내일은 월요일");
        board.setContent("준영아 힘내자!");

        //when
        memberService.save(member);
        boardService.save(board);

        //then


     }
}
