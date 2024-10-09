package com.thered.stocksignal.app.controller;

import com.thered.stocksignal.apiPayload.ApiResponse;
import com.thered.stocksignal.apiPayload.Status;
import com.thered.stocksignal.app.dto.QuestionDto.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/question")
public class QuestionController {

    @GetMapping("/{keyword}")
    @Operation(summary = "키워드에 대한 답변 조회", description = "특정 키워드에 대한 답변 정보를 조회합니다.")
    public ApiResponse<Object> getQuestionInfo(@PathVariable String keyword) {

        // 예시 데이터
        String answer = "시가총액이란 특정 회사의 주식이 현재 시장에서 거래되는 가격을 기준으로 계산된 총 가치를 의미합니다. 일반적으로 1천억원 이하를 소형주, 1천억원 이상을 중형주, 1조원 이상을 대형주라고 부릅니다.";

        // 응답
        QuestionResponseDto responseDto = QuestionResponseDto.builder()
                .answer(answer)
                .build();

        return ApiResponse.onSuccess(Status.QUESTION_FOUND, responseDto);
    }
}
