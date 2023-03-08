package com.example.meet.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.List;
import java.util.ArrayList;

@Slf4j
public class MultiChatHandler extends TextWebSocketHandler {
    //관련 소스 링크 : https://dev-gorany.tistory.com/212
    // TextWebSocketHandler 설명 링크 : https://supawer0728.github.io/2018/03/30/spring-websocket/

    private static List<WebSocketSession> list = new ArrayList<>(); // 채팅에 접속한 세션들 저장소인듯

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //afterConnectionEstablished(WebSocketSession) : connection이 맺어진 후 실행된다.
        // 즉 Client가 접속시 실행되는 메서드이다.

        list.add(session);
        log.info("클라이언트 접속 {}", session);

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // handleMessage(WebSocketSession, WebSocketMessage<?>) : session에서 메시지를 수신했을 때 실행된다.
        // message 타입에 따라 handleTextMessage(), handleBinaryMessage()를 실행한다.
        String payload = message.getPayload();
        // payload는 전송되는 데이터를 의미한다. 즉 Client에서 보낸 메세지(data)이다.
        // message 객체 = {payload: "hello"} 와 같은 것 같다(추측)



    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // afterConnectionClosed(WebSocketSession, CloseStatus) : connectino이 종료된 후 실행된다.
        // 즉, Client가 접속해제시 실행되는 메서드이다.

        list.remove(session);
        log.info("클라이언트 접속 해제 {}", session);
    }
}
