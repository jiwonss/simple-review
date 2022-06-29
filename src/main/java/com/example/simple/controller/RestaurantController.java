package com.example.simple.controller;

import com.example.simple.restaurant.dto.RestaurantDto;
import com.example.simple.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/search")
    public RestaurantDto search(@RequestParam String query) {
        return restaurantService.search(query);
    }

    @PostMapping("")
    public RestaurantDto add(@RequestBody RestaurantDto restaurantDto){
        return restaurantService.add(restaurantDto);
    }

    @GetMapping("/all")
    public List<RestaurantDto> findAll() {
        return restaurantService.findAll();
    }

    @GetMapping("/all/user")
    public List<RestaurantDto> findAllByUserId(@RequestParam Long id) {
        return restaurantService.findAllByUserId(id);
    }

    @GetMapping("/all/user/{email}")
    public List<RestaurantDto> findAllByUserEmail(@PathVariable String email) {
        return restaurantService.findAllByUserEmail(email);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        restaurantService.delete(id);
    }

    @PutMapping("/{id}")
    public void addVisit(@PathVariable Long id){
        restaurantService.addVisit(id);
    }

}
