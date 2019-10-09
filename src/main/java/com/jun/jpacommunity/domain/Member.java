package com.jun.jpacommunity.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String password;

    @Column(nullable = false)
    private String email;

    private int age;

    @OneToMany(mappedBy = "member")
    List<Board> boards = new ArrayList<Board>();

    @CreationTimestamp
    @Column(name = "sys_creation_date")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "sys_update_date")
    private Timestamp updatedAt;

    @Builder
    public Member(String name, String password, String email, int age) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.age = age;
    }
}
