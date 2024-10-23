package com.thered.stocksignal.service.scenario;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thered.stocksignal.app.dto.CompanyDto;
import com.thered.stocksignal.app.dto.kakao.KakaoLoginDto;
import com.thered.stocksignal.app.dto.ScenarioDto.ScenarioResponseDto;
import com.thered.stocksignal.domain.entity.Scenario;
import com.thered.stocksignal.repository.ScenarioRepository;
import com.thered.stocksignal.service.company.CompanyService;
import com.thered.stocksignal.app.dto.StockDto.CurrentPriceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import jakarta.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ScenarioServiceImpl implements ScenarioService {

    private final ScenarioRepository scenarioRepository;
    private final CompanyService companyService;

//    private String websocketUrl = "ws://ops.koreainvestment.com:31000";
//    private StompSession stompSession;
//
//    @PostConstruct
//    public void connect() {
//        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
//        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
//
//        stompClient.connect(websocketUrl, new StompSessionHandler() {
//            @Override
//            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
//                stompSession = session;
//                subscribeToStock("005930"); // 삼성전자
//            }
//
//            @Override
//            public void handleFrame(StompHeaders headers, Object payload) {
//                // 메시지 처리
//                System.out.println("Received message: " + payload);
//            }
//
//            @Override
//            public Type getPayloadType(StompHeaders headers) {
//                return String.class;
//            }
//
//            @Override
//            public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
//                System.err.println("Error: " + exception.getMessage());
//            }
//
//            @Override
//            public void handleTransportError(StompSession session, Throwable error) {
//                System.err.println("Transport error: " + error.getMessage());
//            }
//        });
//    }
//
//    private void subscribeToStock(String stockCode) {
//        String subscribeMessage = createSubscribeMessage(stockCode);
//        stompSession.send("/topic/subscribe", subscribeMessage);
//    }
//
//    private String createSubscribeMessage(String stockCode) {
//        return String.format("{\"header\": {\"approval_key\": \"발급받은_접속키\", \"custtype\": \"P\", \"tr_type\": \"1\", \"content-type\": \"utf-8\"}, \"body\": {\"input\": {\"tr_id\": \"H0STCNT0\", \"tr_key\": \"%s\"}}}", stockCode);
//    }

    public List<ScenarioResponseDto> getScenario(Long userId) {
        List<Scenario> scenarios = scenarioRepository.findByUserId(userId);

        List<ScenarioResponseDto> scenarioList = new ArrayList<>();

        for (Scenario scenario : scenarios) {
            Long currentPrice = companyService.findCurrentPriceByCode(scenario.getCompany().getCompanyCode(), userId)
                    .map(CurrentPriceResponseDto::getCurrentPrice)
                    .orElse(null); // current price 정보를 찾을 수 없음

            ScenarioResponseDto responseDto = ScenarioResponseDto.builder()
                    .scenarioId(scenario.getId())
                    .scenarioName(scenario.getScenarioName())
                    .companyName(scenario.getCompany().getCompanyName())
                    .initialPrice(scenario.getInitialPrice())
                    .currentPrice(currentPrice)
                    .build();

            scenarioList.add(responseDto);
        }

        return scenarioList;
    }

}
