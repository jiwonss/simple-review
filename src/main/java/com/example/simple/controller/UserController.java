package com.example.simple.controller;

import com.example.simple.user.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @PostMapping(value = "/signup/new")
    public void NewUser(UserDto userDto){
        System.out.println(userDto.toString());
    }
}
