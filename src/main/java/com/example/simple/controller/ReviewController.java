package com.example.simple.controller;

import com.example.simple.review.dto.ReviewDto;
import com.example.simple.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/add")
    public ReviewDto add(@RequestBody ReviewDto reviewDto) {
        return reviewService.add(reviewDto);
    }

    @GetMapping("/all")
    public List<ReviewDto> findAll() {
        return reviewService.findAll();
    }

    @GetMapping("/all/{id}")
    public List<ReviewDto> findAllByRestaurantId(@RequestParam Long id) {
        return reviewService.findAllByRestaurantId(id);
    }

    @PostMapping("/edit")
    public void edit(@RequestBody ReviewDto reviewDto) {
       reviewService.edit(reviewDto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        reviewService.delete(id);
    }

}
