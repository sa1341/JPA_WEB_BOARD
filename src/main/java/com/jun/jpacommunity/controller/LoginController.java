package com.jun.jpacommunity.controller;

import com.jun.jpacommunity.configuration.auth.LoginUser;
import com.jun.jpacommunity.dto.SessionUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class LoginController {

    @GetMapping("/login")
    public void login(){
       log.info("login!!");
    }

    @GetMapping("/accessDenied")
    public void accessDenied(){
        log.info("accessDenied!!");
    }

    @GetMapping("/logout")
    public void logout(){
        log.info("logout!!!");
    }

    @GetMapping("/loginForm")
    public void loginForm(){
        log.info("loginForm!");
    }

    @GetMapping("/sessionLogin")
    public String seesionLogin(Model model, @LoginUser SessionUser user){

        if(user != null){
            log.info("" + user.getEmail() + " " + user.getName());
            model.addAttribute("username", user.getName());
        }

        return "board/list";
    }

}
