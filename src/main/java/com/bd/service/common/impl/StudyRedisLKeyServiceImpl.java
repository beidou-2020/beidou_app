package com.bd.service.common.impl;

import com.alibaba.fastjson.JSONObject;
import com.bd.constant.RedisKeyConstant;
import com.bd.service.common.StudyRedisKeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
@Slf4j
public class StudyRedisLKeyServiceImpl implements StudyRedisKeyService {


    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public Boolean syncIndexViewByStudyKey() {
        // 本月结束的计划项
        String indexEndStudyListKey = RedisKeyConstant.indexEndStudyList;

        // 累计计划项
        String indexStudyNumKey = RedisKeyConstant.indexStudyNum;

        try{
            if (redisTemplate.hasKey(indexEndStudyListKey)){
                Boolean delete = redisTemplate.delete(indexEndStudyListKey);
                if (!delete){
                    log.error("无法删除key：{}", JSONObject.toJSONString(indexEndStudyListKey));
                    return false;
                }
            }
        }catch (Exception ex1){
            log.error("同步key：{}失败", JSONObject.toJSONString(indexEndStudyListKey), ex1);
            return false;
        }
        try{
            if (redisTemplate.hasKey(indexStudyNumKey)){
                Boolean delete = redisTemplate.delete(indexStudyNumKey);
                if (!delete){
                    log.error("无法删除key：{}", JSONObject.toJSONString(indexStudyNumKey));
                    return false;
                }
            }
        }catch (Exception ex2){
            log.error("同步key：{}失败", JSONObject.toJSONString(indexStudyNumKey), ex2);
            return false;
        }
        return true;
    }
}
