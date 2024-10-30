package com.thered.stocksignal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketHandler webSocketHandler;

    public WebSocketConfig(WebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //엔드포인트 정의, url에 웹소켓 핸들러 매핑     //websocket연결 시작할 수 있도록 서버에서 핸들러 등록
        registry.addHandler(webSocketHandler, "/board")
                //클라이언트에서 웹소켓 서버에 요청시 http://localhost:8080"에서 들어오는 WebSocket 연결만을 허용
                .setAllowedOrigins("http://localhost:8080");

        //엔드포인트 정의, url에 웹소켓 핸들러 매핑
        registry.addHandler(webSocketHandler, "/tryitout/H0STCNT0")
                //클라이언트에서 웹소켓 서버에 요청시 모든 요청을 수용
                .setAllowedOrigins("http://ops.koreainvestment.com:31000");
    }
}