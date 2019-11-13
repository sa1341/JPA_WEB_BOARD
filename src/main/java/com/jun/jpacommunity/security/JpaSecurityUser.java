package com.jun.jpacommunity.security;

import com.jun.jpacommunity.domain.Member;
import com.jun.jpacommunity.domain.MemberRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JpaSecurityUser extends User {

    private static final String ROLE_PREFIX = "ROLE_";

    private Member member;

    public JpaSecurityUser(Member member){

        super(member.getUid(), member.getUpw(),makeGrantedAuthority(member.getRoles()));

    }

    public JpaSecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities){

        super(username, password, authorities);
    }


    private static List<GrantedAuthority> makeGrantedAuthority(List<MemberRole> roles){

        List<GrantedAuthority> list = new ArrayList<>();

        roles.forEach(role -> list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getRoleName())));

        return list;
    }

}
