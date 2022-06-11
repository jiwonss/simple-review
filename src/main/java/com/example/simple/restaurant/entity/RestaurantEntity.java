package com.example.simple.restaurant.entity;

import com.example.simple.review.entity.ReviewEntity;
import com.example.simple.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant")
@Builder
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "roadaddress", nullable = false)
    private String roadAddress;

    @Column(name = "homepagelink", nullable = false)
    private String homePageLink;

    @Column(name = "imagelink", nullable = false)
    private String imageLink;

    @Column(name = "isvisit")
    private boolean isVisit;

    @Column(name = "visitcount")
    private int visitCount;

    @Column(name = "lastvisitdate")
    private LocalDateTime lastVisitDate;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Builder.Default
    @OneToMany(mappedBy = "restaurant")
    private List<ReviewEntity> reviews = new ArrayList<>();

    public RestaurantEntity(String title, String category, String address, String roadAddress, String homePageLink, String imageLink, boolean isVisit, int visitCount, LocalDateTime lastVisitDate) {
        this.title = title;
        this.category = category;
        this.address = address;
        this.roadAddress = roadAddress;
        this.homePageLink = homePageLink;
        this.imageLink = imageLink;
        this.isVisit = isVisit;
        this.visitCount = visitCount;
        this.lastVisitDate = lastVisitDate;
    }
}
