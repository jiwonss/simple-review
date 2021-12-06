package com.example.simple.review.service;

import com.example.simple.restaurant.entity.RestaurantEntity;
import com.example.simple.restaurant.repository.RestaurantRepository;
import com.example.simple.review.dto.ReviewDto;
import com.example.simple.review.entity.ReviewEntity;
import com.example.simple.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;


    public ReviewDto add(ReviewDto reviewDto) {
        var restaurant = restaurantRepository.findByTitle(reviewDto.getTitle());
        var review = ReviewEntity.builder()
                .content(reviewDto.getContent())
                .build();
        review.setRestaurant(restaurant);
        restaurant.getReviews().add(review);
        var saveEntity = reviewRepository.save(review);
        return entityToDto(saveEntity);
    }

    public ReviewDto entityToDto(ReviewEntity reviewEntity) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setContent(reviewEntity.getContent());
        return reviewDto;
    }
}
