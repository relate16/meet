package com.example.meet;

import com.example.meet.controller.MultiChatController;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
// build.gradle 에 webSocket 을 implement 한 후 @EnableWebSocket 어노테이션으로 WebSocket 을 활성화 할 수 있다.
public class WebSocketConfig implements WebSocketConfigurer {
    // 관련 소스 링크 : https://dev-gorany.tistory.com/212

    private final MultiChatController multiChatController;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // paths: "ws/~" - 웹소켓은 클라이언트가 접속 요청을 하고 웹 서버가 응답한 후 연결을 끊는 것이 아닌
        //      Connection을 그대로 유지하고 클라이언트의 요청 없이도 데이터를 전송할 수 있는 프로토콜이다.
        //      프로토콜의 요청은 [ws://~]로 시작한다.
        // .setAllowedorigins("*") : 도메인이 접속 허용할 서버를 추가해준다.
        //      도메인이 다른 서버에서도 접속 가능하도록 모든 경로 "*"를 파라미터에 넣어준다.
        registry.addHandler(multiChatController, "ws/multiChat").setAllowedOrigins("*");
    }
}
