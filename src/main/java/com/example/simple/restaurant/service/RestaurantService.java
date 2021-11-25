package com.example.simple.restaurant.service;

import com.example.simple.naver.NaverClient;
import com.example.simple.naver.dto.SearchImageReq;
import com.example.simple.naver.dto.SearchLocalReq;
import com.example.simple.restaurant.dto.RestaurantDto;
import com.example.simple.restaurant.entity.RestaurantEntity;
import com.example.simple.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final NaverClient naverClient;
    private final RestaurantRepository restaurantRepository;

    public RestaurantDto search(String query) {
        // 지역 검색
        var searchLocalReq = new SearchLocalReq();
        searchLocalReq.setQuery(query);

        var searchLocalRes = naverClient.searchLocal(searchLocalReq);

        if(searchLocalRes.getTotal() > 0) {
            var localItem = searchLocalRes.getItems().stream().findFirst().get();

            var imageQuery = localItem.getTitle().replaceAll("<[^>]*>", "");
            var searchImageReq = new SearchImageReq();
            searchImageReq.setQuery(imageQuery);

            // 이미지 검색
            var searchImageRes = naverClient.searchImage(searchImageReq);

            if(searchImageRes.getTotal() > 0){
                var imageItem = searchImageRes.getItems().stream().findFirst().get();
                // 결과 리턴
                var result = new RestaurantDto();
                result.setTitle(localItem.getTitle());
                result.setCategory(localItem.getCategory());
                result.setAddress(localItem.getAddress());
                result.setRoadAddress(localItem.getRoadAddress());
                result.setHomePageLink(localItem.getLink());
                result.setImageLink(imageItem.getLink());

                return result;
            }
        }

        return new RestaurantDto();
    }

    public RestaurantEntity add(RestaurantDto restaurantDto) {
        var restaurant = RestaurantEntity.builder()
                .title(restaurantDto.getTitle())
                .category(restaurantDto.getCategory())
                .address(restaurantDto.getAddress())
                .roadAddress(restaurantDto.getRoadAddress())
                .homePageLink(restaurantDto.getHomePageLink())
                .imageLink(restaurantDto.getImageLink())
                .isVisit(restaurantDto.isVisit())
                .visitCount(restaurantDto.getVisitCount())
                .lastVisitDate(restaurantDto.getLastVisitDate())
                .build();
        return restaurantRepository.save(restaurant);
    }

    private RestaurantEntity dtoToEntity(RestaurantDto restaurantDto) {
        var entity = new RestaurantEntity();
        entity.setTitle(restaurantDto.getTitle());
        entity.setCategory(restaurantDto.getCategory());
        entity.setAddress(restaurantDto.getAddress());
        entity.setRoadAddress(restaurantDto.getRoadAddress());
        entity.setHomePageLink(restaurantDto.getHomePageLink());
        entity.setImageLink(restaurantDto.getImageLink());
        entity.setVisit(restaurantDto.isVisit());
        entity.setVisitCount(restaurantDto.getVisitCount());
        entity.setLastVisitDate(restaurantDto.getLastVisitDate());
        return entity;
    }

    private RestaurantDto entityToDto(RestaurantEntity restaurantEntity) {
        var dto = new RestaurantDto();
        dto.setTitle(restaurantEntity.getTitle());
        dto.setCategory(restaurantEntity.getCategory());
        dto.setAddress(restaurantEntity.getAddress());
        dto.setRoadAddress(restaurantEntity.getRoadAddress());
        dto.setHomePageLink(restaurantEntity.getHomePageLink());
        dto.setImageLink(restaurantEntity.getImageLink());
        dto.setVisit(restaurantEntity.isVisit());
        dto.setVisitCount(restaurantEntity.getVisitCount());
        dto.setLastVisitDate(restaurantEntity.getLastVisitDate());
        return dto;
    }

    public List<RestaurantDto> findAll(){
        return restaurantRepository.findAll().stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public void delete(Long id){
        restaurantRepository.deleteById(id);
    }

    public void addVisit(Long id){
        var restaurantItem = restaurantRepository.findById(id);
        if(restaurantItem.isPresent()){
            var item = restaurantItem.get();
            item.setVisit(true);
            item.setVisitCount(item.getVisitCount() + 1);
            restaurantRepository.save(item);
            System.out.println(item);
        }
    }
}
