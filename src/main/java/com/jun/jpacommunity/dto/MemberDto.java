package com.jun.jpacommunity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class MemberDto {

    @NotNull
    private String id;

}
