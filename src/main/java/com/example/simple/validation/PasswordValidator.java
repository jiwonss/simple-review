package com.example.simple.validation;

import com.example.simple.user.dto.PasswordDto;
import com.example.simple.user.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PasswordValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PasswordDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PasswordDto passwordDto = (PasswordDto) target;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (encoder.matches(passwordDto.getNewPassword(), userEntity.getPassword())) {
            errors.rejectValue("newPassword", "same value", "현재 패스워드와 일치합니다.");
        }

        if (!passwordDto.getNewPassword().equals(passwordDto.getNewPasswordConfirm())) {
            errors.rejectValue("newPassword", "wrong value", "입력한 새 패스워드가 일치하지 않습니다.");
        }
    }
}
