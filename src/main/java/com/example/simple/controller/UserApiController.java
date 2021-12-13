package com.example.simple.controller;

import com.example.simple.user.entity.UserEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserApiController {

    @GetMapping("/email")
    public String currentUserEmail(@AuthenticationPrincipal UserEntity userEntity) {
        return userEntity.getEmail();
    }

}
