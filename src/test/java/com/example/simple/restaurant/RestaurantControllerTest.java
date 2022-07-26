package com.example.simple.restaurant;

import com.example.simple.restaurant.dto.RestaurantDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
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

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // MockHttpServletResponse Body 한글 깨짐 문제 해결
                .apply(springSecurity())
                .build();
    }

    @Test
    @Transactional
    @WithUserDetails(value = "test@a.com")
    @DisplayName("[Restaurant] Search")
    public void searchTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/restaurant/search")
                        .queryParam("query", "목포")
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
    public void addTest() throws Exception {
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setTitle("카페");
        restaurantDto.setCategory("음식점>카페");
        restaurantDto.setAddress("");
        restaurantDto.setRoadAddress("");
        restaurantDto.setImageLink("");
        restaurantDto.setHomePageLink("");

        String json = new ObjectMapper().writeValueAsString(restaurantDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/restaurant")
                        .contentType(APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andDo(
                MockMvcResultHandlers.print()
        );
    }

    @Test
    @Transactional
    @WithMockUser
    @DisplayName("[Restaurant] Find All")
    public void findAllTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/restaurant")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andDo(
                MockMvcResultHandlers.print()
        );
    }

    @Test
    @Transactional
    @WithUserDetails("test@a.com")
    @DisplayName("[Restaurant] Find All By User Email")
    public void findAllByUserEmailTest() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/restaurant/test@a.com")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andDo(
                MockMvcResultHandlers.print()
        );
    }

    @Test
    @Transactional
    @WithUserDetails("test@a.com")
    @DisplayName("[Restaurant] Delete")
    public void deleteTest() throws Exception {
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setTitle("카페");
        restaurantDto.setCategory("음식점>카페");
        restaurantDto.setAddress("");
        restaurantDto.setRoadAddress("");
        restaurantDto.setImageLink("");
        restaurantDto.setHomePageLink("");

        String json = new ObjectMapper().writeValueAsString(restaurantDto);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/restaurant")
                        .contentType(APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andDo(
                MockMvcResultHandlers.print()
        ).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        mockMvc.perform(
                MockMvcRequestBuilders.delete(String.format("/api/restaurant/%s", String.valueOf(id)))
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andDo(
                MockMvcResultHandlers.print()
        );
    }

    @Test
    @Transactional
    @WithUserDetails("test@a.com")
    @DisplayName("[Restaurant] Add Visit Count")
    public void addVisitCountTest() throws Exception {
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setTitle("카페");
        restaurantDto.setCategory("음식점>카페");
        restaurantDto.setAddress("");
        restaurantDto.setRoadAddress("");
        restaurantDto.setImageLink("");
        restaurantDto.setHomePageLink("");

        String json = new ObjectMapper().writeValueAsString(restaurantDto);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/restaurant")
                        .contentType(APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andDo(
                MockMvcResultHandlers.print()
        ).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        mockMvc.perform(
                MockMvcRequestBuilders.put(String.format("/api/restaurant/%s", String.valueOf(id)))
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andDo(
                MockMvcResultHandlers.print()
        );
    }

}
