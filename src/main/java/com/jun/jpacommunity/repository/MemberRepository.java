package com.jun.jpacommunity.repository;

import com.jun.jpacommunity.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
    public Member findByEmail(String email);
}
