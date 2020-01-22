package com.jun.jpacommunity.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    BASIC("ROLE_BASIC", "손님"),
    USER("ROLE_USER", "일반 사용자");


    private final String key;
    private final String title;


}
