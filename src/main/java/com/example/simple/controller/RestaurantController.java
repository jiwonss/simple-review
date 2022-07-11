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

    // GET /api/restaurant/search?query={query}
    @GetMapping("/search")
    public RestaurantDto search(@RequestParam String query) {
        return restaurantService.search(query);
    }

    // POST /api/restaurant
    @PostMapping
    public RestaurantDto add(@RequestBody RestaurantDto restaurantDto){
        return restaurantService.add(restaurantDto);
    }

    // GET /api/restaurant
    @GetMapping
    public List<RestaurantDto> findAll() {
        return restaurantService.findAll();
    }

    // GET /api/restaurant/{email}
    @GetMapping("/{email}")
    public List<RestaurantDto> findAllByUserEmail(@PathVariable String email) {
        return restaurantService.findAllByUserEmail(email);
    }

    // DELETE /api/restaurant/{id}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        restaurantService.delete(id);
    }

    // PUT /api/restaurant/{id}
    @PutMapping("/{id}")
    public void addVisit(@PathVariable Long id){
        restaurantService.addVisit(id);
    }

}
