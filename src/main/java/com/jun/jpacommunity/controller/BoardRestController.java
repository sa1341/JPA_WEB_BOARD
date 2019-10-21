package com.jun.jpacommunity.controller;


import com.jun.jpacommunity.domain.Board;
import com.jun.jpacommunity.dto.BoardForm;
import com.jun.jpacommunity.dto.BoardResponse;
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
    public ResponseEntity<?> postBoard(@RequestBody @Valid final BoardForm boardForm, final BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return new ResponseEntity<>("제대로 입력하세요", HttpStatus.BAD_REQUEST);
        }

        log.info("" + boardForm.getTitle());
        log.info("" + boardForm.getContent());

        Board board = boardForm.toEntity();
        boardService.save(board);

        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putBoard(@PathVariable("id") final Long id, @RequestBody final BoardForm boardForm){

        boardService.updateBoard(id, boardForm);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable("id") final Long id){

        log.info(""+ id);
        boardService.deleteById(id);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }




}
