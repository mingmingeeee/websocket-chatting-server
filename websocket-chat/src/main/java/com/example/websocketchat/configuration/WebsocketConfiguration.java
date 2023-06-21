package com.example.websocketchat.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // WebSocket 메시지 브로커 활성화
public class WebsocketConfiguration implements WebSocketMessageBrokerConfigurer {

    // Client에서 websocket연결할 때 사용할 API 경로를 설정해주는 메서드
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat").setAllowedOriginPatterns("*");
        // STOMP 엔드 포인트 등록
        // "/chat" 경로에 엔드포인트 등록 == chat 경로를 통해 서버, websocket 연결
    }

    // enableSimpleBroker
    // 메시지 받을 때 관련 경로 설정
    //" /queue", "/topic" 이 두 경로가 prefix(api 경로 맨 앞)에 붙은 경우, messageBroker가 잡아서 해당 채팅방을 구독하고 있는 클라이언트에게 메시지를 전달해줌
    // 주로 "/queue"는 1대1 메시징, "/topic"은 1대다 메시징일 때 주로 사용함.
    // setApplicationDestinationPrefixes
    // 메시지 보낼 때 관련 경로
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app").enableSimpleBroker("/topic");
        // 메시지 브로커 구성
        // "/app": 클라이언트가 메시지 보낼 때 "/pub"으로 시작하는 대상 지정
        // => "/app" 접두사로 하는 메시지는 컨트롤러 클래스에서 처리됨
        // enableSimpleBroker("/topic") -> 메시지 브로커가 "/topic"으로 시작하는 대상에게 메시지 전송할 수 있도록 활성화
        // 클라이언트가 topic으로 시작하는 대상에 구독하면 해당 topic에 대한 메시지 수신 가능

        // 해당 접두사 붙은 구독자에 메시지 보냄
        // @MessageMapping 통해 Message 보낼 url에 대한 설정
    }

}
