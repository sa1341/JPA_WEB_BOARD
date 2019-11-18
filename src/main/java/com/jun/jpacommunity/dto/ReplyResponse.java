package com.jun.jpacommunity.dto;

import com.jun.jpacommunity.domain.Reply;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyResponse {

    private Long id;

    private String content;

    private String writer;

    private Timestamp createdAt;

    public ReplyResponse(Reply reply){
        this.id = reply.getId();
        this.content = reply.getContent();
        this.writer = reply.getMember().getUid();
        this.createdAt = reply.getCreatedAt();
    }

}
