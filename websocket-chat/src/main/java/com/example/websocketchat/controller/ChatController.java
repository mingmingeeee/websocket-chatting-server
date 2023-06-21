package com.example.websocketchat.controller;

import com.example.websocketchat.entity.OutputMessage;
import com.example.websocketchat.model.Message;
import com.example.websocketchat.service.OutputMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.result.Output;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import java.util.List;

// STOMP 통신에 대해 핸들링 할 수 있는 Handler 클래스와 같은 맥락
@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final OutputMessageService outputMessageService;
    private final SimpMessageSendingOperations messagingTemplate;

    // send함수를 통해 "/chat/{topic}의 url을 요청하게 된다면 이에 맞춰 채팅 내용 추가 후
    // 올린 채팅 내용 가져오는 함수
    // @SendTo: 구독에 대해 읽어볼 수 있도록 설정하는 어노테이션
    @MessageMapping("/chat/{topic}")
//    @SendTo("/topic/{topic}")
    public OutputMessage send(@DestinationVariable("topic") String topic, Message message) throws Exception {
        OutputMessage outputMessage = outputMessageService.insert(message, topic);
        log.info(message.toString() + " " + topic);
        messagingTemplate.convertAndSend("/topic/" + topic, outputMessage);
        return outputMessageService.findTopByOrderByCurrentTimeDesc();
    }

    @MessageMapping("/chat/list")
//    @SendTo("/topic/{topic}/refresh")
    public List<OutputMessage> findAll() {
        return outputMessageService.findAll();
    }

}
