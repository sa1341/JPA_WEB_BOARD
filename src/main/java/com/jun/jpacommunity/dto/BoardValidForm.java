package com.jun.jpacommunity.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class BoardValidForm {

    @NotEmpty(message = "제목을 넣으셔야 합니다.")
    private  String title;

    private  String content;


}
