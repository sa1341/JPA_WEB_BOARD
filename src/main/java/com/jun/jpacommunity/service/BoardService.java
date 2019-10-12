package com.jun.jpacommunity.service;

import com.jun.jpacommunity.domain.Board;
import com.jun.jpacommunity.repository.BoardRepository;
import com.jun.jpacommunity.repository.BoardSearch;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public void save(Board board){
        boardRepository.save(board);
    }

    @Transactional
    public Optional<Board>
    findOne(Long Id){
        return boardRepository.findById(Id);
    }

    // 페이지 번호는 실제로 0부터 시작합니다.  getPageSize()는 실제 페이지당 데이터 수를 의미합니다.
    /*  public Page<Board> findBoardList(Pageable pageable){
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize());
        return boardRepository.findAll(pageable);
    }
    */

    @Transactional
    public Board findBoardById(Long id){
        return boardRepository.findById(id).orElse(new Board());
    }

    @Transactional
    public Page<Board> findAll(Predicate predicate, Pageable pageable){
        return boardRepository.findAll(predicate, pageable);
    }

    public Predicate makePredicate(BoardSearch boardSearch){
        return boardRepository.makePredicate(boardSearch);
    }


}
