package com.thered.stocksignal.app.controller;

import com.thered.stocksignal.apiPayload.ApiResponse;
import com.thered.stocksignal.apiPayload.Status;
import com.thered.stocksignal.app.dto.kakao.KakaoLoginDto;
import com.thered.stocksignal.domain.entity.User;
import com.thered.stocksignal.service.kakao.KakaoLoginService;
import com.thered.stocksignal.service.user.UserAccountService;
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
    private final UserAccountService userAccountService;

    @Operation(summary = "프론트로부터 카카오 인가코드 전달받기 좀 돼라라라라ㅏㄹ")
    @Parameter(name = "code", description = "카카오에서 받은 인카코드, RequestParam")
    @GetMapping("/login")
    public ApiResponse<?> kakaoLoginCode(@RequestParam("code") String code){

        String token = kakaoLoginService.getKakaoToken(code);
        KakaoLoginDto.KakaoUserInfoDto kakaoUserInfoDto = kakaoLoginService.getKakaoUserInfo(token);


        return ApiResponse.onSuccess(Status.LOGIN_SUCCESS, kakaoUserInfoDto);
    }

    @Operation(summary = "백엔드에서 인가코드 확인용으로, 사용하지 않는 API입니다.")
    @GetMapping("/callback")
    public String testInga(@RequestParam("code") String code){
        return code;
    }

    @Operation(summary = "백엔드에서 토큰 정보 확인용으로, 사용하지 않는 API입니다.")
    @GetMapping("/valid")
    public void testToken(@RequestParam("code") String token){
        kakaoLoginService.isValidToken(token);
    }

}
