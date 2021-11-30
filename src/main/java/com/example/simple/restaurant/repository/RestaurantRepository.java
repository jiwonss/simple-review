package com.example.simple.restaurant.repository;

import com.example.simple.restaurant.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    public RestaurantEntity findByTitle(String title);
    public boolean existsByTitle(String title);
}
