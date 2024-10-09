package com.thered.stocksignal.app.dto;

import lombok.Builder;
import lombok.Getter;

public class CompanyDto {

    @Getter
    @Builder
    public static class CompanyCodeResponseDto {
        private String companyCode;  // 종목 코드
    }

    @Getter
    @Builder
    public static class CompanyLogoResponseDto {
        private String logoImage;     // 로고 이미지 URL
    }
}


