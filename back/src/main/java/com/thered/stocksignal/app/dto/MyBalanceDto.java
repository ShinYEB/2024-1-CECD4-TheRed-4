package com.thered.stocksignal.app.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MyBalanceDto {
    private long cash;  // 예수금 총 금액
    private List<Stock> stocks;  // 보유 주식 목록
    private long totalStockPrice;  // 보유 주식 전체 가치 (금액)
    private long totalStockPL;  // 보유 주식 전체 손익 (금액)

    @Getter
    @Setter
    @ToString
    public static class Stock {
        private String stockName;  // 종목명
        private long quantity;  // 보유 수량
        private long avgPrice;  // 매입 평균가
        private long currentPrice;  // 현재가
        private long PL;  // 손익 금액
    }
}
