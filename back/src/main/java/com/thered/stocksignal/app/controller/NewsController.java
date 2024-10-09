package com.thered.stocksignal.app.controller;

import com.thered.stocksignal.apiPayload.ApiResponse;
import com.thered.stocksignal.apiPayload.Status;
import com.thered.stocksignal.app.dto.NewsDto.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news")
public class NewsController {

    @GetMapping("/{stockName}")
    @Operation(summary = "뉴스 검색", description = "특정 종목에 대한 뉴스를 검색합니다.")
    public ApiResponse<Object> getNews(@PathVariable String stockName) {

        // 예시 데이터
        List<NewsResponseDto> newsList = List.of(

                NewsResponseDto.builder()
                        .title("[단독] 삼성전자, 파운드리 생산라인 줄줄이 ‘셧다운’… 설비투자")
                        .url("https://n.news.naver.com/mnews/article/366/0001020797?sid=105")
                        .build(),
                NewsResponseDto.builder()
                        .title("삼성전자, 4조 투자했는데...기술 빼돌려 중국으로 유출")
                        .url("https://n.news.naver.com/mnews/article/050/0000080262?sid=101")
                        .build(),
                NewsResponseDto.builder()
                        .title("삼성전자 '8세대 V낸드' 탑재한 SSD '990 EVO 플러스' 출시")
                        .url("https://n.news.naver.com/mnews/article/001/0014948810?sid=101")
                        .build(),
                NewsResponseDto.builder()
                        .title("전영현도 샀다…삼성전자 임원, 줄줄이 자사주 매입")
                        .url("https://n.news.naver.com/mnews/article/003/0012808774?sid=101")
                        .build(),
                NewsResponseDto.builder()
                        .title("삼성 HBM 수요 폭증…비관론 일축")
                        .url("https://www.hankyung.com/article/2024092605281")
                        .build()
        );

        // 응답
        NewsListResponseDto responseDto = NewsListResponseDto.builder()
                .data(newsList)
                .build();

        return ApiResponse.onSuccess(Status.NEWS_SEARCH_SUCCESS, responseDto);
    }
}
