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
    STOCK_CODE_SUCCESS("200", "SUCCESS", "종목 코드 조회에 성공했습니다."),
    CURRENT_DATA_SUCCESS("200", "SUCCESS", "현재가 조회에 성공했습니다."),
    MINUTE_DATA_SUCCESS("200", "SUCCESS", "분봉 조회에 성공했습니다."),
    PERIOD_DATA_SUCCESS("200", "SUCCESS", "기간별 조회에 성공했습니다."),
    AI_PREDICTION_SUCCESS("200", "SUCCESS", "AI 예상 주가 조회에 성공했습니다.");

    private final String code;
    private final String result;
    private final String message;
}
