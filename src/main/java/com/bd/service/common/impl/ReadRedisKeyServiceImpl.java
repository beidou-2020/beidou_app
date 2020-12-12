package com.bd.service.common.impl;

import com.alibaba.fastjson.JSONObject;
import com.bd.constant.RedisKeyConstant;
import com.bd.service.common.ReadRedisKeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
@Slf4j
public class ReadRedisKeyServiceImpl implements ReadRedisKeyService {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public Boolean syncIndexViewByReadKey() {
        // 今年读完的阅读数据
        String indexTodayYearReadListKey = RedisKeyConstant.indexTodayYearReadList;

        // 累计阅读量
        String indexReadNumKey = RedisKeyConstant.indexReadNum;

        try{
            if (redisTemplate.hasKey(indexTodayYearReadListKey)){
                Boolean delete = redisTemplate.delete(indexTodayYearReadListKey);
                if (!delete){
                    log.error("无法删除key：{}", JSONObject.toJSONString(indexTodayYearReadListKey));
                    return false;
                }
            }
        }catch (Exception ex1){
            log.error("同步key：{}失败", JSONObject.toJSONString(indexTodayYearReadListKey), ex1);
            return false;
        }
        try{
            if (redisTemplate.hasKey(indexReadNumKey)){
                Boolean delete = redisTemplate.delete(indexReadNumKey);
                if (!delete){
                    log.error("无法删除key：{}", JSONObject.toJSONString(indexReadNumKey));
                    return false;
                }
            }
        }catch (Exception ex2){
            log.error("同步key：{}失败", JSONObject.toJSONString(indexReadNumKey), ex2);
            return false;
        }
        return true;
    }
}
