package com.example.simple.validation;

import com.example.simple.user.dto.PasswordDto;
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

        if (!passwordDto.getNewPassword().equals(passwordDto.getNewPasswordConfirm())) {
            errors.rejectValue("newPassword", "wrong value", "입력한 새 패스워드가 일치하지 않습니다.");
        }
    }
}
