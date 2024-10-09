package com.thered.stocksignal.app.controller;

import com.thered.stocksignal.apiPayload.ApiResponse;
import com.thered.stocksignal.apiPayload.Status;
import com.thered.stocksignal.service.company.CompanyService;
import com.thered.stocksignal.app.dto.CompanyDto.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/code/{companyName}")
    @Operation(summary = "종목 코드 조회", description = "회사명으로 종목 코드를 가져옵니다.")
    public ApiResponse<CompanyCodeResponseDto> getCompanyCode(@PathVariable String companyName) {
        String companyCode = companyService.findCodeByName(companyName);
        CompanyCodeResponseDto responseDto = CompanyCodeResponseDto.builder()
                .companyCode(companyCode)
                .build();
        return ApiResponse.onSuccess(Status.COMPANY_CODE_SUCCESS, responseDto);
    }

    @GetMapping("/logo/{companyName}")
    @Operation(summary = "로고 이미지 URL 조회", description = "회사명으로 로고 이미지 URL을 가져옵니다.")
    public ApiResponse<CompanyLogoResponseDto> getCompanyLogo(@PathVariable String companyName) {
        String companyLogo = companyService.findLogoByName(companyName);
        CompanyLogoResponseDto responseDto = CompanyLogoResponseDto.builder()
                .logoImage(companyLogo)
                .build();
        return ApiResponse.onSuccess(Status.COMPANY_LOGO_SUCCESS, responseDto);
    }
}
