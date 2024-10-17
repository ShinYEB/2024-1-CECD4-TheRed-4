package com.thered.stocksignal.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TradeDto {
    public String scode; // 종목코드
    public String week; // 주문수량
}
