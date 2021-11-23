package com.example.simple.controller;

import com.example.simple.restaurant.dto.RestaurantDto;
import com.example.simple.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class PageController {

    private final RestaurantService restaurantService;

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
    public ModelAndView restaurant(Model model) {
        List<RestaurantDto> restaurants = new ArrayList<>();
        restaurants = restaurantService.findAll();
        model.addAttribute("restaurants", restaurants);
        return new ModelAndView("restaurant");
    }

    @GetMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("index");
    }
}
