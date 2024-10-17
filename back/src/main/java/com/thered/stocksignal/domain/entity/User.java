package com.thered.stocksignal.domain.entity;

import com.thered.stocksignal.domain.enums.OauthType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 256)
    private String email;

    @Column(nullable = false, length = 256)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private OauthType oauthType;

    @Column(nullable = true, length = 256)
    private String oauthId;

    @Column(nullable = true, length = 256)
    private String secretKey;

    @Column(nullable = true, length = 256)
    private String appKey;

    @Column(nullable = true)
    private Boolean isKisLinked;

    @Column(nullable = true)
    private String accountNumber;

    @Column(nullable = true, length = 1024)
    private String kisToken;

    public void setNickname(String nickname) {this.nickname = nickname;}

    public void setKisAccount(String secretKey, String appKey, Boolean isKisLinked){
        this.secretKey = secretKey;
        this.appKey = appKey;
        this.isKisLinked = isKisLinked;
    }
}