package com.example.simple.review.service;

import com.example.simple.review.dto.ReviewDto;
import com.example.simple.review.entity.ReviewEntity;
import com.example.simple.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewEntity add(ReviewDto reviewDto) {
        var review = ReviewEntity.builder()
                .content(reviewDto.getContent())
                .build();
        return reviewRepository.save(review);
    }

}
