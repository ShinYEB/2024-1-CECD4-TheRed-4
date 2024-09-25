package com.thered.stocksignal.app.controller;

import com.thered.stocksignal.apiPayload.ApiResponse;
import com.thered.stocksignal.apiPayload.Status;
import com.thered.stocksignal.app.dto.KakaoLoginDto;
import com.thered.stocksignal.service.kakao.KakaoLoginService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/kakao")
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;

    @Operation(summary = "프론트로부터 카카오 인가코드 전달받기")
    @Parameter(name = "code", description = "카카오에서 받은 인카코드, RequestParam")
    @GetMapping("/login")
    public ApiResponse<?> kakaoLoginCode(@RequestParam("code") String code){
        KakaoLoginDto.LoginResponseDto dto = new KakaoLoginDto.LoginResponseDto().builder()
                .userId(1)
                .token("1")
                .build();
        return ApiResponse.onSuccess(Status.LOGIN_SUCCESS, dto);
    }

}
