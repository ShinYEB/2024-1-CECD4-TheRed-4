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
    TRADE_BUY_SUCCESS("200", "SUCCESS", "매수를 성공했습니다."),
    TRADE_SELL_SUCCESS("200", "SUCCESS", "매도를 성공했습니다."),
    LOGIN_SUCCESS("200", "SUCCESS", "로그인이 완료되었습니다.");

    private final String code;
    private final String result;
    private final String message;
}
