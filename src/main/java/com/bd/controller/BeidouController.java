package com.bd.controller;

import com.bd.entitys.model.THistoricalReading;
import com.bd.entitys.model.TZxzStudy;
import com.bd.service.ReadService;
import com.bd.service.StudyService;
import com.bd.service.common.ConcurrentIndexService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
@Slf4j
public class BeidouController {

    @Resource
    private ReadService readService;

    @Resource
    private StudyService studyService;

    @Resource
    private ConcurrentIndexService concurrentIndexService;

    /**
     * 系统登录页
     * @return
     */
    @RequestMapping(value = "beidou", method = RequestMethod.GET)
    public ModelAndView beidou() {
        return new ModelAndView("login");
    }

    /**
     * 系统公共错误页面
     * @return
     */
    @RequestMapping(value = "error", method = RequestMethod.GET)
    public ModelAndView error() {
        return new ModelAndView("error");
    }

    /**
     * 加载个人控制台数据(串行获取)
     * @return
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index() {
        List<THistoricalReading> todayYearReadList = new ArrayList<>();
        List<TZxzStudy> endStudyList = new ArrayList<>();
        Integer readNum = 0;
        Integer studyNum = 0;
        try {
            // 今年读完的阅读数据
            todayYearReadList = readService.todayYearByReadFlag();
            // 累计阅读量
            readNum = readService.countReadNumber();
            // 本月结束的计划项(只限正在执行中)
            endStudyList = studyService.endStudyByCurrentMonth();
            // 累计计划项
            studyNum = studyService.countStudyNumber();

        } catch (Exception e) {
            log.error("个人控制台数据加载出现异常：{}", ExceptionUtils.getFullStackTrace(e));
            //return new ModelAndView("redirect:/bd/error");
        }

        return new ModelAndView("index").
                addObject("todayYearReadList", todayYearReadList).
                addObject("readNum", readNum).
                addObject("endStudyList", endStudyList).
                addObject("studyNum", studyNum);
    }

    /**
     * 加载个人控制台数据(并发获取)
     * @return
     */
    @RequestMapping(value = "indexByConcurrent", method = RequestMethod.GET)
    public ModelAndView indexByConcurrent() {
        List<THistoricalReading> todayYearReadList = new ArrayList<>();
        List<TZxzStudy> endStudyList = new ArrayList<>();
        Integer readNum = 0;
        Integer studyNum = 0;
        try {
            // 并发获取首页个人指标项数据
            Map<String, Object> indexData = concurrentIndexService.getIndexData();

            // 解析数据
            todayYearReadList = (List<THistoricalReading>) indexData.get("todayYearReadList");
            readNum = (Integer) indexData.get("readNum");
            endStudyList = (List<TZxzStudy>) indexData.get("endStudyList");
            studyNum = (Integer) indexData.get("studyNum");


        } catch (Exception e) {
            log.error("个人控制台数据加载出现异常：{}", ExceptionUtils.getFullStackTrace(e));
            //return new ModelAndView("redirect:/bd/error");
        }

        return new ModelAndView("index").
                addObject("todayYearReadList", todayYearReadList).
                addObject("readNum", readNum).
                addObject("endStudyList", endStudyList).
                addObject("studyNum", studyNum);
    }


}
