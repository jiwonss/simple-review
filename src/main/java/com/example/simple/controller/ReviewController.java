package com.example.simple.controller;

import com.example.simple.review.dto.ReviewDto;
import com.example.simple.review.entity.ReviewEntity;
import com.example.simple.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/add")
    public ReviewEntity add(@RequestBody ReviewDto reviewDto) {
        return reviewService.add(reviewDto);
    }
}
