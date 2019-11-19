package com.jun.jpacommunity.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import javax.sql.DataSource;

//웹 보안 활성화 시키는 어노테이
@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomerAuthenticationProvider customerAuthenticationProvider;

    private final JpaUserService jpaUserService;

    private final DataSource dataSource;

   /* @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }
*/
    @Override
    public void configure(HttpSecurity http) throws Exception {
      log.info("security config.......");


      // 웹과 관련된 다양한 보안 설정을 걸어주는 역할을 합니다.
      http.authorizeRequests().antMatchers("/guest/**").permitAll()
              .antMatchers("/board/list").permitAll()
              .antMatchers("/board/writeArticle").hasRole("BASIC");

      http.authorizeRequests().antMatchers("/resources/**").permitAll();

      // authorizeRequests()는 시큐리티 처리에 HttpServletRequest를 이용합니다.
      http.authorizeRequests().antMatchers("/manager/**").hasRole("MANAGER");

      http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");

      http.authorizeRequests().antMatchers("/board/view").permitAll();

      // customize 한 로그인 페이지 설정
      http.formLogin().loginPage("/loginForm").defaultSuccessUrl("/board/list");

      // 로그아웃시에 세션 무효화, 스프링 시큐리티는 웹을 HttpSession 방식이기 때문에 브라우저가 완전히 종료되면, 로그인한 정보를 잃게 됩니다. 브라우저를 종료하지않을시에.. 로그아웃으로 무효화시킵니다.
      http.logout().logoutUrl("/logout").invalidateHttpSession(true);

      //특정 리소스에 대한 접근 권한이 존재하지 않을때 이동시킬 페이지 설정
      http.exceptionHandling().accessDeniedPage("/accessDenied");

      http.sessionManagement().maximumSessions(1);
      http.rememberMe().key("jpa").userDetailsService(jpaUserService).tokenRepository(getJDBCRepository()).tokenValiditySeconds(60*60*24);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(jpaUserService).passwordEncoder(passwordEncoder());
    }

    private PersistentTokenRepository getJDBCRepository(){
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }

}
