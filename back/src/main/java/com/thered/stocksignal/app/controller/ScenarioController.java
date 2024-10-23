package com.thered.stocksignal.app.controller;

import com.thered.stocksignal.apiPayload.ApiResponse;
import com.thered.stocksignal.apiPayload.Status;
import com.thered.stocksignal.app.dto.ScenarioDto;
import com.thered.stocksignal.app.dto.ScenarioDto.ConditionRequestDto;
import com.thered.stocksignal.app.dto.ScenarioDto.ConditionResponseDto;
import com.thered.stocksignal.app.dto.ScenarioDto.ScenarioRequestDto;
import com.thered.stocksignal.app.dto.ScenarioDto.ScenarioResponseDto;
import com.thered.stocksignal.service.scenario.ScenarioService;
import com.thered.stocksignal.service.user.UserAccountService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/scenario")
public class ScenarioController {

    private final ScenarioService scenarioService;
    private final UserAccountService userAccountService;

    @GetMapping()
    @Operation(summary = "시나리오 조회", description = "시나리오를 조회합니다.")
    public ApiResponse<List<ScenarioResponseDto>> getScenario(@RequestHeader("Authorization") String token) {

        Long userId = userAccountService.getUserIdFromToken(token);
        if(userId == -1) return ApiResponse.onFailure(Status.TOKEN_INVALID);

        List<ScenarioResponseDto> responseDto = scenarioService.getScenario(
                userId
        );

        return ApiResponse.onSuccess(Status.SCENARIO_FOUND, responseDto);
    }

    @PostMapping("/create")
    @Operation(summary = "시나리오 생성", description = "새로운 시나리오를 생성합니다.")
    public ApiResponse<String> createScenario(
            @RequestHeader("Authorization") String token,
            @RequestBody ScenarioRequestDto newScenario) {

        Long userId = userAccountService.getUserIdFromToken(token);
        if (userId == -1) return ApiResponse.onFailure(Status.TOKEN_INVALID);

        boolean responseDto = scenarioService.createScenario(userId, newScenario);
        if(!responseDto){
            return ApiResponse.onFailure(Status.SCENARIO_CREATION_FAILED, null);
        }
        return ApiResponse.onSuccess(Status.SCENARIO_CREATED, null);
    }

    @DeleteMapping("/{scenarioId}/delete")
    @Operation(summary = "시나리오 삭제", description = "특정 시나리오를 삭제합니다.")
    public ApiResponse<Void> deleteScenario(
            @RequestHeader("Authorization") String token,
            @PathVariable Long scenarioId) {

        Long userId = userAccountService.getUserIdFromToken(token);
        if (userId == -1) return ApiResponse.onFailure(Status.TOKEN_INVALID);

        boolean isDeleted = scenarioService.deleteScenario(userId, scenarioId);
        if (!isDeleted) {
            return ApiResponse.onFailure(Status.SCENARIO_DELETION_FAILED, null);
        }
        return ApiResponse.onSuccess(Status.SCENARIO_DELETED, null);
    }

    @GetMapping("/{scenarioId}/conditions")
    @Operation(summary = "시나리오 조건 조회", description = "특정 시나리오의 조건을 조회합니다.")
    public ApiResponse<List<ConditionResponseDto>> getConditions(
            @RequestHeader("Authorization") String token,
            @PathVariable Long scenarioId) {

        Long userId = userAccountService.getUserIdFromToken(token);
        if (userId == -1) return ApiResponse.onFailure(Status.TOKEN_INVALID);

        List<ConditionResponseDto> responseDto = scenarioService.getConditions(userId, scenarioId);

        return ApiResponse.onSuccess(Status.CONDITION_FOUND, responseDto);
    }

    @PatchMapping("/{scenarioId}/conditions/create")
    @Operation(summary = "시나리오 조건 추가", description = "특정 시나리오에 조건을 추가합니다.")
    public ApiResponse<String> addCondition(
            @RequestHeader("Authorization") String token,
            @RequestBody ConditionRequestDto newScenario) {

        Long userId = userAccountService.getUserIdFromToken(token);
        if (userId == -1) return ApiResponse.onFailure(Status.TOKEN_INVALID);

        boolean responseDto = scenarioService.addCondition(userId, newScenario);

        if(!responseDto){
            return ApiResponse.onFailure(Status.ADDING_CONDITION_FAILED, null);
        }
        return ApiResponse.onSuccess(Status.CONDITION_ADDED, null);
    }

}
