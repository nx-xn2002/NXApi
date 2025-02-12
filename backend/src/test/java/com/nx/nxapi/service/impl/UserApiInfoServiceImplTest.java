package com.nx.nxapi.service.impl;

import com.nx.nxapi.service.UserApiInfoService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserApiInfoServiceImplTest {
    @Resource
    private UserApiInfoService userApiInfoService;

    @Test
    public void invokeCountTest() {
        System.out.println(userApiInfoService.invokeCount(1L, 1L));
    }
}