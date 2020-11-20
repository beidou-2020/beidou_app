package com.bd.controller;

import com.bd.entitys.model.THistoricalReading;
import com.bd.entitys.model.TZxzStudy;
import com.bd.service.ReadService;
import com.bd.service.StudyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/")
@Slf4j
public class BeidouController {

    @Resource
    private ReadService readService;

    @Resource
    private StudyService studyService;

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
     * 加载个人控制台数据
     * @return
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index() {
        try {
            // 今年读完的阅读数据
            List<THistoricalReading> todayYearReadList = readService.todayYearByReadFlag();
            // 累计阅读量
            Integer readNum = readService.countReadNumber();
            // 本月结束的计划项(只限正在执行中)
            List<TZxzStudy> endStudyList = studyService.endStudyByCurrentMonth();
            // 累计计划项
            Integer studyNum = studyService.countStudyNumber();
            return new ModelAndView("index").
                    addObject("todayYearReadList", todayYearReadList).
                    addObject("readNum", readNum).
                    addObject("endStudyList", endStudyList).
                    addObject("studyNum", studyNum);
        } catch (Exception e) {
            log.error("个人控制台数据加载出现异常：{}", ExceptionUtils.getFullStackTrace(e));
        }

        return new ModelAndView("redirect:/bd/error");
    }
}
