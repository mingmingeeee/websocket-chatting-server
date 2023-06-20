package com.example.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class ChatController {

    private final ChatRepository chatRepository;

    // 귓속말할 때 사용하면 됨!!!
    // produces: 리턴 타입 정해주는 것
    // produces = MediaType.TEXT_EVENT_STREAM_VALUE: 데이터 생길 때마다 계속 보내줄 수 있음
    // => "Flux"
    @CrossOrigin
    @GetMapping(value = "/sender/{sender}/receiver/{receiver}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> getMsg(@PathVariable String sender, @PathVariable String receiver) {
        return chatRepository.mFindBySender(sender, receiver)
                .subscribeOn(Schedulers.boundedElastic());
    }

    // Mono: 데이터 한 번만 리턴
    // Flux: 데이터 계속 리턴
    @CrossOrigin
    @PostMapping("/chat")
    public Mono<Chat> setMsg(@RequestBody Chat chat) {
        chat.setCreatedAt(LocalDateTime.now());
        return chatRepository.save(chat); // save한 데이터 잘 들어갔는지 보기 위해 "Mono"
    }

    @CrossOrigin
    @GetMapping(value = "/chat/roomNum/{roomNum}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> findByRoomNum(@PathVariable Integer roomNum) {
        return chatRepository.mFindByRoomNum(roomNum)
                .subscribeOn(Schedulers.boundedElastic());
    }

}
