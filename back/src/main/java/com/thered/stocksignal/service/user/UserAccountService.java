package com.thered.stocksignal.service.user;

import com.thered.stocksignal.domain.entity.User;

import java.util.Optional;

public interface UserAccountService {

    Optional<User> saveKakaoUser(String email);

    Optional<User> findByEmail(String email);
}
