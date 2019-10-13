package com.jun.jpacommunity.domain;

import com.jun.jpacommunity.domain.enums.BoardType;
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


    public static Board createBoard(String title, String content, Member member){

        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);
        board.setMember(member);
        return board;
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
