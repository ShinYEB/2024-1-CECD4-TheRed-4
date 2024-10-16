package com.thered.stocksignal.service.company;

import java.util.Optional;

import static com.thered.stocksignal.app.dto.CompanyDto.*;
import static com.thered.stocksignal.app.dto.StockDto.*;

public interface CompanyService {

    // 회사명 -> 종목 코드 조회
    Optional<CompanyCodeResponseDto> findCodeByName(String companyName);

    // 회사명 -> 로고 조회
    Optional<CompanyLogoResponseDto> findLogoByName(String companyName);

    // 종목 코드 -> 회사 정보 (분석 탭에 들어갈 내용)
    Optional<CompanyInfoResponseDto> findCompanyInfoByCode(String companyCode, String accessToken, String appKey, String appSecret);

    // 종목 코드 -> 현재가 조회
    Optional<CurrentPriceResponseDto> findCurrentPriceByCode(String companyCode, String accessToken, String appKey, String appSecret);

    // 종목 코드 -> 일봉 조회
    Optional<PeriodPriceResponseDto> findPeriodPriceByCode(String companyCode, String startDate, String endDate, String accessToken, String appKey, String appSecret);
}

