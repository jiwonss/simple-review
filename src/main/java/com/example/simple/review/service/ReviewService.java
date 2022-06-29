package com.example.simple.review.service;

import com.example.simple.restaurant.entity.RestaurantEntity;
import com.example.simple.restaurant.repository.RestaurantRepository;
import com.example.simple.review.dto.ReviewDto;
import com.example.simple.review.entity.ReviewEntity;
import com.example.simple.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        reviewDto.setId(reviewEntity.getId());
        reviewDto.setContent(reviewEntity.getContent());
        reviewDto.setTitle(reviewEntity.getRestaurant().getTitle());
        return reviewDto;
    }

    public List<ReviewDto> findAll(){
        return reviewRepository.findAll().stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public List<ReviewDto> findAllByRestaurantId(Long id) {
        return reviewRepository.findAllByRestaurantId(id).stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public List<ReviewDto> findAllByUser(Long id) {
        List<RestaurantEntity> restaurants = restaurantRepository.findAllByUserId(id);
        List<ReviewDto> reviews = new ArrayList<>();
        for (RestaurantEntity restaurant : restaurants) {
            reviews.addAll(restaurant.getReviews().stream().map(this::entityToDto).collect(Collectors.toList()));
        }
        return reviews;
    }

    @Transactional
    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

    public void edit(ReviewDto reviewDto) {
        var review = reviewRepository.findById(reviewDto.getId()).get();
        review.setContent(reviewDto.getContent());
        reviewRepository.save(review);
    }
}
