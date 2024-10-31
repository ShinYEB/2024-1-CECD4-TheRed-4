package com.thered.stocksignal.service.scenario;

import com.thered.stocksignal.app.dto.ScenarioDto.ConditionRequestDto;
import com.thered.stocksignal.app.dto.ScenarioDto.ConditionResponseDto;
import com.thered.stocksignal.app.dto.ScenarioDto.ScenarioRequestDto;
import com.thered.stocksignal.app.dto.ScenarioDto.ScenarioResponseDto;
import com.thered.stocksignal.app.dto.StockDto;

import java.util.List;


public interface ScenarioService {
    List<ScenarioResponseDto> getScenario(Long userId);

    boolean createScenario(Long userId, ScenarioRequestDto scenarioCreateDto);

    boolean deleteScenario(Long userId, Long scenarioId);

    List<ConditionResponseDto> getConditions(Long userId, Long scenarioId);

    boolean addCondition(Long userId, ConditionRequestDto condtionRequestDto);

    boolean deleteCondition(Long userId, Long conditionId);

    boolean checkAutoTrade(Long userId, List<StockDto.RealTimeStockInfoDto> stockInfoDtoList);

}
