package com.jun.jpacommunity.service;

import com.jun.jpacommunity.domain.Board;
import com.jun.jpacommunity.domain.Reply;
import com.jun.jpacommunity.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyService {

    private  final ReplyRepository replyRepository;

    @Transactional
    public void save(Reply reply){
        replyRepository.save(reply);
    }

    public List<Reply> getRepliesOfBoard(Board board){
        return replyRepository.getRepliesOfBoard(board);
    }

    @Transactional
    public void updateReply(Reply reply){
        Optional<Reply> optReply = replyRepository.findById(reply.getId());
        Reply findReply = optReply.get();
        findReply.setContent(reply.getContent());
    }


    @Transactional
    public void deleteReplyById(Long id){
        replyRepository.deleteById(id);
    }

}
