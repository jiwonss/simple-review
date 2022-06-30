package com.example.simple.review.service;

import com.example.simple.restaurant.entity.RestaurantEntity;
import com.example.simple.restaurant.repository.RestaurantRepository;
import com.example.simple.review.dto.ReviewDto;
import com.example.simple.review.entity.ReviewEntity;
import com.example.simple.review.repository.ReviewRepository;
import com.example.simple.user.entity.UserEntity;
import com.example.simple.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public ReviewDto add(UserEntity userEntity, ReviewDto reviewDto) {
        String title = reviewDto.getTitle();
        var restaurants = userEntity.getRestaurants();
        var saveEntity = new ReviewEntity();

        for (RestaurantEntity entity : restaurants) {
            if (entity.getTitle().equals(title)) {
                var review = ReviewEntity.builder()
                        .content(reviewDto.getContent())
                        .build();

                review.setRestaurant(entity);
                entity.getReviews().add(review);
                saveEntity = reviewRepository.save(review);
            }
        }

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
