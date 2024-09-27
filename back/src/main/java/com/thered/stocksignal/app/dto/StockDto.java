package com.thered.stocksignal.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class StockDto {
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
