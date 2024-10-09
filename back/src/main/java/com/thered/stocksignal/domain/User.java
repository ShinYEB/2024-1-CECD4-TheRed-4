package com.thered.stocksignal.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;

    private Boolean notification;

    @Enumerated(EnumType.STRING)
    private OauthType oauthType;

    private String oauthId;

    private String secretKey;

    private String appKey;

    private Boolean isKisLinked;

    public enum OauthType {
        KAKAO,
        NAVER
    }
}

