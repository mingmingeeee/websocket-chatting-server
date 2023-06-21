package com.example.websocketchat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
public class User {

    @Id
    String id;
    String name;

}
