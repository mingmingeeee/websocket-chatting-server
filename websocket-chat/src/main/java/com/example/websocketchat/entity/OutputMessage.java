package com.example.websocketchat.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "chatservice")
public class OutputMessage {

    @Id
    String id;
    String from;
    String message;
    String topic;
    Date currentTime;

}
