package com.jun.jpacommunity.controller;

import com.jun.jpacommunity.dto.JobTicket;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsonApiController {

    @PostMapping("/convJob")
    public String convJob(@RequestBody JobTicket jobTicket){

        System.out.println(jobTicket.getSrcFile());
        System.out.println(jobTicket.getDestFile());
        System.out.println(jobTicket.getJobId());

        return "good!!";
    }

}
