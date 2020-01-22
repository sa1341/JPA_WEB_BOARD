package com.jun.jpacommunity.repository;

import com.jun.jpacommunity.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findByEmail(String email);
}
