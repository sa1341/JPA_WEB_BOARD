package com.jun.jpacommunity.domain;

import com.jun.jpacommunity.dto.MemberDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(exclude = "boards")
public class Member {


    @Id
    @Column(name = "member_id")
    private String uid;

    private String upw;

    private String uname;

    @Column(nullable = false, unique = true)
    private String email;

    private int age;

    @OneToMany(mappedBy = "member")
    List<Board> boards = new ArrayList<Board>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "member")
    private List<MemberRole> roles;

    @CreationTimestamp
    @Column(name = "sys_creation_date", nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "sys_update_date", nullable = false)
    private Timestamp updatedAt;


}
