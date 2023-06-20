# **채팅 서버**

# 사용 기술 및 언어
- Java, SpringBoot, MongoDB, SSE + WebFlux
- Websocket (업로드 예정)
- Netty Server 사용 => 싱글 쓰레드, 비동기 서버 만들어줌 

# RDBMS VS MongoDB (NoSQL)
##  **[1] RDBMS**
### **[data 공유]** 
- 데이터를 한 곳에 정제해두고 연결해서 사용

### **장점**
(1) 데이터 중복 X

예: user-table, board-table 수정시 

user_name을 수정할 때 board-table 건드릴 필요가 없음

## **[2] MongoDB**
"user" 라는 컬렉션이 "json"으로 생김


**ex)**
```
user:[
    {id:1, username:ssar, phone:01012222},
    {id:2, username:cos, phone:0103333}
]
```

```
board: [
    {id:1, title:제목1, content:내용1,
        username: ssar, phone: 0102222},
        ....
]
````

### **특징**
- 외래키로 연결하지 않고 그대로 넣음
- 중복 허용
- RDBMS: join으로 data를 가져와야 하지만 mongodb는 **한 번에** select 가능 

### **장점**
데이터 찾을 때 성능 좋음 (insert할 땐 별로)

### **단점**
- 데이터 일관성 유지 힘듬 => 트리거 설정 잘 되어 있어야 함

# Etc
## **비동기 서버 <=> 비동기 DB (= 몽고DB)**

> **RDBMS** 보통 동기 방식

> **@Tailable**: 서버-클라이언트 간의 데이터 스트림을 구독하고 있는 클라이언트가 마지막으로 읽은 위치 이후의 새로운 데이터만을 수신하는 기능 

=> 주로 MongoDB와 같은 NoSQL 데이터베이스의 tailable cursor라는 기능과 관련 있음

> SSE가 되면 Flux로 계속 데이터를 흘려보낼 수 있음 & SSE는 resp 선만 유지되고 있음 