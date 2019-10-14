package com.jun.jpacommunity.dto;


import com.jun.jpacommunity.domain.Board;
import com.jun.jpacommunity.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(exclude = "member")
public class BoardForm {

    private Long id;

    @NotEmpty(message = "작성자를 넣어주셔야 합니다")
    private String writer;

    @NotEmpty(message = "제목을 넣으셔야 합니다.")
    private  String title;

    @NotEmpty(message = "내용을 넣어주셔야 합니다.")
    private  String content;

    private Member member;


    public Board toEntity(){
        Board board = Board.createBoard(this.id, this.writer ,this.title, this.content, this.member);
        return board;
    }

}
