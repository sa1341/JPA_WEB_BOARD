package com.jun.jpacommunity.service;

import com.jun.jpacommunity.domain.Board;
import com.jun.jpacommunity.domain.Reply;
import com.jun.jpacommunity.repository.BoardRepository;
import com.jun.jpacommunity.repository.ReplyRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ReplyServiceTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    ReplyRepository replyRepository;

    @Test
    public void 댓글저장_테스트() throws Exception {

        //given
        Optional<Board> optBoard = boardRepository.findById(300L);

        Board findBoard = optBoard.get();

        Reply reply = new Reply();
        reply.setContent("좆까 니들의 개병신같은 핑계");
        reply.setReplier("임준영");
        reply.setBoard(findBoard);

        //when
        replyRepository.save(reply);

        //then
        assertEquals("임준영", replyRepository.findById(reply.getId()).get().getReplier());


     }


}
