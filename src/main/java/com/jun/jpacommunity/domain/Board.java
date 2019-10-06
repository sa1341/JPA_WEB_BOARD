package com.jun.jpacommunity.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Board {

    @Id
    @GeneratedValue
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


}
