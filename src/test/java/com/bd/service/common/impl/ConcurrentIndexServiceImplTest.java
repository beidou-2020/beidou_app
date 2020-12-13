package com.bd.service.common.impl;

import com.alibaba.fastjson.JSONObject;
import com.bd.service.common.ConcurrentIndexService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;


@SpringBootTest
@Slf4j
class ConcurrentIndexServiceImplTest {

    @Autowired
    private ConcurrentIndexService concurrentIndexService;

    @Test
    public void test1(){
        Map<String, Object> indexData = concurrentIndexService.getIndexData();
        log.info("并发结果为：{}", JSONObject.toJSONString(indexData));
    }

}