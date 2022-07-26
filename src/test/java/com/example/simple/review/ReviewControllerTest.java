package com.example.simple.review;

import com.example.simple.review.dto.ReviewDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@AutoConfigureWebMvc
@AutoConfigureMockMvc
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .apply(springSecurity())
                .build();
    }

    @Test
    @Transactional
    @WithUserDetails(value = "test@a.com")
    @DisplayName("[Review] Add")
    public void addTest() throws Exception {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setTitle("코롬방제과점");
        reviewDto.setContent("test 입니다.");

        String json = new ObjectMapper().writeValueAsString(reviewDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andDo(
                MockMvcResultHandlers.print()
        );
    }

    @Test
    @Transactional
    @WithUserDetails("test@a.com")
    @DisplayName("[Review] Find All")
    public void findAllTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/review")
        ).andDo(
                MockMvcResultHandlers.print()
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andDo(
                MockMvcResultHandlers.print()
        );
    }

    @Test
    @Transactional
    @WithUserDetails("test@a.com")
    @DisplayName("[Review] Edit")
    public void editTest() throws Exception {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setTitle("코롬방제과점");
        reviewDto.setContent("test 입니다.");

        String json = new ObjectMapper().writeValueAsString(reviewDto);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andDo(
                MockMvcResultHandlers.print()
        ).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        reviewDto.setId(Long.valueOf(id));
        reviewDto.setContent("test2 입니다.");

        String editJson = new ObjectMapper().writeValueAsString(reviewDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(editJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andDo(
                MockMvcResultHandlers.print()
        );
    }

    @Test
    @Transactional
    @WithUserDetails("test@a.com")
    @DisplayName("[Review] Delete")
    public void deleteTest() throws Exception {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setTitle("코롬방제과점");
        reviewDto.setContent("test 입니다.");

        String json = new ObjectMapper().writeValueAsString(reviewDto);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andDo(
                MockMvcResultHandlers.print()
        ).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        reviewDto.setId(Long.valueOf(id));

        mockMvc.perform(
                MockMvcRequestBuilders.delete(String.format("/api/review/%s", String.valueOf(id)))
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andDo(
                MockMvcResultHandlers.print()
        );
    }

}
