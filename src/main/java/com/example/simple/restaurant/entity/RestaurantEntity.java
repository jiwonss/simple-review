package com.example.simple.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "title", nullable = false)
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
