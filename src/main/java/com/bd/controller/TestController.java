package com.bd.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/app")
public class TestController {


    /**
     * 检测服务运行状态
     * @return
     */
    @GetMapping(value = "/health", produces = "application/json;charset=utf-8")
    public String health(){
        return "server success";
    }
}
