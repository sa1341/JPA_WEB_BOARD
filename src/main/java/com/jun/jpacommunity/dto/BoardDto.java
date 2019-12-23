package com.jun.jpacommunity.dto;

import com.jun.jpacommunity.domain.Board;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class BoardDto {

    private Long id;
    private String title;
    private String memberId;

    private Timestamp createdAt;

    private Timestamp updateAt;


    public BoardDto(Board board) {

        this.id = board.getId();
        this.title = board.getTitle();
        this.memberId = board.getMember().getUname();
        this.createdAt = board.getCreatedAt();
        this.updateAt = board.getUpdatedAt();
    }
}
