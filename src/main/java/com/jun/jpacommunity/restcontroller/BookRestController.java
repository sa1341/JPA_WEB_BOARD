package com.jun.jpacommunity.restcontroller;

import com.jun.jpacommunity.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;

public class BookRestController {

    @Autowired
    BookRestController bookRestController;


    public Book getRestBooks(){
        return bookRestController.getRestBooks();
    }

}
