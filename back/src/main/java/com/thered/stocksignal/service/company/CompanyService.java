package com.thered.stocksignal.service.company;

import static com.thered.stocksignal.app.dto.CompanyDto.*;

public interface CompanyService {

    // 회사명 -> 종목 코드 조회
    String findCodeByName(String companyName);

    // 회사명 -> 로고 조회
    String findLogoByName(String companyName);

    // 종목 코드 -> 회사 정보 (분석 탭에 들어갈 내용)
    CompanyInfoResponseDto findCompanyInfoByCode(String companyCode, String accessToken, String appKey, String appSecret);
}

