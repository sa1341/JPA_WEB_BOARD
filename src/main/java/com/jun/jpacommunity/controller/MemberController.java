package com.jun.jpacommunity.controller;

import com.jun.jpacommunity.domain.Member;
import com.jun.jpacommunity.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/join")
    public void join(){

    }

    @PostMapping("/join")
    public String joinPost(@ModelAttribute("member") Member member){

        System.out.println(member.getUname());
        System.out.println(member.getUid());
        System.out.println(member.getUpw());

        String encryptPw = passwordEncoder.encode(member.getUpw());

        System.out.println(encryptPw);
        member.setUpw(encryptPw);

        memberRepository.save(member);

        return "/member/joinResult";
    }

}
