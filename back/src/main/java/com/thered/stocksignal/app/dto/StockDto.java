package com.thered.stocksignal.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class StockDto {

    @Getter
    @Builder
    // 현재가 정보
    public static class CurrentDataResponseDto {
        private Long currentPrice;    // 현재가
    }

    /* 안쓸가능성 높음
    @Getter
    @Builder
    // 분봉 정보
    public static class MinuteDataResponseDto {
        private String date;      // 날짜
        private String time;      // 시분초
        private Long openPrice; // 시가
        private Long closePrice; // 종가
        private Long highPrice; // 고가
        private Long lowPrice;  // 저가
    }
     */

    @Getter
    @Builder
    // 일봉
    public static class PeriodDataResponseDto {
        private String date;      // 날짜
        private Long closePrice; // 종가
        private Long tradingVolume;    //거래량
    }

    @Getter
    @Builder
    // AI 예측 주가 정보
    public static class PredictionDataResponseDto {
        private String date;      // 날짜
        private Long closePrice; // 종가
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

