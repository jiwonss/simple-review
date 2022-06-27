package com.example.simple.restaurant;

import com.example.simple.restaurant.dto.RestaurantDto;
import com.example.simple.restaurant.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@AutoConfigureWebMvc
@AutoConfigureMockMvc
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private RestaurantService restaurantService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // MockHttpServletResponse Body 한글 깨짐 문제 해결
                .apply(springSecurity())
                .build();
    }

    @Test
    public void hello() {
        System.out.println("hello");
    }

    @Test
    @WithMockUser
    public void helloController() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/restaurant/hello")
        ).andDo(
                MockMvcResultHandlers.print()
        );
    }

    @Test
    @Transactional
    @WithUserDetails(value = "test@a.com")
    @DisplayName("[Restaurant] Search")
    public void search() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/restaurant/search")
                        .queryParam("query", "목포")
        ).andExpect(
                SecurityMockMvcResultMatchers.authenticated()
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andDo(
                MockMvcResultHandlers.print()
        );
    }

    @Test
    @Transactional
    @WithUserDetails(value = "test@a.com")
    @DisplayName("[Restaurant] Add")
    public void add() throws Exception {
        RestaurantDto dto = new RestaurantDto();
        dto.setTitle("카페");
        dto.setCategory("음식점>카페");
        dto.setAddress("");
        dto.setRoadAddress("");
        dto.setImageLink("");
        dto.setHomePageLink("");

        String json = new ObjectMapper().writeValueAsString(dto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/restaurant")
                        .contentType(APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                SecurityMockMvcResultMatchers.authenticated()
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andDo(
                MockMvcResultHandlers.print()
        );
    }

    @Test
    @Transactional
    @WithUserDetails(value = "test@a.com")
    @DisplayName("[Restaurant] Find All")
    public void findAll() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/restaurant/all")
        ).andExpect(
                SecurityMockMvcResultMatchers.authenticated()
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andDo(
                MockMvcResultHandlers.print()
        );
    }
    
}
