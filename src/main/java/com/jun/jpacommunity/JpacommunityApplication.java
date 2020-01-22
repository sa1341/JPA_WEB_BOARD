package com.jun.jpacommunity;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/*
import org.springframework.security.crypto.password.PasswordEncoder;
*/

@SpringBootApplication
public class JpacommunityApplication {

    private static final String PROPERTIES =
            "spring.config.location="
            +"classpath:/application.properties";

    public static void main(String[] args) {
        new SpringApplicationBuilder(JpacommunityApplication.class)
                .properties(PROPERTIES)
                .run(args);
    }





    // 스프링은 빈으로 생성된 메서드에 파라미터로 DI시키는 메커니즘이 존재합니다. 생성자를 통해서 의존성을 주입하는 방법이랑 유사합니다.

    /*    @Bean
    public CommandLineRunner runner(MemberRepository memberRepository, BoardRepository boardRepository, PasswordEncoder passwordEncoder) throws Exception {
        return (args) -> {

        };
    }*/
}
