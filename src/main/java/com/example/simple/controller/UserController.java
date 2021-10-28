package com.example.simple.controller;

import com.example.simple.user.dto.UserDto;
import com.example.simple.user.entity.UserEntity;
import com.example.simple.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/signup/new")
    public List<UserEntity> findAllUser() {
        return userRepository.findAll();
    }

    @PostMapping(value = "/signup/new")
    public UserEntity NewUser(UserDto userDto){
        final UserEntity user = UserEntity.builder()
                .name(userDto.getName())
                .id(userDto.getId())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .createdAt(LocalDateTime.now())
                .build();
        return userRepository.save(user);
    }
}
