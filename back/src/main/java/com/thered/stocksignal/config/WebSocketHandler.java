package com.thered.stocksignal.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thered.stocksignal.app.dto.StockDto;
import com.thered.stocksignal.app.dto.kis.KisSocketDto;
import com.thered.stocksignal.repository.CompanyRepository;
import com.thered.stocksignal.repository.ScenarioRepository;
import com.thered.stocksignal.repository.UserRepository;
import com.thered.stocksignal.service.company.CompanyService;
import com.thered.stocksignal.service.scenario.ScenarioService;
import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;
import okhttp3.Request;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

//웹소켓 서버를 구현
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {
//    private static Set<WebSocketSession> CLIENTS = Collections.synchronizedSet(new HashSet<>());
private final ConcurrentHashMap<Long, Session> userSessions = new ConcurrentHashMap<>();
    private final CompanyRepository companyRepository;
    private final ScenarioRepository scenarioRepository;
    private final ScenarioService scenarioService;

    /*//client 접속 시 호출
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long userId = extractUserIdFromSession(session); // 세션에서 userId를 추출하는 메서드 필요
        userSessions.remove(userId); // 세션 제거
        System.out.println("세션을 닫습니다. : " + session);
    }

    //client 접속 해제 시 호출
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long userId = extractUserIdFromSession(session); // 세션에서 userId를 추출하는 메서드 필요
        userSessions.remove(userId); // 세션 제거
        System.out.println("세션을 닫습니다. : " + session);
    }

    //클라이언트에서 websocket으로 들어오는 메세지를 처리함.
    @Override
    protected void handleTextMessage(WebSocketSession webSocketSession, TextMessage message) throws Exception {
        System.out.println("입력된 메세지입니다. > " + message);

        String payload = message.getPayload();

        try {
            // open websocket
            final WebSocketClientEndpoint clientEndPoint = new WebSocketClientEndpoint();

            Session session = clientEndPoint.connect(new URI("ws://ops.koreainvestment.com:31000"));
            clientEndPoint.sendMessage(payload);

            // add listener
            clientEndPoint.addMessageHandler(new WebSocketClientEndpoint.MessageHandler() {
                public void handleMessage(String message) throws IOException {

                    // Split the message using the '^' delimiter
                    String[] parts = message.split("\\^");
                    String[] codepart = parts[0].split("\\|");

                    // Check if the array has enough elements
                    if (parts.length >= 4) {
                        // Access individual parts based on their position
                        String companyCode = codepart[3];
                        String currentPrice = parts[2];
                        String companyName = companyRepository.findByCompanyCode(companyCode).get().getCompanyName();

                        String data = "test";
                        sendWebSocketMessage(data);

                    } else {
                        sendWebSocketMessage(message);
                    }
                }
            });

            Thread.sleep(5000);

        } catch (InterruptedException ex) {
            sendWebSocketMessage("Invalid message format: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            sendWebSocketMessage("URISyntaxException exception: " + ex.getMessage());
        }
    }*/

    public void subscribeStockInfo(Long userId, KisSocketDto.KisSocketRequestDto request) throws URISyntaxException, JsonProcessingException {
        if(request.getBody().getInput().getTr_id() == "1") {
            if(userSessions.containsKey(userId)){
                try {
                    // userId에 해당하는 세션 가져오기
                    Session existingSession = userSessions.get(userId);

                    ObjectMapper objectMapper = new ObjectMapper();
                    String message = objectMapper.writeValueAsString(request);

                    // 기존 세션으로 업데이트된 메시지 전송
                    existingSession.getBasicRemote().sendText(message);
                    System.out.println("변경된 메시지를 전송했습니다: " + message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    // 웹소켓 열기
                    final WebSocketClientEndpoint clientEndPoint = new WebSocketClientEndpoint();
                    Session session = clientEndPoint.connect(new URI("ws://ops.koreainvestment.com:31000"));

                    userSessions.put(userId, session);

                    ObjectMapper objectMapper = new ObjectMapper();
                    String message = objectMapper.writeValueAsString(request);

                    clientEndPoint.sendMessage(message);

                    // 메시지 리스너 추가
                    clientEndPoint.addMessageHandler(new WebSocketClientEndpoint.MessageHandler() {
                        public void handleMessage(String message) throws IOException {
                            String[] parts = message.split("\\|");
                            String[] data = parts[parts.length-1].split("\\^");

                            List<List<String>> stockInfos = new ArrayList<>();
                            List<StockDto.RealTimeStockInfoDto> stockInfoDtoList = new ArrayList<>();

                            // 58개씩 묶어서 리스트에 추가
                            for (int i = 0; i < data.length; i += 58) {
                                List<String> stockInfo = new ArrayList<>();
                                for (int j = i; j < i + 58 && j < data.length; j++) {
                                    stockInfo.add(data[j]);
                                }
                                stockInfos.add(stockInfo);
                            }

                            // 각 종목 정보 세트를 순회
                            for (List<String> stockInfo : stockInfos) {

                                if (!stockInfo.isEmpty()) {
                                    String companyCode = stockInfo.getFirst();
                                    String companyName = companyRepository.findByCompanyCode(companyCode).get().getCompanyName();

                                    StockDto.RealTimeStockInfoDto stockInfoDto = StockDto.RealTimeStockInfoDto.builder()
                                            .companyName(companyName)
                                            .time(stockInfo.get(1))

                                            .sellPrice1(Long.parseLong(stockInfo.get(3)))
                                            .sellPrice2(Long.parseLong(stockInfo.get(4)))
                                            .sellPrice3(Long.parseLong(stockInfo.get(5)))
                                            .sellPrice4(Long.parseLong(stockInfo.get(6)))
                                            .sellPrice5(Long.parseLong(stockInfo.get(7)))
                                            .sellPrice6(Long.parseLong(stockInfo.get(8)))

                                            .buyPrice1(Long.parseLong(stockInfo.get(13)))
                                            .buyPrice2(Long.parseLong(stockInfo.get(14)))
                                            .buyPrice3(Long.parseLong(stockInfo.get(15)))
                                            .buyPrice4(Long.parseLong(stockInfo.get(16)))
                                            .buyPrice5(Long.parseLong(stockInfo.get(17)))
                                            .buyPrice6(Long.parseLong(stockInfo.get(18)))

                                            .sellQuantity1(Long.parseLong(stockInfo.get(23)))
                                            .sellQuantity2(Long.parseLong(stockInfo.get(24)))
                                            .sellQuantity3(Long.parseLong(stockInfo.get(25)))
                                            .sellQuantity4(Long.parseLong(stockInfo.get(26)))
                                            .sellQuantity5(Long.parseLong(stockInfo.get(27)))
                                            .sellQuantity6(Long.parseLong(stockInfo.get(28)))

                                            .buyQuantity1(Long.parseLong(stockInfo.get(33)))
                                            .buyQuantity2(Long.parseLong(stockInfo.get(34)))
                                            .buyQuantity3(Long.parseLong(stockInfo.get(35)))
                                            .buyQuantity4(Long.parseLong(stockInfo.get(36)))
                                            .buyQuantity5(Long.parseLong(stockInfo.get(37)))
                                            .buyQuantity6(Long.parseLong(stockInfo.get(38)))
                                            .build();

                                    stockInfoDtoList.add(stockInfoDto);
                                } else {
                                    //실패메시지
                                }
                            }
                            scenarioService.checkAutoTrade(userId, stockInfoDtoList);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else {

            try {
                // userId에 해당하는 세션 가져오기
                Session existingSession = userSessions.get(userId);

                ObjectMapper objectMapper = new ObjectMapper();
                String message = objectMapper.writeValueAsString(request);

                // 기존 세션으로 업데이트된 메시지 전송
                existingSession.getBasicRemote().sendText(message);
                System.out.println("변경된 메시지를 전송했습니다: " + message);

                // 만약 scenario 테이블에 아무것도 없다면 userSessions에서 세션을 가져오고 종료
                if(scenarioRepository.findByUserId(userId).isEmpty()){
                    try {
                        existingSession.close(); // 세션 종료
                        userSessions.remove(userId); // userSessions에서 세션 제거
                        System.out.println("웹소켓 연결이 종료되었습니다.");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

/*    // WebSocket 클라이언트로 메시지 전송, 클라이언트가 이 메세지를 받아서 처리를 수행함.
    public void sendWebSocketMessage(String message) throws IOException {
        for (WebSocketSession client : CLIENTS) {
            TextMessage textMessage = new TextMessage(message);
            System.out.println("Sending message to WebSocket client: \n" + message);
            client.sendMessage(textMessage);
        }
    }*/
}