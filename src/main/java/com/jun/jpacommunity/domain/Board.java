package com.jun.jpacommunity.domain;

import com.jun.jpacommunity.dto.BoardForm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"replies", "user"})
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @NotEmpty(message = "제목을 넣으셔야 합니다.")
    @Column(nullable = false)
    private String title;

    @NotEmpty(message = "내용을 넣어주셔야 합니다.")
    @Column(length = 500)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Reply> replies = new ArrayList<Reply>();

    @CreationTimestamp
    @Column(name = "sys_creation_date", nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "sys_update_date", nullable = false)
    private Timestamp updatedAt;



    public static Board makeBoardForm(){
            Board boardForm = new Board();
            return boardForm;
    }


    public static Board createBoard(final String title, final String content, final User user){

        Board board = new Board();
        board.title = title;
        board.content = content;
        board.user = user;
        return board;
    }

    // 게시글 업데이트를 수행하는 로직
    public void updateBoard(BoardForm boardForm){
        this.title = boardForm.getTitle();
        this.content = boardForm.getContent();
    }

    public void setMember(final User user) {
        this.user = user;
    }


}
