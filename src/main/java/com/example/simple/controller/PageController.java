package com.example.simple.controller;

import com.example.simple.restaurant.dto.RestaurantDto;
import com.example.simple.restaurant.entity.RestaurantEntity;
import com.example.simple.restaurant.service.RestaurantService;
import com.example.simple.review.dto.ReviewDto;
import com.example.simple.review.service.ReviewService;
import com.example.simple.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class PageController {

    private final RestaurantService restaurantService;
    private final ReviewService reviewService;

    @GetMapping("/")
    public ModelAndView main() {
        return new ModelAndView("main");
    }

    @GetMapping("/signup")
    public ModelAndView join() {
        return new ModelAndView("signup");
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "AccessDenied";
    }

    @ResponseBody
    @GetMapping("/auth")
    public Authentication auth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/restaurant")
    public ModelAndView restaurant() {
        return new ModelAndView("restaurant");
    }

    @GetMapping("/restaurant/search")
    public String restaurantSearch(@RequestParam String query, Model model) {
        RestaurantDto result = restaurantService.search(query);
        model.addAttribute("result", result);
        model.addAttribute("resultTable", true);
        return "restaurant";
    }

    @GetMapping("/restaurant/list")
    public ModelAndView restaurantList(@AuthenticationPrincipal UserEntity userEntity, Model model) {
        List<RestaurantDto> restaurants = new ArrayList<>();
        if (userEntity != null){
            restaurants = restaurantService.findAllByUser(userEntity.getId());
            model.addAttribute("restaurants", restaurants);
        }
        return new ModelAndView("restaurant-list");
    }

    @PostMapping("/restaurant/list")
    public String restaurantAdd(Model model, RestaurantDto restaurantDto) {
        var id = restaurantService.getUser().getId();
        restaurantService.add(restaurantDto);
        List<RestaurantDto> restaurants = new ArrayList<>();
        restaurants = restaurantService.findAllByUser(id);
        model.addAttribute("restaurants", restaurants);
        return "restaurant-list";
    }

    @GetMapping("/write")
    public ModelAndView write(@RequestParam String title, Model model) {
        model.addAttribute("title", title);
        return new ModelAndView("write");
    }

    @GetMapping("/review")
    public ModelAndView reviewList(@AuthenticationPrincipal UserEntity userEntity, Model model) {
        List<ReviewDto> reviews = new ArrayList<>();
        if (userEntity != null) {
            reviews = reviewService.findAllByUser(userEntity.getId());
            model.addAttribute("reviews", reviews);
        }
        return new ModelAndView("review-list");
    }

    @GetMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @GetMapping("/userpage")
    public ModelAndView userpage() {
        return new ModelAndView("userpage");
    }
}
