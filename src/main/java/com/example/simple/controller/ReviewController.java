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

    @PostMapping("/add")
    public ReviewDto add(@AuthenticationPrincipal UserEntity userEntity,
                         @RequestBody @Valid ReviewDto reviewDto) {
        return reviewService.add(userEntity, reviewDto);
    }

    @GetMapping("/all")
    public List<ReviewDto> findAll() {
        return reviewService.findAll();
    }

    @GetMapping("/all/user")
    public List<ReviewDto> findAllByRestaurantId(@RequestParam Long id) {
        return reviewService.findAllByRestaurantId(id);
    }

    @PutMapping("/edit")
    public void edit(@RequestBody @Valid ReviewDto reviewDto) {
       reviewService.edit(reviewDto);
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        reviewService.delete(id);
    }

}
