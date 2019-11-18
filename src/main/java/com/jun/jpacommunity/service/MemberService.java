package com.jun.jpacommunity.service;


import com.jun.jpacommunity.domain.Member;
import com.jun.jpacommunity.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;


    @Transactional
    public void save(Member member){
        memberRepository.save(member);
    }


    public Member findById(String username){
        return memberRepository.findById(username).filter(m -> m != null).get();
    }

}
