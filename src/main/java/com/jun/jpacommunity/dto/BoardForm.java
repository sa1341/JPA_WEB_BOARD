package com.jun.jpacommunity.dto;


import com.jun.jpacommunity.domain.Board;
import com.jun.jpacommunity.domain.enums.BoardType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BoardForm {

    @NotEmpty(message = "제목을 넣으셔야 합니다.")
    private  String title;

    @NotEmpty(message = "내용을 넣어주셔야 합니다.")
    private  String content;

    private  BoardType boardType;


    public Board toEntity(){

        Board board = Board.createBoard(getTitle(), getContent(), getBoardType());

        return board;
    }


}
