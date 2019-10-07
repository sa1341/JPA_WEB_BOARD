package com.jun.jpacommunity.dto;


import com.jun.jpacommunity.domain.Board;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDTO {

    private String memberId;
    private String title;
    private String content;


   /* public Board toEntity(){

        Board board = Board.createBoard(getMemberId(), getTitle(), getContent());

        return board;
    }
*/

}
