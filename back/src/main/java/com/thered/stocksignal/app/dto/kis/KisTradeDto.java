package com.thered.stocksignal.app.dto.kis;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.result.Output;

import java.util.List;

public class KisTradeDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TradeResponseDto{

        String rt_cd;
        String msg_cd;
        String msg1;
        @JsonProperty("output")
        List<Output> outputs;

        @Getter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Output {
            String KRX_FWDG_ORD_ORGNO;
            String ODNO;
            String ORD_TMD;
        }
    }
}
