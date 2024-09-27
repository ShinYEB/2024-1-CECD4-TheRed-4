package com.thered.stocksignal.app.dto;

import lombok.Builder;
import lombok.Getter;

//날짜에 따라 종가 or 현재가 등 조금씩 필드가 달라져서 가격 클래스를 전부 개별적으로 분리했습니다.

public class StockDto {

    @Getter
    @Builder
    // 종목코드
    public static class StockCodeResponseDto {
        private String stockName; // 종목 이름
        private String stockCode; // 종목 코드
    }

    @Getter
    @Builder
    // 현재가 정보
    public static class CurrentDataResponseDto {
        private Integer currentPrice;    // 현재가
        private Integer openPrice;       // 시가
        private Integer highPrice;       // 고가
        private Integer lowPrice;        // 저가
        private Integer upperLimitPrice; // 상한가
        private Integer lowerLimitPrice; // 하한가
    }

    @Getter
    @Builder
    // 분봉 정보
    public static class MinuteDataResponseDto {
        private String date;      // 날짜
        private String time;      // 시분초
        private Integer openPrice; // 시가
        private Integer closePrice; // 종가
        private Integer highPrice; // 고가
        private Integer lowPrice;  // 저가
    }

    @Getter
    @Builder
    // 기간별 봉 정보 (일봉이상)
    public static class PeriodDataResponseDto {
        private String date;      // 날짜
        private Integer openPrice; // 시가
        private Integer closePrice; // 종가
        private Integer highPrice; // 고가
        private Integer lowPrice;  // 저가
        private Integer volume;    //거래량
    }

    @Getter
    @Builder
    // AI 예측 주가 정보
    public static class PredictionDataResponseDto {
        private String date;      // 날짜
        private Integer closePrice; // 종가
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyStockResponseDto{
        public String companyName;
        public Integer totalPrice;
        public String rate;
    }


}

