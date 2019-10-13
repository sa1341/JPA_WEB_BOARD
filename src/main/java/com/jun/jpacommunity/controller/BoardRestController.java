package com.jun.jpacommunity.controller;


import com.jun.jpacommunity.domain.Board;
import com.jun.jpacommunity.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
@Slf4j
public class BoardRestController {

    @Autowired
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<?> postBoard(@RequestBody @Valid Board board, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return new ResponseEntity<>("제대로 입력하세요", HttpStatus.BAD_REQUEST);
        }

        log.info("" + board.getTitle());
        log.info("" + board.getContent());

        boardService.save(board);

        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putBoard(@PathVariable("id") Long id, @RequestBody Board board){

        boardService.updateBoard(id, board);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable("id") Long id){

        log.info(""+ id);
        boardService.deleteById(id);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }




}
