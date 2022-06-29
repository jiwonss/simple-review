package com.example.simple.review.entity;

import com.example.simple.restaurant.entity.RestaurantEntity;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "review")
@ToString(exclude = "restaurant")
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;

    @Builder
    public ReviewEntity(String content) {
        this.content = content;
    }
}
