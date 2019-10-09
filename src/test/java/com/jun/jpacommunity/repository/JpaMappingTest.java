package com.jun.jpacommunity.repository;

import com.jun.jpacommunity.domain.Board;
import com.jun.jpacommunity.domain.Member;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class JpaMappingTest {

    private final String boardTestTitle = "테스트";
    private final String email = "test@gmail.com";

    @Autowired MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;


    @Before
    public void init(){

        Member member = Member.builder()
                .name("junyoung")
                .password("test")
                .age(28)
                .email(email)
                .build();

        memberRepository.save(member);

        Board board = Board.createBoard(boardTestTitle,"콘텐츠", member);
        boardRepository.save(board);
    }


    @Test
    public void 제대로_생성되었는지_테스트() throws Exception {

        //given

        //when
        Member member = memberRepository.findByEmail(email);

        //then
        assertThat(member.getAge(), is(28));
        assertThat(member.getName(), is("junyoung"));
        assertThat(member.getEmail(), is(email));
        assertThat(member.getPassword(), is("test"));
    }




}
