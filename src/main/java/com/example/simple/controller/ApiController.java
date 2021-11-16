package com.example.simple.controller;

import com.example.simple.restaurant.dto.RestaurantDto;
import com.example.simple.restaurant.entity.RestaurantEntity;
import com.example.simple.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final RestaurantService restaurantService;

    @GetMapping("/search")
    public RestaurantDto search(@RequestParam String query) {
        return restaurantService.search(query);
    }

    @PostMapping("")
    public RestaurantEntity add(@RequestBody RestaurantDto restaurantDto){
        return restaurantService.add(restaurantDto);
    }

    @GetMapping("/all")
    public List<RestaurantDto> findAll() {
        return restaurantService.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        restaurantService.delete(id);
    }

    @PostMapping("/{id}")
    public void addVisit(@PathVariable Long id){
        restaurantService.addVisit(id);
    }

}
