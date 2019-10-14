package com.jun.jpacommunity.dto;

import com.jun.jpacommunity.domain.Board;
import com.jun.jpacommunity.domain.Member;
import com.jun.jpacommunity.domain.Reply;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class BoardResponse {


    private Long id;

    private String writer;

    private String title;

    private String content;

    private Member member;

    private List<Reply> replies = new ArrayList<Reply>();

    private Timestamp createdAt;

    private Timestamp updatedAt;


    public BoardResponse(Board board){
        this.id = board.getId();
        this.writer = board.getWriter();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.member = board.getMember();
        this.replies = board.getReplies();
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
    }

}
