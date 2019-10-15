package com.jun.jpacommunity.dto;


import com.jun.jpacommunity.domain.Board;
import com.jun.jpacommunity.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(exclude = "member")
public class BoardForm {

    private Long id;

    private String writer;

    @NotBlank(message = "제목을 넣으셔야 합니다.")
    private  String title;

    private  String content;

    private Member member;


    public Board toEntity(){
        Board board = Board.createBoard(this.id, this.writer ,this.title, this.content, this.member);
        return board;
    }

}
