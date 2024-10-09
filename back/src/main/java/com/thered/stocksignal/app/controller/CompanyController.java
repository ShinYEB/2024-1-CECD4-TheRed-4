package com.thered.stocksignal.app.controller;

import com.thered.stocksignal.apiPayload.ApiResponse;
import com.thered.stocksignal.apiPayload.Status;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.thered.stocksignal.app.dto.CompanyDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company")
public class CompanyController {

    @GetMapping("/{companyName}")
    @Operation(summary = "회사 정보 조회", description = "회사 정보를 조회합니다.")
    public ApiResponse<Object> getCompanyInfo(@PathVariable String companyName){

        // 예시 데이터
        CompanyResponseDto responseDto = CompanyResponseDto.builder()
                .companyName(companyName)
                .marketCap(10000000000L)
                .build();

        // 응답
        return ApiResponse.onSuccess(Status.COMPANY_INFO_SUCCESS, responseDto);
    }
}