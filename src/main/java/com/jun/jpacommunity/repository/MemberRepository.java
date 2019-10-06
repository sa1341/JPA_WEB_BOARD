package com.jun.jpacommunity.repository;

import com.jun.jpacommunity.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;


    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(String Id){
       return em.find(Member.class, Id);
    }



}
