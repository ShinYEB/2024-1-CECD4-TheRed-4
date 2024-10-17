package com.thered.stocksignal.service.news;

import com.thered.stocksignal.app.dto.NewsDto.NewsResponseDto;

import java.util.List;
import java.util.Optional;

public interface NewsService {

    Optional<List<NewsResponseDto>> searchNews(String query);
}
