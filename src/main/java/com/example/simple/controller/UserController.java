package com.example.simple.controller;

import com.example.simple.user.dto.UserDto;
import com.example.simple.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping(value = "/new")
    public String signup(@Valid UserDto userDto, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            List<String> result = new ArrayList<>();
            bindingResult.getAllErrors().forEach(objectError -> {
                FieldError field = (FieldError) objectError;
                String message = objectError.getDefaultMessage();
                result.add("[" + field.getField() + "] " + message);
                log.info("field : " + field.getField());
                log.info("message : " + message);
            });
            redirectAttributes.addFlashAttribute("result", result);
            return "redirect:/signup-error";
        }

        userService.save(userDto);
        log.info("{} 회원가입 성공", userDto.getEmail());
        return "redirect:/login";
    }

    // 로그아웃
    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/";
    }
}
