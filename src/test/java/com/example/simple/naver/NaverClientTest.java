package com.example.simple.naver;

import com.example.simple.naver.dto.SearchImageReq;
import com.example.simple.naver.dto.SearchImageRes;
import com.example.simple.naver.dto.SearchLocalReq;
import com.example.simple.naver.dto.SearchLocalRes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NaverClientTest {

    @Autowired
    private NaverClient naverClient;

    @Test
    public void hello() {
        System.out.println("hello");
    }

    @Test
    public void searchLocal() {
        String query = "서울 카페";

        SearchLocalReq searchLocalReq = new SearchLocalReq();
        searchLocalReq.setQuery(query);

        SearchLocalRes searchLocalRes = naverClient.searchLocal(searchLocalReq);
        System.out.println(searchLocalRes.getItems());
    }

    @Test
    public void searchImage() {
        String query = "서울 카페";

        SearchImageReq searchImageReq = new SearchImageReq();
        searchImageReq.setQuery(query);

        SearchImageRes searchImageRes = naverClient.searchImage(searchImageReq);
        System.out.println(searchImageRes.getItems());
    }

}
