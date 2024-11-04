/*
package com.thered.stocksignal.app.controller;

import com.thered.stocksignal.apiPayload.ApiResponse;
import com.thered.stocksignal.apiPayload.Status;
import com.thered.stocksignal.app.dto.StockDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main")
public class MainController {

    @Operation(summary = "나의 주식현황 전체")
    @GetMapping("/origin")
    public ApiResponse<?> getMyStocks(){
        StockDto.MyStockResponseDto dto1 = new StockDto.MyStockResponseDto().builder()
                .companyName("삼성전자")
                .totalPrice(250000)
                .rate("+2976.46")
                .build();

        StockDto.MyStockResponseDto dto2 = new StockDto.MyStockResponseDto().builder()
                .companyName("동국전자")
                .totalPrice(200)
                .rate("-96.50")
                .build();

        List<StockDto.MyStockResponseDto> dtos = new ArrayList<>();
        dtos.add(dto1);
        dtos.add(dto2);
        return ApiResponse.onSuccess(Status.MYSTOCK_SUCCESS, dtos);
    }

    @Operation(summary = "나의 주식현황 요약")
    @GetMapping("/short")
    public ApiResponse<?> getMyStocksShort(){
        StockDto.MyStockResponseDto dto1 = new StockDto.MyStockResponseDto().builder()
                .companyName("삼성전자")
                .totalPrice(250000)
                .rate("+2976.46")
                .build();

        StockDto.MyStockResponseDto dto2 = new StockDto.MyStockResponseDto().builder()
                .companyName("동국전자")
                .totalPrice(200)
                .rate("-96.50")
                .build();

        List<StockDto.MyStockResponseDto> dtos = new ArrayList<>();
        dtos.add(dto1);
        dtos.add(dto2);
        return ApiResponse.onSuccess(Status.MYSTOCK_SHORT_SUCCESS, dtos);
    }
}
*/
