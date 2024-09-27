package com.thered.stocksignal.app.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class MyBalanceDto {

    @Getter
    @Builder
    public static class MyStockDetailResponseDto{

        private String stockName;
        private Integer quantity;
        private Integer avgPrice;
        private Integer currentPrice;
        private Integer PLAmount;
        private Double PLRatio;
    }

    @Getter
    @Builder
    public static class MyBalanceResponseDto {
        private final Integer cash; // 예수금
        private final List<MyStockDetailResponseDto> stocks;
    }
}
