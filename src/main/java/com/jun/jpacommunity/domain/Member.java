package com.jun.jpacommunity.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {


    @Id
    @Column(name = "member_id")
    private String id;

    private String password;

    private String name;

    private int age;

    @OneToMany(mappedBy = "member")
    List<Board> boards = new ArrayList<Board>();

    @CreationTimestamp
    @Column(name = "sys_creation_date")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "sys_update_date")
    private Timestamp updatedAt;



}
