package com.example.websocketchat.service;

import com.example.websocketchat.entity.User;
import com.example.websocketchat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByUser(String name) {
        User user = userRepository.findByName(name).orElse(null);

        return user;
    }

}
