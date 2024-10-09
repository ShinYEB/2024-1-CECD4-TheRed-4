package com.thered.stocksignal.app.dto;

import lombok.Builder;
import lombok.Getter;

public class CompanyDto {

    @Getter
    @Builder
    public static class CompanyResponseDto {
        private String companyName; // 회사명
        private Long marketCap;     // 시가총액
    }
}

