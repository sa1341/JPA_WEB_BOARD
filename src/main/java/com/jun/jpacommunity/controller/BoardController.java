package com.jun.jpacommunity.controller;

import com.jun.jpacommunity.domain.Board;
import com.jun.jpacommunity.dto.BoardForm;
import com.jun.jpacommunity.dto.PageMaker;
import com.jun.jpacommunity.dto.PageVO;
import com.jun.jpacommunity.repository.BoardSearch;
import com.jun.jpacommunity.service.BoardService;
import com.jun.jpacommunity.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {


    private final BoardService boardService;

    private final MemberService memberService;


    @GetMapping({"", "/"})
    public String board(@RequestParam(value ="id", defaultValue = "0") Long id, Model model){
        log.info("" + "어 왔니?");

        model.addAttribute("board", boardService.findBoardById(id));
        return "board/boardForm";
    }


    // 게시글 목록 호출
    @GetMapping("/list")
    public String boardList(PageVO pageVO, Model model, @ModelAttribute("boardSearch") BoardSearch boardSearch){


        // 페이징 처리에 필요한 정보를 제공함.
        // 표현계층에서 정렬 방향과 정렬 대상을 처리하는 부분
        Pageable page = pageVO.makePageable(0,"id");

        //Page<T> 타입을 이용하면 Spring MV와 연동할 때 상당한 편리함을 제공해줍니다. 단순 데이터 추출용도가 아니고 웹에서 필요한 데이터들을 추가적으로 처리해줌.
        Page<Board> boards = boardService.findAll(boardService.makePredicate(boardSearch), page);

        log.info("" + page);
        log.info("" + boards);
        log.info("" + boardSearch.getType());
        log.info("" + boardSearch.getKeyword());

        model.addAttribute("boardList", new PageMaker<>(boards));

        return "board/list";
    }



}
