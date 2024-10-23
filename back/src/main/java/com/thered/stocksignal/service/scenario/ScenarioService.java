package com.thered.stocksignal.service.scenario;

import com.thered.stocksignal.app.dto.ScenarioDto;

import java.util.List;


public interface ScenarioService {
    List<ScenarioDto.ScenarioResponseDto> getScenario(Long userId);
}
