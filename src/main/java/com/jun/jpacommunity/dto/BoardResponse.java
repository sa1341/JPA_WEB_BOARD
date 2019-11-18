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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardResponse {


    private Long id;
    private String title;
    private String writer;
    private String content;

    private List<Reply> replies = new ArrayList<Reply>();

    private Timestamp createdAt;

    private Timestamp updatedAt;


    public BoardResponse(Board board){
        this.id = board.getId();
        this.title = board.getTitle();
        this.writer = board.getMember().getUid();
        this.content = board.getContent();
        this.replies = board.getReplies();
        this.createdAt = board.getCreatedAt();
        this.updatedAt = board.getUpdatedAt();
    }

}
