package com.ecommerce.app.service;

import org.springframework.stereotype.Service;

import com.ecommerce.app.entity.User;
import com.ecommerce.app.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public User createUser(User user) {
        
        User savedUser = userRepository.save(user);

        return savedUser;
    }

    public User getUserById(Long userId) {
        
       User savedUser =  userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User is not found with id : " + userId));

        return savedUser;
    }
}
