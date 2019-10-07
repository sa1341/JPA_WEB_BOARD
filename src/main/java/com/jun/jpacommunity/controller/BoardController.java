package com.jun.jpacommunity.controller;

import com.jun.jpacommunity.domain.Board;
import com.jun.jpacommunity.domain.Member;
import com.jun.jpacommunity.dto.BoardDTO;
import com.jun.jpacommunity.service.BoardService;
import com.jun.jpacommunity.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class BoardController {


    private final BoardService boardService;

    private final MemberService memberService;



    @GetMapping("/board")
    public String createForm(){
        return "board/boardForm";
    }



    @PostMapping("/board")
    public String saveBoard(BoardDTO boardDTO){

        Member member = new Member();
        member.setId("sa1341");
        member.setPassword("djalkwnsd");
        member.setName("임준영");
        member.setAge(28);

        String title ="뺑덕어멈";
        String content = "왜불러?";

        Board board = Board.createBoard(member,boardDTO.getTitle(), boardDTO.getContent());
        memberService.save(member);
        boardService.save(board);

        return "redirect:/board";
    }


}
