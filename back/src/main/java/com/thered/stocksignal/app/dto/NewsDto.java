package com.thered.stocksignal.app.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class NewsDto {

    @Getter
    @Builder
    public static class NewsResponseDto {
        private String title; // 뉴스 제목
        private String url;   // 뉴스 URL
    }

    @Getter
    @Builder
    public static class NewsListResponseDto {
        private List<NewsResponseDto> data; // 뉴스 항목 리스트
    }
}

