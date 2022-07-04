package com.example.simple.controller;

import com.example.simple.user.dto.UserDto;
import com.example.simple.user.entity.UserEntity;
import com.example.simple.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    // email 중복 확인
    @GetMapping(value = "/check")
    public String emailDuplicateCheck(@RequestParam String email, RedirectAttributes redirectAttributes) {
        if (userService.existsByEmail(email)) {
            redirectAttributes.addFlashAttribute("msg", "이메일이 이미 존재합니다.");
            return "redirect:/email-check";
        }
        redirectAttributes.addFlashAttribute("email", email);
        redirectAttributes.addFlashAttribute("msg", "✔");
        return "redirect:/email-check";
    }

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

    // 회원탈퇴
    @GetMapping(value = "/delete")
    public String deleteUser(@AuthenticationPrincipal UserEntity userEntity, RedirectAttributes redirectAttributes) {
        if (userService.deleteUser(userEntity.getEmail()) > 0) {
            redirectAttributes.addFlashAttribute("msg", "성공적으로 회원 정보를 삭제하였습니다.");
            redirectAttributes.addFlashAttribute("url", "/");
            SecurityContextHolder.clearContext();
        } else {
            redirectAttributes.addFlashAttribute("msg", "회원 정보 삭제를 실패하였습니다.");
            redirectAttributes.addFlashAttribute("url", "/userpage");
        }

        return "redirect:/delete/account";
    }
}
