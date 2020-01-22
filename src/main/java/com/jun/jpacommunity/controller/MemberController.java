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

import javax.servlet.http.HttpServletResponse;

@RequestMapping("/member")
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/join")
    public void join(){

    }

    //포스트 백 : 자기자신한테 요청을 하는 경우 form 태그에서 action 값이 생략되어 있습니다.
    @PostMapping("/join")
    public String joinPost(@ModelAttribute("member") Member member, HttpServletResponse response) throws Exception{

        System.out.println(member.getUname());
        System.out.println(member.getUid());
        System.out.println(member.getUpw());

        String encryptPw = passwordEncoder.encode(member.getUpw());

        //System.out.println(encryptPw);
        //member.setUpw(encryptPw);

        memberRepository.save(member);


        return "redirect:/board/list";
    }


}
