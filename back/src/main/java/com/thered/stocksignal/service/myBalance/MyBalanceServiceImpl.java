package com.thered.stocksignal.service.myBalance;

import com.thered.stocksignal.kisApi.KisApiRequest;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.thered.stocksignal.app.dto.MyBalanceDto.*;

@Service
@RequiredArgsConstructor
public class MyBalanceServiceImpl implements  MyBalanceService{

    private final KisApiRequest apiRequest;
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    // 내 잔고 조회
    public Optional<MyBalanceResponseDto> getMyBalance(String accountNumber, String accessToken, String appKey, String appSecret) {

        // API url
        String endpoint = "/uapi/domestic-stock/v1/trading/inquire-balance";

        // API 쿼리 파라미터
        String url = apiRequest.buildUrl(endpoint,
                "CANO=" + accountNumber,
                "ACNT_PRDT_CD=01",
                "AFHR_FLPR_YN=N",
                "INQR_DVSN=02",
                "UNPR_DVSN=01",
                "FUND_STTL_ICLD_YN=N",
                "FNCG_AMT_AUTO_RDPT_YN=N",
                "PRCS_DVSN=00",
                "OFL_YN=",
                "CTX_AREA_FK100=",
                "CTX_AREA_NK100="
        );

        // 요청 헤더 생성
        Request request = new Request.Builder()
                .url(url)
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer " + accessToken)
                .addHeader("appkey", appKey)
                .addHeader("appsecret", appSecret)
                .addHeader("tr_id", "VTTC8434R")
                .build();

        try (Response response = client.newCall(request).execute()) {

            // 응답 본문을 JsonNode로 변환
            String jsonResponse = Objects.requireNonNull(response.body()).string();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            MyBalanceResponseDto myBalanceDto = MyBalanceResponseDto.builder().build();
            List<StockResponseDto> stocks = new ArrayList<>(); // 주식 리스트

            // output1 : 보유 주식 개별 정보
            for (JsonNode stockNode : jsonNode.path("output1")) {

                StockResponseDto stock = StockResponseDto.builder().build();

                stock.setStockName(stockNode.path("prdt_name").asText());   // 종목명
                stock.setQuantity(stockNode.path("hldg_qty").asLong());  // 수량
                stock.setAvgPrice(stockNode.path("pchs_avg_pric").asLong());  // 매입 평균가
                stock.setCurrentPrice(stockNode.path("prpr").asLong()); // 현재가
                stock.setPL(stockNode.path("evlu_pfls_amt").asLong()); // 손익

                stocks.add(stock); // 해당 주식을 리스트에 추가
            }

            // output2 : 보유 주식 총합 정보
            myBalanceDto.setCash(jsonNode.path("output2").get(0).path("dnca_tot_amt").asLong());  // 예수금
            myBalanceDto.setStocks(stocks);
            myBalanceDto.setTotalStockPrice(jsonNode.path("output2").get(0).path("evlu_amt_smtl_amt").asLong()); // 보유 주식 전체 가치
            myBalanceDto.setTotalStockPL(jsonNode.path("output2").get(0).path("evlu_pfls_smtl_amt").asLong());    // 보유 주식 전체 손익

            return Optional.of(myBalanceDto);

        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
