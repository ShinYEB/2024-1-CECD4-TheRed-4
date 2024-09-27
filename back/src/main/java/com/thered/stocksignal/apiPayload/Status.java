package com.thered.stocksignal.apiPayload;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;



@Getter
@RequiredArgsConstructor(access = PRIVATE)
public enum Status {

    /**
     * SUCCESS CODE
     * 200 : OK
     * 201 : CREATED
     * 204 : NO CONTENT
     * ...
     */

    /**
     * FAILURE CODE
     * 400 :
     * 401 :
     * 404 :
     * ...
     */

    LOGIN_SUCCESS("200", "SUCCESS", "로그인이 완료되었습니다."),
    NEWS_SEARCH_SUCCESS("200", "SUCCESS", "뉴스 검색에 성공하였습니다.");

    private final String code;
    private final String result;
    private final String message;
}
