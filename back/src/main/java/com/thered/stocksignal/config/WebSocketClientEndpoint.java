package com.thered.stocksignal.config;

import jakarta.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.text.ParseException;

@ClientEndpoint
public class WebSocketClientEndpoint {

    Session userSession = null;
    private MessageHandler messageHandler;

    // 서버와 세션 연결
    public Session connect(URI uri){
        try {
            WebSocketContainer container = ContainerProvider
                    .getWebSocketContainer();
            container.connectToServer(this, uri);

            return userSession;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 서버로 메시지 전송
    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }

    @OnOpen
    public void onOpen(Session userSession) {
        this.userSession = userSession;
        System.out.println("웹소켓 연결이 시작되었습니다.");
    }

    @OnClose
    public void onClose() {
        System.out.println("웹소켓 연결이 종료되었습니다.");
    }


    // 메시지 핸들러를 통한 String 메시지 처리
    @OnMessage
    public void onMessage(String message) throws ParseException, IOException {
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
    }

    /* 메시지 핸들러를 통한 암호화(바이너리) 메시지 처리
    @OnMessage
    public void onMessage(ByteBuffer bytes) {
    }*/

    // 메시지 핸들러 추가
    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }

    // 메시지 핸들러
    public static interface MessageHandler {
        public void handleMessage(String message) throws ParseException, IOException;
    }

}