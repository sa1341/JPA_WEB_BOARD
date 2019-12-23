package com.jun.jpacommunity.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtTest {


    @Test
    public void jwt_Test() throws Exception {

        //given
        String jwtString = Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setHeaderParam("issueDate", System.currentTimeMillis())
                .setSubject("내용")
                .signWith(SignatureAlgorithm.HS512, "aaaa")
                .compact();


        System.out.println(jwtString);


        //when

        //then
     }
}
