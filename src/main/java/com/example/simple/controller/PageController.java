package com.example.simple.controller;

import com.example.simple.restaurant.dto.RestaurantDto;
import com.example.simple.restaurant.service.RestaurantService;
import com.example.simple.review.dto.ReviewDto;
import com.example.simple.review.service.ReviewService;
import com.example.simple.user.dto.PasswordDto;
import com.example.simple.user.dto.UserDto;
import com.example.simple.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
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
    public ModelAndView join(Model model) {
        model.addAttribute(new UserDto());
        return new ModelAndView("signup");
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "AccessDenied";
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
            restaurants = restaurantService.findAllByUserId(userEntity.getId());
            model.addAttribute("restaurants", restaurants);
        }
        return new ModelAndView("restaurant-list");
    }

    @PostMapping("/restaurant/list")
    public String restaurantAdd(Model model, RestaurantDto restaurantDto) {
        var id = restaurantService.getUser().getId();
        restaurantService.add(restaurantDto);
        List<RestaurantDto> restaurants = new ArrayList<>();
        restaurants = restaurantService.findAllByUserId(id);
        model.addAttribute("restaurants", restaurants);
        return "restaurant-list";
    }

    @GetMapping("/review")
    public ModelAndView review(@RequestParam String title, Model model) {
        model.addAttribute(new ReviewDto());
        model.addAttribute("title", title);
        return new ModelAndView("review-write");
    }

    @PostMapping("/review")
    public String reviewCheck(@AuthenticationPrincipal UserEntity userEntity,
                             @Valid ReviewDto reviewDto,
                             Errors errors,
                             Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", reviewDto.getTitle());
            return "review-write";
        }

        reviewService.add(userEntity, reviewDto);
        return "redirect:/review/list";
    }

    @GetMapping("/review/list")
    public ModelAndView reviewList(@AuthenticationPrincipal UserEntity userEntity, Model model) {
        List<ReviewDto> reviews = new ArrayList<>();
        if (userEntity != null) {
            reviews = reviewService.findAllByUser(userEntity.getId());
            model.addAttribute("reviews", reviews);
        }
        return new ModelAndView("review-list");
    }

    @GetMapping("/userpage")
    public ModelAndView userpage() {
        return new ModelAndView("userpage");
    }

    @GetMapping("/userinfo")
    public ModelAndView userinfo(@AuthenticationPrincipal UserEntity userEntity, Model model) {
        model.addAttribute("email", userEntity.getEmail());
        return new ModelAndView("userinfo");
    }

    @GetMapping("/userinfo/password")
    public ModelAndView changePassword(@AuthenticationPrincipal UserEntity userEntity, Model model) {
        model.addAttribute(new PasswordDto());
        model.addAttribute("email", userEntity.getEmail());
        return new ModelAndView("password-change");
    }

    @GetMapping("/userinfo/account")
    public String deleteAccount(Model model) {
        model.addAttribute("deleteCheck", true);
        return "userpage";
    }

    @GetMapping("/user/restaurant/list")
    public ModelAndView userRestaurantList(@AuthenticationPrincipal UserEntity userEntity, Model model) {
        List<RestaurantDto> restaurants = new ArrayList<>();
        if (userEntity != null){
            restaurants = restaurantService.findAllByUserId(userEntity.getId());
            model.addAttribute("restaurants", restaurants);
        }
        return new ModelAndView("user-restaurant-list");
    }

    @GetMapping("/user/review/list")
    public ModelAndView userReviewList(@AuthenticationPrincipal UserEntity userEntity, Model model) {
        List<ReviewDto> reviews = new ArrayList<>();
        if (userEntity != null) {
            reviews = reviewService.findAllByUser(userEntity.getId());
            model.addAttribute("reviews", reviews);
        }
        return new ModelAndView("user-review-list");
    }


}
