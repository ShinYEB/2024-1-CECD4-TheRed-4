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
    GET_USERINFO_SUCCESS("200", "SUCCESS", "회원 정보가 조회되었습니다."),
    SET_USERINFO_SUCCESS("200", "SUCCESS", "회원 정보가 수정되었습니다."),
    NICKNAME_SUCCESS("200", "SUCCESS", "사용 가능한 닉네임입니다.");

    private final String code;
    private final String result;
    private final String message;
}
