package com.thered.stocksignal.service.user;

import com.thered.stocksignal.domain.entity.User;
import com.thered.stocksignal.domain.enums.OauthType;
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

    @Override
    public Optional<User> saveKakaoUser(String email) {
        User newUser = User.builder()
                .email(email)
                .nickname(email)
                .oauthType(OauthType.KAKAO)
                .build();
        Optional<User> user = Optional.of(userRepository.save(newUser));
        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
