package com.example.simple.naver;

import com.example.simple.naver.dto.SearchLocalReq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NaverClientTest {

    @Autowired
    private NaverClient naverClient;

    @Test
    public void searchTest() {
        var search = new SearchLocalReq();
        search.setQuery("목포 맛집");

        var result = naverClient.searchLocal(search);
        System.out.println(result);
    }
}