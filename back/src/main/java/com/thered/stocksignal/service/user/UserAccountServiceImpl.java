package com.thered.stocksignal.service.user;

import com.thered.stocksignal.app.dto.user.UserInfoDto;
import com.thered.stocksignal.domain.entity.User;
import com.thered.stocksignal.domain.enums.OauthType;
import com.thered.stocksignal.jwt.JWTUtil;
import com.thered.stocksignal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserAccountServiceImpl implements UserAccountService{

    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    @Override
    public Optional<User> saveKakaoUser(String email) {
        User newUser = User.builder()
                .email(email)
                .nickname(email)
                .oauthType(OauthType.KAKAO)
                .isKisLinked(false)
                .build();
        Optional<User> user = Optional.of(userRepository.save(newUser));
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Boolean isExistNickname(String nickname) {
        return userRepository.existsByNickname(nickname);

    }

    @Override
    public void editUserNickname(Long userId, String nickname) {
        Optional<User> user = userRepository.findById(userId);
        if(user == null) throw new IllegalArgumentException("존재하지 않는 userId 입니다 : " + userId);
        User updateUser = user.get();
        updateUser.setNickname(nickname);
        userRepository.save(updateUser);
    }

    @Override
    public Optional<User> findById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user == null) throw new IllegalArgumentException("존재하지 않는 userId 입니다 : " + userId);
        return user;
    }

    @Override
    public Long getUserIdFromToken(String token) {
        if (token == null || !token.startsWith("Bearer ")) return -1L;

        String actualToken = token.replace("Bearer ", "");
        Long userId = jwtUtil.getUserId(actualToken);

        return userId;
    }

    @Override
    public void connectKisAccount(Long userId, UserInfoDto.kisAccountRequestDto dto) {
        Optional<User> user = userRepository.findById(userId);
        if(user == null) throw new IllegalArgumentException("존재하지 않는 userId 입니다 : " + userId);
        User updateUser = user.get();

        updateUser.setKisAccount(dto.getSecretKey(), dto.getAppKey(), true);

        userRepository.save(updateUser);
    }
}
