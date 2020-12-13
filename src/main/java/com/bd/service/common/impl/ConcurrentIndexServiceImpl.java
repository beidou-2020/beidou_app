package com.bd.service.common.impl;

import com.alibaba.fastjson.JSONObject;
import com.bd.entitys.model.THistoricalReading;
import com.bd.entitys.model.TZxzStudy;
import com.bd.service.ReadService;
import com.bd.service.StudyService;
import com.bd.service.common.ConcurrentIndexService;
import com.bd.simultaneously.IndexThreadPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

@Service
@Slf4j
public class ConcurrentIndexServiceImpl implements ConcurrentIndexService {

    @Resource
    private ReadService readService;

    @Resource
    private StudyService studyService;


    @Override
    public Map<String, Object> getIndexData() {
        Map<String, Object> map = new HashMap<>();              // 并发结果
        List<Callable<List>> taskList = new ArrayList<>();      // 任务池(携带返回结果)
        try{
            // 今年读完的阅读数据
            Callable<List> todayYearReadListTask = new Callable() {
                @Override
                public Object call() throws Exception {
                    List<THistoricalReading> tHistoricalReadings = readService.todayYearByReadFlag();
                    if (CollectionUtils.isEmpty(tHistoricalReadings)){
                        return new ArrayList<>();
                    }
                    map.put("todayYearReadList", tHistoricalReadings);
                    return tHistoricalReadings;
                }
            };
            taskList.add(todayYearReadListTask);

            // 累计阅读量
            Callable readNumTask = new Callable() {
                @Override
                public Object call() throws Exception {
                    Integer readNum = readService.countReadNumber();
                    if (Objects.nonNull(readNum)){
                        map.put("readNum", readNum);
                        return readNum;
                    }
                    return 0;
                }
            };
            taskList.add(readNumTask);


            // 本月结束的计划项(只限正在执行中)
            Callable<List> endStudyListTask = new Callable() {
                @Override
                public Object call() throws Exception {
                    List<TZxzStudy> tZxzStudies = studyService.endStudyByCurrentMonth();
                    if (CollectionUtils.isEmpty(tZxzStudies)){
                        return new ArrayList<>();
                    }
                    map.put("endStudyList", tZxzStudies);
                    return tZxzStudies;
                }
            };
            taskList.add(endStudyListTask);

            // 累计计划项
            Callable studyNumTask = new Callable() {
                @Override
                public Object call() throws Exception {
                    Integer studyNum = studyService.countStudyNumber();
                    if (Objects.nonNull(studyNum)){
                        map.put("studyNum", studyNum);
                        return studyNum;
                    }
                    return 0;
                }
            };
            taskList.add(studyNumTask);

            // 批量提交任务(携带返回值的)
            List<Future<List>> futureList = IndexThreadPool.indexPool.invokeAll(taskList);
            for (Future<List> itemFu : futureList){
                log.info("个人控制台并发执行的结果数据：{}", JSONObject.toJSONString(itemFu.get()));
            }
        }catch (Exception ex){
            log.error("并发获取个人控制台数据异常", ex);
        }
        return map;
    }
}
