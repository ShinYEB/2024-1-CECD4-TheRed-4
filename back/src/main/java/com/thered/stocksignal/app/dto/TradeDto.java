package com.thered.stocksignal.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TradeDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class buyRequestDto {
        public String scode;
        public int week;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class sellRequestDto{
        public String scode;
        public int week;
    }
}
