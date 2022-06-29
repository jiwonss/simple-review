package com.example.simple.restaurant.repository;

import com.example.simple.restaurant.dto.RestaurantDto;
import com.example.simple.restaurant.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    RestaurantEntity findByTitle(String title);
    boolean existsByTitle(String title);
    List<RestaurantEntity> findAllByUserId(Long id);
    List<RestaurantEntity> findAllByUserEmail(String email);
}
