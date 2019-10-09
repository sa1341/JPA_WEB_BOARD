package com.jun.jpacommunity;

import com.jun.jpacommunity.domain.Board;
import com.jun.jpacommunity.domain.Member;
import com.jun.jpacommunity.domain.enums.BoardType;
import com.jun.jpacommunity.repository.BoardRepository;
import com.jun.jpacommunity.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.IntStream;

@SpringBootApplication
public class JpacommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpacommunityApplication.class, args);
    }

    // 스프링은 빈으로 생성된 메서드에 파라미터로 DI시키는 메커니즘이 존재합니다. 생성자를 통해서 의존성을 주입하는 방법이랑 유사합니다.
    @Bean
    public CommandLineRunner runner(MemberRepository memberRepository, BoardRepository boardRepository) throws Exception {
        return (args -> {

            Member member = Member.builder()
                    .email("test@gmail.com")
                    .name("junyoung")
                    .password("12345")
                    .age(29)
                    .build();
            memberRepository.save(member);

            // range와의 차이는 끝 개수의 포함여부 입니다.
            IntStream.rangeClosed(1, 200).forEach(index ->
					boardRepository.save(
							Board.createBoard("게시글" + index, "콘텐트" + index, member, BoardType.free)
					)
            );
        });
    }
}
