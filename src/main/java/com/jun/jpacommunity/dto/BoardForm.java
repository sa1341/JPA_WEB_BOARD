package com.jun.jpacommunity.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class BoardForm {

    private Long id;

    @NotEmpty(message = "제목을 넣으셔야 합니다.")
    private  String title;

    @NotEmpty(message = "내용을 넣어주셔야 합니다.")
    private  String content;

}
