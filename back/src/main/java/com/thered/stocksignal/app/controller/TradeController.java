package com.thered.stocksignal.app.controller;

import com.thered.stocksignal.apiPayload.ApiResponse;
import com.thered.stocksignal.apiPayload.Status;
import com.thered.stocksignal.app.dto.TradeDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trade")
public class TradeController {

    @Operation(summary = "주식 매수")
    @PostMapping("/buy")
    public ApiResponse<?> buyStock(@RequestBody TradeDto.buyRequestDto dto){
        return ApiResponse.onSuccess(Status.TRADE_BUY_SUCCESS, null);
    }

    @Operation(summary = "주식 매도")
    @PostMapping("/sell")
    public ApiResponse<?> sellStock(@RequestBody TradeDto.sellRequestDto dto){
        return ApiResponse.onSuccess(Status.TRADE_SELL_SUCCESS, null);
    }



}
