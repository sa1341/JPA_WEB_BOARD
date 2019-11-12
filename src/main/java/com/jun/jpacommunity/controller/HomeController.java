package com.jun.jpacommunity.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {


    @GetMapping("/home")
    public String home(){
        return "board/form";
    }



    @RequestMapping("/index")
    public String index(){

        log.info("index");
        return "index";
    }


    @RequestMapping("/guest")
    public void forGuest(){
        log.info("guest");
    }


    @RequestMapping("/manager")
    public void forManager(){
        log.info("manager");
    }

    @RequestMapping("/admin")
    public void forAdmin(){
        log.info("admin");
    }

}
