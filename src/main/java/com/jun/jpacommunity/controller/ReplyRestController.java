package com.jun.jpacommunity.controller;

import com.jun.jpacommunity.domain.Board;
import com.jun.jpacommunity.domain.Member;
import com.jun.jpacommunity.domain.Reply;
import com.jun.jpacommunity.dto.JobTicket;
import com.jun.jpacommunity.dto.ReplyDto;
import com.jun.jpacommunity.dto.ReplyResponse;
import com.jun.jpacommunity.service.BoardService;
import com.jun.jpacommunity.service.MemberService;
import com.jun.jpacommunity.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/replies")
@Slf4j
@RequiredArgsConstructor
public class ReplyRestController {

    private final ReplyService replyService;

    private final BoardService boardService;

    private final MemberService memberService;

    //게시글 상세 조회시 해당 게시글과 연관관계를 가진 댓글들을 조회
    @GetMapping("/{bno}")
    public ResponseEntity<List<ReplyResponse>> getReplies(@PathVariable("bno") Long bno){

        log.info("get All Replies.................");

        Board findBoard = boardService.findBoardById(bno);

        return new ResponseEntity<>(getListByBoard(findBoard), HttpStatus.OK);
    }


    // 댓글 등록 회후 게시글 목록 조회
    @PostMapping("/{bno}")
    public ResponseEntity<List<ReplyResponse>> addReply(@PathVariable("bno") final Long bno, @RequestBody final ReplyDto replyDto) {

        log.info("bno: " + bno);
        log.info("REPLY: " + replyDto.getContent());
        log.info("REPLY: " + replyDto.getReplier());

        String memberId = replyDto.getReplier();

        Board findBoard = boardService.findBoardById(bno);
        Member findMember = memberService.findById(memberId.trim());
        Reply reply = replyDto.toEntity(findBoard, findMember);

        replyService.save(reply);

        return new ResponseEntity<>(getListByBoard(findBoard), HttpStatus.CREATED);
    }

    // 게시글에 대한 댓글 목록 조회
    public List<ReplyResponse> getListByBoard(final Board board) throws RuntimeException{

        log.info("getListByBoard...." + board);
        List<Reply> replies =  replyService.getRepliesOfBoard(board);
        List<ReplyResponse> replyResponses = replies.stream().map(r -> new ReplyResponse(r)).collect(Collectors.toList());

        return replyResponses;

    }

    // 게시글에 대한 댓글 수
    @PutMapping("/{bno}")
    public ResponseEntity<List<ReplyResponse>> updateReply(@PathVariable("bno") final Long bno, @RequestBody final Reply reply){

        log.info("update reply: " + reply);

        System.out.println("bno: " + bno);
        replyService.updateReply(reply);

        Board findBoard = boardService.findBoardById(bno);

        return new ResponseEntity<>(getListByBoard(findBoard), HttpStatus.CREATED);
    }


    // 댓글 삭제 후 게시글의 댓글 갱신
    @DeleteMapping("/{bno}/{rno}")
    public ResponseEntity<List<ReplyResponse>> deleteReply(@PathVariable("bno") final Long bno, @PathVariable("rno") final Long rno){

        log.info("delete reply: " + rno);
        replyService.deleteReplyById(rno);
        Board findBoard = boardService.findBoardById(bno);

        return new ResponseEntity<>(getListByBoard(findBoard), HttpStatus.OK);
    }




}
