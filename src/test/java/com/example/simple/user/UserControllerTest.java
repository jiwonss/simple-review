package com.example.simple.user;

import com.example.simple.user.dto.UserDto;
import com.example.simple.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(springSecurity())
                .build();
    }

    private UserDto createUser(String email, String password) {
        UserDto userDto = new UserDto(email, password, "ROLE_USER");
        userService.save(userDto);
        return userDto;
    }

    // 이메일 중복 확인
    @Test
    @Transactional
    @DisplayName("[이메일 중복 확인] 중복 ⭕")
    public void emailCheckSuccess() throws Exception {
        String email = "test@a.com";

        mockMvc.perform(
                MockMvcRequestBuilders.get("/check")
                        .queryParam("email", email)
        ).andExpect(
                MockMvcResultMatchers.flash().attribute("message", "❌")
        ).andDo(
                MockMvcResultHandlers.print()
        );
    }

    @Test
    @Transactional
    @DisplayName("[이메일 중복 확인] 중복 ❌")
    public void emailCheckFail() throws Exception {
        String email = "test1@a.com";

        mockMvc.perform(
                MockMvcRequestBuilders.get("/check")
                        .queryParam("email", email)
        ).andExpect(
                MockMvcResultMatchers.flash().attribute("message", "✔")
        ).andDo(
                MockMvcResultHandlers.print()
        );
    }

    // 회원가입
    @Test
    @Transactional
    @DisplayName("[회원가입] 성공")
    public void signupSuccess() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("email", "test1@a.com")
                        .param("password", "qwer!1234")
                        .param("auth", "ROLE_USER")
        ).andExpect(
                MockMvcResultMatchers.redirectedUrl("/login")
        ).andDo(
                MockMvcResultHandlers.log()
        );
    }

    @Test
    @Transactional
    @DisplayName("[회원가입] 실패")
    public void signupFail() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("email", "test1@a.com")
                        .param("password", "1234")
                        .param("auth", "ROLE_USER")
        ).andExpect(
                MockMvcResultMatchers.view().name("signup")
        ).andDo(
                MockMvcResultHandlers.log()
        );
    }
    
    // 로그인 성공
    @Test
    @Transactional
    @DisplayName("[로그인] 성공")
    public void loginSuccess() throws Exception {
        String email = "test1@a.com";
        String password = "test1!1234";

        UserDto userDto = this.createUser(email, password);

        mockMvc.perform(
                SecurityMockMvcRequestBuilders.formLogin().user(email).password(password)
        ).andExpect(
                MockMvcResultMatchers.redirectedUrl("/")
        ).andDo(
                MockMvcResultHandlers.print()
        );
    }

    // 로그인 실패
    @Test
    @Transactional
    @DisplayName("[로그인] 실패")
    public void loginFail() throws Exception {
        String email = "test1@a.com";
        String password = "test1!1234";

        UserDto userDto = this.createUser(email, password);

        mockMvc.perform(
                SecurityMockMvcRequestBuilders.formLogin().user(email).password("1111")
        ).andExpect(
                MockMvcResultMatchers.redirectedUrl("/login?error")
        ).andDo(
                MockMvcResultHandlers.print()
        );
    }

    // 비밀번호 변경
    @Test
    @Transactional
    @WithUserDetails("test@a.com")
    @DisplayName("[비밀번호 변경] 성공")
    public void changePasswordSuccess() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/change")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("newPassword", "qwer!1234")
                        .param("newPasswordConfirm", "qwer!1234")
        ).andExpect(
                MockMvcResultMatchers.redirectedUrl("/change/password")
        ).andDo(
                MockMvcResultHandlers.log()
        );
    }

    @Test
    @Transactional
    @WithUserDetails("test@a.com")
    @DisplayName("[비밀번호 변경] 실패")
    public void changePasswordFail() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/change")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("newPassword", "test!1234")
                        .param("newPasswordConfirm", "")
        ).andExpect(
                MockMvcResultMatchers.view().name("password-change")
        ).andDo(
                MockMvcResultHandlers.log()
        );
    }

    // 로그아웃
    @Test
    @Transactional
    @WithUserDetails("test@a.com")
    @DisplayName("[로그아웃] 성공")
    public void logoutSuccess() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/logout")
        ).andExpect(
                MockMvcResultMatchers.redirectedUrl("/")
        ).andDo(
                MockMvcResultHandlers.print()
        );
    }

    // 계정 삭제
    @Test
    @Transactional
    @WithUserDetails("test@a.com")
    @DisplayName("[계정 삭제] 성공")
    public void deleteAccountSuccess() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/delete")
        ).andExpect(
                MockMvcResultMatchers.flash().attribute("url", "/")
        ).andDo(
                MockMvcResultHandlers.print()
        );
    }

}
