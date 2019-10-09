package com.jun.jpacommunity.repository;

import com.jun.jpacommunity.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;



public interface BoardRepository extends JpaRepository<Board, Long> {

}