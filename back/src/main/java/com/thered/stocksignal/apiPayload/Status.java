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

    MY_BALANCE_SUCCESS("200", "SUCCESS", "잔고 조회에 성공했습니다."),

    GET_USERINFO_SUCCESS("200", "SUCCESS", "회원 정보가 조회되었습니다."),
    SET_USERINFO_SUCCESS("200", "SUCCESS", "회원 정보가 수정되었습니다."),
    NICKNAME_SUCCESS("200", "SUCCESS", "사용 가능한 닉네임입니다."),
  
    TRADE_BUY_SUCCESS("200", "SUCCESS", "매수를 성공했습니다."),
    TRADE_SELL_SUCCESS("200", "SUCCESS", "매도를 성공했습니다."),
  
    MYSTOCK_SUCCESS("200", "SUCCESS", "나의 전체 주식현황을 읽는데 성공했습니다."),
    MYSTOCK_SHORT_SUCCESS("200", "SUCCESS", "나의 주식현황 요약을 읽는데 성공했습니다.");
  
    private final String code;
    private final String result;
    private final String message;
}
