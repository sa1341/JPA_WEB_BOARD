package com.jun.jpacommunity.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    private String content;

    @CreationTimestamp
    @Column(name = "sys_creation_date")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "sys_update_date")
    private Timestamp updatedAt;


    public static Board createBoard(Member member, String title, String content){

        Board board = new Board();
        board.setMember(member);
        board.setTitle(title);
        board.setContent(content);

        return board;
    }

    public void setMember(final Member member) {
        this.member = member;

    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setContent(final String content) {
        this.content = content;
    }
}
