# WebSocket
## **1. HTTP vs WebSocket**
- HTTP (Stateless):
> 서버 응답 후 연결이 끝남 (== Statelss)
- WebSocket (Stateful): 
> HTTP와 달리 Client - Server간 연결 유지, **양방향** 통신 가능 

### 정리
- 자주 데이터를 주고 받는 환경이 아닌 경우: HTTP 프로토콜 통신이 유리
- 자주 데이터를 주고 받아야 하는 환경인 경우: 직접적인 소켓 통신이 유리
- HTTP 통신은 사용자가 서버에 요청을 보내는 단방향 통신, 소켓 통신은 양방향 통신

## **2. WebSocket 동작 흐름**
(1) Client -> (HTTP 요청, 핸드쉐이크 요청) -> Server

(2) Server: 해당 요청에 101코드 응답, 프로토콜을 HTTP -> WebSocket으로 변경

(3) Connection 완료, 양방향 통신 가능

(4) Client, Server 양쪽에서 Connection 종료 가능

## **3-1. 장점**
(1) 양방향성

Client, Server 양방향 통신 가능하므로 **실시간성** 서비스에 적합 (채팅, 실시간 차트 등)

(2) Header

요청, 응답 헤더에 많은 정보를 담고 있는 HTTP에 반해 헤더의 크기가 작기 때문에 처리시간이 빠름

## **3-2. 단점**
(1) HTTP와 달리 커넥션을 연결한 상태로 유지하기 때문에 커넥션이 많아지면 서버 부하 발생

(2) WebSocket 지원하지 않는 브라우저 존재 

### SockJS
> 웹소켓이 모든 브라우저에 지원되지 않는 점을 해결하기 위해 고안된 JS 라이브러리
- 웹소켓을 지원하지 않는 브라우저일 경우 Long-Polling과 같은 HTTP 기반의 다른 기술로 전환하여 커넥션 제공

# Stomp
> Simple/Stream Text Oriented Messaging Protocol

Client-Server가 주고 받는 데이터(메시지)들을 간단한 텍스트로 지향하는 프로토콜

(sub/pub 방식)

# 구현 유의 사항
(1) 클라이언트는 처음 연결할 때 WebSocket Config에 등록된 엔드포인트를 통해 서버에 연결함


    registry.addEndpoint("~~")로 설정된 부분


(2) 연결된 이후 메시지를 전송할 때는 `setApplicationDestinationPrefixes`에 등록한 prefix로 전송
> 예: setApplicationDestinationPrefixes("/app") 라면, "/app/{url}" 형식으로 전송


(3) 메소드가 반환하는 메시지를 토픽으로 전송하도록 지정함

    @SendTo("topic/{topic}") OR template.convertAndSend("/topic/" + topic, message);

enableSimpleBroker("/topic");과 같이 등록한 구독 대상을 구독하도록 설정

- enableSimpleBroker 메서드에 등록한 주제(topic)만이 메시지 브로커에 의해 관리되며, 해당 주제에 대한 구독자들에게 메시지가 전달됨
- @SendTo 애노테이션을 사용하여 특정 토픽에 메시지를 전송하더라도 메시지 브로커는 enableSimpleBroker에 등록된 주제만을 관리하고 전달함.
- 따라서 enableSimpleBroker에 등록한 주제와 @SendTo에서 지정한 토픽은 일치해야만 해당 토픽에 대한 구독자들에게 메시지가 전달되고 다른 토픽은 메시지 브로커에 의해 처리되지 않고 무시됨


---
test: apic (stomp 때문에)
