package com.jun.jpacommunity.repository;

import com.jun.jpacommunity.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

}
