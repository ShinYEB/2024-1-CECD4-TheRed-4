package com.thered.stocksignal.app.controller;

import com.thered.stocksignal.apiPayload.ApiResponse;
import com.thered.stocksignal.apiPayload.Status;
import com.thered.stocksignal.service.MyBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.Operation;
import com.thered.stocksignal.app.dto.MyBalanceDto;

import static com.thered.stocksignal.app.dto.MyBalanceDto.*;

@RestController
@RequestMapping("/api/mybalance")
public class MyBalanceController {

    private final MyBalanceService myBalanceService;

    @Autowired
    public MyBalanceController(MyBalanceService myBalanceService) {
        this.myBalanceService = myBalanceService;
    }

    @GetMapping
    @Operation(summary = "주식 잔고 조회", description = "사용자의 주식 잔고 정보를 조회합니다.")
    public ApiResponse<MyBalanceResponseDto> getMyBalance(
            @RequestParam String accountNumber,
            @RequestParam String accessToken,
            @RequestParam String appKey,
            @RequestParam String appSecret) {
        MyBalanceResponseDto responseDto = myBalanceService.getMyBalance(accountNumber, accessToken, appKey, appSecret);

        return ApiResponse.onSuccess(Status.MY_BALANCE_SUCCESS, responseDto);
    }
}




