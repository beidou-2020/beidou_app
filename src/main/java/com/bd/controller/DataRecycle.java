package com.bd.controller;
/**
 * 数据回收站
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/recycle")
@Slf4j
public class DataRecycle {

    @GetMapping("/dataRecycle")
    public ModelAndView dataRecycle(){
        return new ModelAndView("system/dataRecycle");
    }


}
