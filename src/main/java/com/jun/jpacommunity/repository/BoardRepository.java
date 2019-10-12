package com.jun.jpacommunity.repository;

import com.jun.jpacommunity.domain.Board;
import com.jun.jpacommunity.domain.QBoard;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;


public interface BoardRepository extends CrudRepository<Board, Long>, QuerydslPredicateExecutor<Board> {

    // 자바 8부터 인터페이스에 디폴트 메소드가 추가가 가능해졌습니다.
    public default Predicate makePredicate(BoardSearch boardSearch) {

        String type = boardSearch.getType();
        String keyword = boardSearch.getKeyword();

        // 메소드 체인형식으로 동적으로 조건절을 추가해주는 BooleanBuilder 객체 생성
        BooleanBuilder builder = new BooleanBuilder();

        QBoard board = QBoard.board;

        builder.and(board.id.gt(0));

        System.out.println( "type:" + type);
        System.out.println( "keyword:" + keyword);


        if(type == null){
            return builder;
        }

        switch(type){
            case "t":
                builder.and(board.title.like("%" + keyword + "%"));
                break;

            case "c":
                builder.and(board.content.like("%" + keyword + "%"));
                break;

            case "w":
                builder.and(board.member.name.like("%" + keyword + "%"));
                break;
        }
        return builder;
    }
}