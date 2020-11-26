package com.bd.controller;
/**
 * 数据回收站
 */

import com.bd.entitys.model.TZxzStudy;
import com.bd.entitys.parame.PageParam;
import com.bd.entitys.query.StudyQuery;
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

    /**
     * 到数据回收管理页面
     * @return
     */
    @GetMapping("/dataRecycle")
    public ModelAndView dataRecycle(PageParam pageQuery){
        // 获取删除的学习计划列表
        PageInfo<TZxzStudy> studyPageInfo = studyService.pageFindByRemove(new StudyQuery(), pageQuery);
        return new ModelAndView("system/dataRecycle").
                addObject("studyTotal", studyPageInfo.getTotal()).
                addObject("studyList", studyPageInfo.getList()).
                addObject("studyTotalPage", studyPageInfo.getPages()).
                addObject("studyCurrPage", pageQuery.getCurrentPageNumber());
    }


}
