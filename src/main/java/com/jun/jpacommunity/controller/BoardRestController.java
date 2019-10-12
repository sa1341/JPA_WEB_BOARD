package com.jun.jpacommunity.controller;


import com.jun.jpacommunity.domain.Board;
import com.jun.jpacommunity.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
@Slf4j
public class BoardRestController {

    @Autowired
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<?> postBoard(@RequestBody Board board){

        log.info("" + board.getTitle());
        log.info("" + board.getContent());

        boardService.save(board);

        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }


}
