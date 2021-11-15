package com.example.simple.controller;

import com.example.simple.resaurant.dto.RestaurantDto;
import com.example.simple.resaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiController {

    private final RestaurantService restaurantService;

    @GetMapping("/search")
    public RestaurantDto search(@RequestParam String query) {
        System.out.println(query);
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

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        restaurantService.delete(id);
    }

    @PostMapping("/{id}")
    public void addVisit(@PathVariable Long id){
        restaurantService.addVisit(id);
    }

}
