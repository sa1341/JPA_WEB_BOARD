package com.jun.jpacommunity.controller;


import com.jun.jpacommunity.domain.Member;
import com.jun.jpacommunity.security.JpaSecurityUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
public class SecurityController {


    @GetMapping("/security")
    public ResponseEntity<String> currentUserName(Principal principal){


        /* Spring Security를 이용하여 사용자의 정보를 찾는 방법
        1.
        Authentcation authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());

        2.User 클래스로 형변환하여 정보를 조회하는 방법
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user.getUsername());
        */
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        JpaSecurityUser user = (JpaSecurityUser) userDetails;
        System.out.println(user.getMember().getEmail());


        return new ResponseEntity<>(principal.getName(), HttpStatus.OK);
    }
}

