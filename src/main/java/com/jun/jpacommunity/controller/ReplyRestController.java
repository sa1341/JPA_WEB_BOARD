package com.jun.jpacommunity.controller;

import com.jun.jpacommunity.domain.Board;
import com.jun.jpacommunity.domain.Reply;
import com.jun.jpacommunity.repository.BoardRepository;
import com.jun.jpacommunity.repository.ReplyRepository;
import com.jun.jpacommunity.service.BoardService;
import com.jun.jpacommunity.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/replies")
@Slf4j
@RequiredArgsConstructor
public class ReplyRestController {

    private final ReplyService replyService;

    private final BoardService boardService;

    @GetMapping("/{bno}")
    public ResponseEntity<List<Reply>> getReplies(@PathVariable("bno") Long bno){

        log.info("get All Replies.................");

        Board findBoard = boardService.findBoardById(bno);

        return new ResponseEntity<>(getListByBoard(findBoard), HttpStatus.OK);
    }


    // 댓글 등록 회후 게시글 목록 조회
    @PostMapping("/{bno}")
    public ResponseEntity<List<Reply>> addReply(@PathVariable("bno") Long bno, @RequestBody Reply reply) {

        log.info("addReply");
        log.info("bno: " + bno);

        log.info("REPLY: " + reply);

        Board findBoard = boardService.findBoardById(bno);
        reply.setBoard(findBoard);

        replyService.save(reply);

        return new ResponseEntity<>(getListByBoard(findBoard), HttpStatus.CREATED);
    }

    // 게시글에 대한 댓글 목록 조회
    public List<Reply> getListByBoard(Board board) throws RuntimeException{

        log.info("getListByBoard...." + board);

        return replyService.getRepliesOfBoard(board);
    }

    // 게시글에 대한 댓글 수
    @PutMapping("/{bno}")
    public ResponseEntity<List<Reply>> updateReply(@PathVariable("bno") Long bno, @RequestBody Reply reply){

        log.info("update reply: " + reply);

        System.out.println("bno: " + bno);
        replyService.updateReply(reply);

        Board findBoard = boardService.findBoardById(bno);

        return new ResponseEntity<>(getListByBoard(findBoard), HttpStatus.CREATED);
    }


    // 댓글 삭제 후 게시글의 댓글 갱신
    @DeleteMapping("/{bno}/{rno}")
    public ResponseEntity<List<Reply>> deleteReply(@PathVariable("bno") Long bno, @PathVariable("rno") Long rno){

        log.info("delete reply: " + rno);
        replyService.deleteReplyById(rno);

        Board findBoard = boardService.findBoardById(bno);

        return new ResponseEntity<>(getListByBoard(findBoard), HttpStatus.OK);
    }


}
