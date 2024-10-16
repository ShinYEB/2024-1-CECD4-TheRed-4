package com.thered.stocksignal.service.myBalance;

import com.fasterxml.jackson.databind.JsonNode;

import static com.thered.stocksignal.app.dto.MyBalanceDto.*;

public interface MyBalanceService {

    // 내 잔고 조회
    MyBalanceResponseDto getMyBalance(String accountNumber, String accessToken, String appKey, String appSecret);

}
