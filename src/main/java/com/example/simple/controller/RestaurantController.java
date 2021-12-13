package com.example.simple.controller;

import com.example.simple.restaurant.dto.RestaurantDto;
import com.example.simple.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

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

    @GetMapping("/all/{id}")
    public List<RestaurantDto> findAllByUserId(@RequestParam Long id) {
        return restaurantService.findAllByUser(id);
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
