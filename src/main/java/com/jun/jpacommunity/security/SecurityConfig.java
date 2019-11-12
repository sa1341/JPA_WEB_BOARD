package com.jun.jpacommunity.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//웹 보안 활성화 시키는 어노테이
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
      log.info("security config.......");


      // 웹과 관련된 다양한 보안 설정을 걸우주는 역할을 합니다.
      http.authorizeRequests().antMatchers("/guest/**").permitAll();

      // authorizeRequests()는 시큐리티 처리에 HttpServletRequest를 이용합니다.
      http.authorizeRequests().antMatchers("/manager/**").hasRole("MANAGER");

      http.formLogin();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{

        log.info("build Auth global.....");

        auth.inMemoryAuthentication()
                .withUser("user")
                .password("{noop}1111}")
                .roles("MANAGER");

    }



}
