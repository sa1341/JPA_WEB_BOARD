package com.jun.jpacommunity.repository;

import com.jun.jpacommunity.domain.Board;
import com.jun.jpacommunity.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long>, QuerydslPredicateExecutor<Reply> {


    @Query("select r from Reply r where r.board = ?1 " + " and r.id > 0 ORDER BY r.id ASC")
    public List<Reply> getRepliesOfBoard(Board board);



}

