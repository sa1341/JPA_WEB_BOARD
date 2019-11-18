package com.jun.jpacommunity.dto;

import com.jun.jpacommunity.domain.Board;
import com.jun.jpacommunity.domain.Member;
import com.jun.jpacommunity.domain.Reply;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ReplyDto {


    private String content;

    private String replier;

    private Timestamp createdAt;

    public Reply toEntity(Board board, Member member){
        Reply reply = new Reply();
        reply.setContent(this.content);
        reply.setBoard(board);
        reply.setMember(member);

        return reply;
    }

}
