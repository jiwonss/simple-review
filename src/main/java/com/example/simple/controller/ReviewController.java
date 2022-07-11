package com.example.simple.controller;

import com.example.simple.review.dto.ReviewDto;
import com.example.simple.review.service.ReviewService;
import com.example.simple.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class ReviewController {

    private final ReviewService reviewService;

    // POST /api/review
    @PostMapping
    public ReviewDto add(@AuthenticationPrincipal UserEntity userEntity,
                         @RequestBody @Valid ReviewDto reviewDto) {
        return reviewService.add(userEntity, reviewDto);
    }

    // GET /api/review
    @GetMapping
    public List<ReviewDto> findAll() {
        return reviewService.findAll();
    }

    // GET /api/review/{id}
    @GetMapping("/{id}")
    public List<ReviewDto> findAllByRestaurantId(@RequestParam Long id) {
        return reviewService.findAllByRestaurantId(id);
    }

    // PUT /api/review
    @PutMapping
    public void edit(@RequestBody @Valid ReviewDto reviewDto) {
       reviewService.edit(reviewDto);
    }

    // DELETE /api/review/{id}
    @Transactional
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        reviewService.delete(id);
    }
}
