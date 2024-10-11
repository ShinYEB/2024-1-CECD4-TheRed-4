package com.thered.stocksignal.app.controller;

import com.thered.stocksignal.apiPayload.ApiResponse;
import com.thered.stocksignal.apiPayload.Status;
import com.thered.stocksignal.app.dto.user.UserInfoDto;
import com.thered.stocksignal.service.user.UserAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserInfoController {

    private final UserAccountService userAccountService;

    @Operation(summary = "회원 정보 조회")
    @GetMapping("/info/detail")
    public ApiResponse<?> getUserInfo(){
        UserInfoDto.InfoResponseDto infoResponseDto = new UserInfoDto.InfoResponseDto().builder()
                .nickname("김별명")
                .build();
        return ApiResponse.onSuccess(Status.GET_USERINFO_SUCCESS, infoResponseDto);
    }

    @Operation(summary = "회원 정보 수정")
    @PatchMapping("/info/edit")
    public ApiResponse<?> setUserInfo(@RequestBody UserInfoDto.InfoRequestDto dto){

        return ApiResponse.onSuccess(Status.SET_USERINFO_SUCCESS, null);
    }

    @Operation(summary = "닉네임 중복 확인")
    @GetMapping("/{nickname}/exists")
    public ApiResponse<?> isNicknameExists(@Parameter @PathVariable(value = "nickname") String nickname){
        Boolean isExists = userAccountService.isExistNickname(nickname);
        if(isExists == false) return ApiResponse.onSuccess(Status.NICKNAME_SUCCESS, null);
        return ApiResponse.onSuccess(Status.NICKNAME_INVALID, null);
    }
}
