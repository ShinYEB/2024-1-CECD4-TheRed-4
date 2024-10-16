package com.thered.stocksignal.app.controller;

import com.thered.stocksignal.apiPayload.ApiResponse;
import com.thered.stocksignal.apiPayload.Status;
import com.thered.stocksignal.app.dto.NewsDto.NewsResponseDto;
import com.thered.stocksignal.service.news.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/{stockName}")
    public ApiResponse<List<NewsResponseDto>> getNews(@PathVariable String stockName) {
        Optional<List<NewsResponseDto>> responseDto = newsService.searchNews(stockName);

        return responseDto
                .map(dto -> ApiResponse.onSuccess(Status.NEWS_FOUND, dto))
                .orElseGet(() -> ApiResponse.onFailure(Status.NEWS_NOT_FOUND));
    }
}
