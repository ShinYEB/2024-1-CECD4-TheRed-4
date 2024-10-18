package com.thered.stocksignal.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KisUtil {

    public static String getCANO(String account) {
        return account.substring(0, 8);
    }

    public static String getACNT_PRDT_CD(String account) {
        return account.substring(9, 11);
    }
}
