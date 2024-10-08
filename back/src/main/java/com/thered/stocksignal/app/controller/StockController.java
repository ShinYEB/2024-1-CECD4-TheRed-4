package com.thered.stocksignal.app.controller;

import com.thered.stocksignal.apiPayload.ApiResponse;
import com.thered.stocksignal.apiPayload.Status;
import com.thered.stocksignal.app.dto.StockDto.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stock")
public class StockController {

    @GetMapping("/code/{stockName}")
    @Operation(summary = "종목 코드 조회", description = "특정 종목 코드를 조회합니다.")
    public ApiResponse<Object> getStockCode(@PathVariable String stockName) {

        // 예시 데이터
        StockCodeResponseDto responseDto = StockCodeResponseDto.builder()
                .stockName(stockName)
                .stockCode("A123456")
                .build();

        // 응답
        return ApiResponse.onSuccess(Status.STOCK_CODE_SUCCESS, responseDto);
    }

    @GetMapping("/current/{stockName}")
    @Operation(summary = "현재가 조회", description = "특정 종목의 현재가를 조회합니다.")
    public ApiResponse<Object> getCurrentData(@PathVariable String stockName) {

        // 예시 데이터
        CurrentDataResponseDto responseDto = CurrentDataResponseDto.builder()
                .currentPrice(85000)
                .openPrice(84000)
                .highPrice(85500)
                .lowPrice(83000)
                .upperLimitPrice(90000)
                .lowerLimitPrice(80000)
                .build();

        // 응답
        return ApiResponse.onSuccess(Status.CURRENT_DATA_SUCCESS, responseDto);
    }

    @GetMapping("/minute")
    @Operation(summary = "분봉 조회", description = "특정 종목의 분봉 데이터를 조회합니다.")
    public ApiResponse<Object> getMinuteData(@RequestParam String stockName, @RequestParam String time) {

        // 예시 데이터
        List<MinuteDataResponseDto> responseDto = List.of(

                MinuteDataResponseDto.builder()
                        .date("2022-05-09")
                        .time("095900")
                        .openPrice(79000)
                        .closePrice(80000)
                        .highPrice(80500)
                        .lowPrice(78500)
                        .build(),
                MinuteDataResponseDto.builder()
                        .date("2022-05-09")
                        .time("100000")
                        .openPrice(79500)
                        .closePrice(81000)
                        .highPrice(81500)
                        .lowPrice(79000)
                        .build()
        );

        return ApiResponse.onSuccess(Status.MINUTE_DATA_SUCCESS, responseDto);
    }

    @GetMapping("/period")
    @Operation(summary = "기간별 조회", description = "특정 종목의 일봉/주봉/월봉/연봉 데이터를 조회합니다.")
    public ApiResponse<Object> getPeriodData(
            @RequestParam String stockName,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam String timeFrame) {

        // 예시 데이터
        List<PeriodDataResponseDto> responseDto = List.of(

                PeriodDataResponseDto.builder()
                        .date("2022-05-09")
                        .openPrice(79000)
                        .closePrice(80000)
                        .highPrice(80500)
                        .lowPrice(78500)
                        .volume(150000)
                        .build(),
                PeriodDataResponseDto.builder()
                        .date("2022-05-08")
                        .openPrice(78000)
                        .closePrice(79000)
                        .highPrice(79500)
                        .lowPrice(77500)
                        .volume(120000)
                        .build()
        );

        // 응답
        return ApiResponse.onSuccess(Status.PERIOD_DATA_SUCCESS, responseDto);
    }

    @GetMapping("/prediction")
    @Operation(summary = "AI 예상 주가 조회", description = "특정 종목의 AI 예상 주가를 조회합니다.")
    public ApiResponse<Object> getPrediction(@RequestParam String stockName,
                                             @RequestParam String timeFrame) {

        // 예시 데이터
        List<PredictionDataResponseDto> responseDto = List.of(

                PredictionDataResponseDto.builder()
                        .date("2022-05-09")
                        .closePrice(90000)
                        .build(),
                PredictionDataResponseDto.builder()
                        .date("2022-05-08")
                        .closePrice(91000)
                        .build()
        );

        // 응답
        return ApiResponse.onSuccess(Status.AI_PREDICTION_SUCCESS, responseDto);
    }
}
