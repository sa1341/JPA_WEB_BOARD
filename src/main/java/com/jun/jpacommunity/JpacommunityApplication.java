package com.jun.jpacommunity;

import com.jun.jpacommunity.domain.Board;
import com.jun.jpacommunity.domain.Member;
import com.jun.jpacommunity.domain.MemberRole;
import com.jun.jpacommunity.domain.enums.BoardType;
import com.jun.jpacommunity.repository.BoardRepository;
import com.jun.jpacommunity.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.stream.IntStream;

@SpringBootApplication
public class JpacommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpacommunityApplication.class, args);
    }

    // 스프링은 빈으로 생성된 메서드에 파라미터로 DI시키는 메커니즘이 존재합니다. 생성자를 통해서 의존성을 주입하는 방법이랑 유사합니다.
/*    @Bean
    public CommandLineRunner runner(MemberRepository memberRepository, BoardRepository boardRepository, PasswordEncoder passwordEncoder) throws Exception {
        return (args) -> {


         Member member = new Member();
         member.("임준영");
         member.setEmail("a79007714@gm.com");
         member.setAge(29);
         member.setUid("sa1341");

         String pwd = "wnsdud2";
         passwordEncoder.encode(pwd);
            System.out.println(pwd);

         member.setUpw(pwd);


         MemberRole memberRole = new MemberRole();
         memberRole.setRoleName("MANAGER");
         member.setRoles(Arrays.asList(memberRole));

         memberRepository.save(member);


         IntStream.rangeClosed(1, 15).forEach(index -> {
                Board board = Board.createBoard(member1.getName(), "제목" + index, "내용" + index, member1);
                boardRepository.save(board);
            });

            IntStream.rangeClosed(16, 30).forEach(index -> {
                Board board = Board.createBoard(member2.getName(), "제목" + index, "내용" + index, member2);
                boardRepository.save(board);
            });


        };
    }*/
}
