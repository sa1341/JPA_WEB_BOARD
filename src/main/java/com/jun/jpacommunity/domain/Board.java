package com.jun.jpacommunity.domain;

import com.jun.jpacommunity.domain.enums.BoardType;
import com.jun.jpacommunity.dto.BoardForm;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(exclude = {"replies", "member"})
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String writer;

    @NotEmpty(message = "제목을 넣으셔야 합니다.")
    private String title;

    @NotEmpty(message = "내용을 넣어주셔야 합니다.")
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Reply> replies = new ArrayList<Reply>();

    @CreationTimestamp
    @Column(name = "sys_creation_date", nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "sys_update_date", nullable = false)
    private Timestamp updatedAt;


    public static Board createBoard(final Long id, final String writer ,final String title, final String content, final Member member){
        Board board = new Board();
        board.id = id;
        board.writer = writer;
        board.title = title;
        board.content = content;
        board.member = member;
        return board;
    }

    public void updateBoard(BoardForm boardForm){
        this.title = boardForm.getTitle();
        this.content = boardForm.getContent();
    }

    public void setMember(final Member member) {
        this.member = member;

        member.getBoards().add(this);

    }

}
