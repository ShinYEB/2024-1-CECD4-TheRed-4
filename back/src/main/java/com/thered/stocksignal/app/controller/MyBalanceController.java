package com.thered.stocksignal.app.controller;

import com.thered.stocksignal.apiPayload.ApiResponse;
import com.thered.stocksignal.apiPayload.Status;
import com.thered.stocksignal.app.dto.MyBalanceDto.MyBalanceResponseDto;
import com.thered.stocksignal.app.dto.MyBalanceDto.MyStockDetailResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mybalance")
public class MyBalanceController {

    @GetMapping
    @Operation(summary = "주식 잔고 조회", description = "사용자의 주식 잔고 정보를 조회합니다.")
    public ApiResponse<Object> getMyStockDetail() {

        // 예시 데이터
        Integer availableCash = 1000000000; // 내 예수금

        List<MyStockDetailResponseDto> stocks = List.of(    // 내 보유주식

                MyStockDetailResponseDto.builder()
                        .stockName("삼성전자")
                        .quantity(500)
                        .avgPrice(80000)
                        .currentPrice(85000)
                        .PLAmount(5000)
                        .PLRatio(0.0625).build(),
                MyStockDetailResponseDto.builder()
                        .stockName("SK하이닉스")
                        .quantity(500)
                        .avgPrice(80000)
                        .currentPrice(85000)
                        .PLAmount(5000)
                        .PLRatio(0.0625).build()
        );

        // 응답
        MyBalanceResponseDto responseDto = MyBalanceResponseDto.builder()
                .cash(availableCash)
                .stocks(stocks)
                .build();

        return ApiResponse.onSuccess(Status.MY_BALANCE_SUCCESS, responseDto);
    }

}
