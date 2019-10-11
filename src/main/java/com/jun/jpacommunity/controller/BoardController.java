package com.jun.jpacommunity.controller;

import com.jun.jpacommunity.dto.PageVO;
import com.jun.jpacommunity.service.BoardService;
import com.jun.jpacommunity.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {


    private final BoardService boardService;

    private final MemberService memberService;


    @GetMapping({"", "/"})
    public String board(@RequestParam(value ="Id", defaultValue = "0") Long Id, Model model){
        System.out.println("왔니?");
        model.addAttribute("board", boardService.findBoardById(Id));
        return "board/boardForm";
    }

    // 게시글 목록 호출
    @GetMapping("/list")
    public String list(PageVO pageVO, Model model){
        // 페이징 처리에 필요한 정보를 제공함.
        // 표현계층에서 정렬 방향과 정렬 대상을 처리하는 부분
        Pageable page = pageVO.makePageable(0,"id");

        log.info("" + page);
        //model.addAttribute("boardList", boardService.findBoardList(pageable));
        return "board/list";
    }

    

}
