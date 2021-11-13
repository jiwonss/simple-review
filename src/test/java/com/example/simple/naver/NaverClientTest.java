package com.example.simple.naver;

import com.example.simple.naver.dto.SearchImageReq;
import com.example.simple.naver.dto.SearchLocalReq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class NaverClientTest {

    @Autowired
    private NaverClient naverClient;

    @Test
    public void search(){
        var search = new SearchLocalReq();
        search.setQuery("목포 맛집");
        var result = naverClient.searchLocal(search);
        System.out.println(result);
    }

    @Test
    public void searchImage(){
        var search = new SearchImageReq();
        search.setQuery("카페");
        var result = naverClient.searchImage(search);
        System.out.println(result);
    }

}