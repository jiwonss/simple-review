package com.example.simple.user;


import com.example.simple.user.dto.UserDto;
import com.example.simple.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private UserDto createUser(String email, String password) {
        UserDto userDto = new UserDto(email, password, "ROLE_USER");
        userService.save(userDto);
        return userDto;
    }

    @Test
    public void hello() {
        System.out.println("Hello");
    }

    @Test
    @Transactional
    @DisplayName("로그인 성공")
    public void loginSuccess() throws Exception {
        String email = "test2@a.com";
        String password = "1111";

        UserDto userDto = this.createUser(email, password);

        mockMvc.perform(
                SecurityMockMvcRequestBuilders.formLogin().user(userDto.getEmail()).password(password)
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
                SecurityMockMvcResultMatchers.authenticated()
        );
    }

    @Test
    @Transactional
    @DisplayName("로그인 실패")
    public void loginFail() throws Exception {
        String email = "test2@a.com";
        String password = "2222";

        UserDto userDto = this.createUser(email, password);

        mockMvc.perform(
                SecurityMockMvcRequestBuilders.formLogin().user(userDto.getEmail()).password("1111")
        ).andExpect(
                SecurityMockMvcResultMatchers.unauthenticated()
        ).andDo(
                MockMvcResultHandlers.print()
        );
    }
}
