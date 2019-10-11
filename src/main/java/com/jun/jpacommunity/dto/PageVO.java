package com.jun.jpacommunity.dto;

import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
public class PageVO {

    private static final int DEFAULT_SIZE = 10;
    private static final int DEFAULT_MAX_SIZE = 50;

    private int page;
    private int size;

    public PageVO(){
        this.page = 1;
        this.size = DEFAULT_SIZE;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setSize(int size) {
        this.size = size < DEFAULT_SIZE || size > DEFAULT_MAX_SIZE ? DEFAULT_SIZE : size;
    }

    public Pageable makePageable(int direction, String... props){

        Sort.Direction dir = direction == 0 ? Sort.Direction.DESC : Sort.Direction.ASC;

        return PageRequest.of(this.page - 1, this.size, dir, props);
    }


}
