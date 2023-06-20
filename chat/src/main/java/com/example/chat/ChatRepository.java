package com.example.chat;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface ChatRepository extends ReactiveMongoRepository<Chat, String> {

    // Http는 한 번에 처리하기 때문에 지속적인 처리 X => SSE 프로토콜 쓰기
    @Tailable // 커서를 안 닫고 계속 유지한다. (열어두었기 때문에 Flux로 응답 => 계속 흘러들어감)
    @Query("{sender:?0,receiver:?1}") // mongodb select절
    Flux<Chat> mFindBySender(String sender, String receiver); // Flux(흐름) => 데이터를 계속 흐르며 받겠다는 뜻

    @Tailable
    @Query("{roomNum:?0}")
    Flux<Chat> mFindByRoomNum(Integer roomNum);

}
