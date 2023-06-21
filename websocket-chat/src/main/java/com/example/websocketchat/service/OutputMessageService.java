package com.example.websocketchat.service;

import com.example.websocketchat.entity.OutputMessage;
import com.example.websocketchat.entity.User;
import com.example.websocketchat.model.Message;
import com.example.websocketchat.repository.OutputMessageRepository;
import com.example.websocketchat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.result.Output;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutputMessageService {

    private final UserRepository userRepository;
    private final OutputMessageRepository outputMessageRepository;

    public List<OutputMessage> findAll() {
        return outputMessageRepository.findAll();
    }

    public OutputMessage insert(Message message, String topic) {

        String name = message.getFrom();
//        Optional<User> user = userRepository.findByName(message.getFrom());
//        if(!user.isPresent())
//            return false;

        OutputMessage newMessage = new OutputMessage().builder()
                .from(name)
                .currentTime(new Date())
                .topic(topic)
                .message(message.getText())
                .build();

//        outputMessageRepository.save(newMessage);

        return outputMessageRepository.save(newMessage);
    }

    public OutputMessage findTopByOrderByCurrentTimeDesc() {
        return outputMessageRepository.findTopByOrderByCurrentTimeDesc();
    }

}
