package com.bd.controller;
/**
 * 数据回收站
 */

import com.bd.entitys.model.THistoricalReading;
import com.bd.entitys.model.TZxzStudy;
import com.bd.entitys.parame.PageParam;
import com.bd.entitys.query.ReadQuery;
import com.bd.entitys.query.StudyQuery;
import com.bd.service.ReadService;
import com.bd.service.StudyService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;

@RestController
@RequestMapping("/recycle")
@Slf4j
public class DataRecycleController {

    @Resource
    private StudyService studyService;

    @Resource
    private ReadService readService;

    /**
     * 到数据回收管理页面
     * @return
     */
    @GetMapping("/dataRecycle")
    public ModelAndView dataRecycle(PageParam pageQuery){
        // 获取删除的学习计划列表
        PageInfo<TZxzStudy> studyPageInfo = studyService.pageFindByRemove(new StudyQuery(), pageQuery);

        // 获取删除的阅读计划列表
        PageInfo<THistoricalReading> readPageInfo = readService.pageByRemove(pageQuery, new ReadQuery());

        return new ModelAndView("system/dataRecycle").
                addObject("studyTotal", studyPageInfo.getTotal()).
                addObject("studyList", studyPageInfo.getList()).
                addObject("readTotal", readPageInfo.getTotal()).
                addObject("readList", readPageInfo.getList());
    }


}
