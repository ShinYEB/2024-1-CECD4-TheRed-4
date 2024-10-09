package com.thered.stocksignal.service.company;

import com.thered.stocksignal.domain.entity.Company;

import java.util.Optional;

public interface CompanyService {

    // 회사명 -> 종목 코드 조회
    String findCodeByName(String companyName);

    // 회사명 -> 로고 조회
    String findLogoByName(String companyName);
}

