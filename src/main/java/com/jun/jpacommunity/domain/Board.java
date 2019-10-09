package com.jun.jpacommunity.domain;

import com.jun.jpacommunity.domain.enums.BoardType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;


    @CreationTimestamp
    @Column(name = "sys_creation_date")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "sys_update_date")
    private Timestamp updatedAt;


    public static Board createBoard(String title, String content,Member member, BoardType boardType){

        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);
        board.setMember(member);
        board.setBoardType(boardType);
        return board;
    }

    public void setBoardType(BoardType boardType) {
        this.boardType = boardType;
    }

    public void setMember(final Member member) {
        this.member = member;

        member.getBoards().add(this);

    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setContent(final String content) {
        this.content = content;
    }
}
