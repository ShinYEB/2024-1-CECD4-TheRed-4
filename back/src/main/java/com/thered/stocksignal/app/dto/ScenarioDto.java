package com.thered.stocksignal.app.dto;

import com.thered.stocksignal.domain.enums.BuysellType;
import com.thered.stocksignal.domain.enums.MethodType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class ScenarioDto {

    @Getter
    @Setter
    @Builder
    public static class ScenarioResponseDto {
        private Long scenarioId;
        private String scenarioName;
        private String companyName;
        private Long initialPrice;
        private Long currentPrice;
    }

    @Getter
    @Setter
    @Builder
    public static class ConditionResponseDto {
        private Long conditionId;
        private BuysellType buysellType;
        private MethodType methodType;
        private Long initialPrice;
        private Long currentPrice;
        private Long targetPrice1;
        private Long targetPrice2;
        private Long targetPrice3;
        private Long targetPrice4;
        private Long quantity;
    }
}
