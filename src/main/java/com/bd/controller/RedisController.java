package com.bd.controller;

import com.alibaba.fastjson.JSONObject;
import com.bd.controller.common.Result;
import com.bd.entitys.enumerate.ResultCode;
import com.bd.entitys.model.THistoricalReading;
import com.bd.service.ReadService;
import com.bd.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/redisPool")
@Slf4j
public class RedisController {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ReadService readService;

    /**
     * 阅读模块信息设置到redis中的key前缀
     */
    private static final String READ_KEY_PREFIX = "readInfo_prefix_";

    /**
     * 根据key获取缓存中的阅读信息
     * @return
     */
    @GetMapping("/getReadInfoByRedis/{id}")
    @ResponseBody
    public Result getValue(@PathVariable(name = "id") Long id){
        try{
            if (Objects.isNull(id)){
                log.info("getValue方法的参数为空：{}", JSONObject.toJSONString(id));
                return Result.fail(ResultCode.PARAM_ERROR.code(), ResultCode.PARAM_ERROR.msg());
            }

            // 开始命中缓存
            String keyName = READ_KEY_PREFIX + id;
            String valueObject = stringRedisTemplate.opsForValue().get(keyName);
            if (Objects.isNull(valueObject)){
                log.info("get key={} is null, Start resetting：", JSONObject.toJSONString(id));
                // 重新设置key的值
                THistoricalReading readInfo = readService.findById(id);
                if (Objects.isNull(readInfo)){
                    log.error("根据id={}无法获取到指定信息", JSONObject.toJSONString(id));
                }
                String valueString = JSONObject.toJSONString(readInfo);
                Boolean writeRedisResult = redisTemplate.opsForValue().setIfAbsent(keyName, valueString, 1, TimeUnit.MINUTES);
                if (writeRedisResult){
                    log.info("value reset successful. vlaue={}", valueString);
                }
                return Result.ok("value reset successful");
            }
            THistoricalReading value = JsonUtil.json2Object(valueObject, THistoricalReading.class);
            return Result.ok(value);
        }catch (Exception ex){
            log.error("get redis data error. key={}", JSONObject.toJSONString(id), ex);
        }
        return Result.ok("get redis data error");
    }


    /**
     * 设置阅读信息到缓存数据库中
     * @return
     */
    @PostMapping("/setReadInfo/{id}")
    @ResponseBody
    public Result setReadInfo(@PathVariable(name = "id") Long id){
        try{
            if (Objects.isNull(id)){
                log.info("setReadInfo方法的参数为空：{}", JSONObject.toJSONString(id));
                return Result.fail(ResultCode.PARAM_ERROR.code(), ResultCode.PARAM_ERROR.msg());
            }
            THistoricalReading readInfo = readService.findById(id);
            if (Objects.isNull(readInfo)){
                log.error("根据id={}无法获取到指定信息", JSONObject.toJSONString(id));
            }

            String keyName = READ_KEY_PREFIX + JSONObject.toJSONString(id);
            String vlaue = JSONObject.toJSONString(readInfo);
            Boolean setResult = redisTemplate.opsForValue().setIfAbsent(keyName, vlaue, 1, TimeUnit.MINUTES);
            if (!setResult){
                log.error("key：{}写入缓存发生冲突", keyName);
                return Result.fail(ResultCode.REDIS_WRITE_CONFILET.code(), ResultCode.REDIS_WRITE_CONFILET.msg());
            }
        }catch (Exception ex){
            log.error("set redis data error. param={}", JSONObject.toJSONString(id), ex);
        }

        return Result.ok("set success");
    }

}
