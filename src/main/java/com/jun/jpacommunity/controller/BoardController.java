package com.jun.jpacommunity.controller;

import com.jun.jpacommunity.service.BoardService;
import com.jun.jpacommunity.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {


    private final BoardService boardService;

    private final MemberService memberService;


    @GetMapping({"", "/"})
    public String board(@RequestParam(value ="Id", defaultValue = "0") Long Id, Model model){

        model.addAttribute("board", boardService.findBoardById(Id));
        return "/board/boardForm";
    }

    @GetMapping("/list")
    public String list(@PageableDefault Pageable pageable, Model model){

        model.addAttribute("boardList", boardService.findBoardList(pageable));
        return "/board/list";
    }

   /* @GetMapping("/board")
    public String createForm(){
        return "board/boardForm";
    }



    @PostMapping("/board")
    public String saveBoard(BoardDTO boardDTO){

        Member member = Member.builder()
                .name("junyoung")
                .password("test")
                .age(28)
                .email("test@gmail.com")
                .build();

        String title ="뺑덕어멈";
        String content = "왜불러?";

        Board board = Board.createBoard(boardDTO.getTitle(), boardDTO.getContent(),member);
        memberService.save(member);
        boardService.save(board);

        return "redirect:/board";
    }
*/

}
