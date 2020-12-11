package com.bd.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bd.constant.RedisConstant;
import com.bd.controller.common.Result;
import com.bd.entitys.dto.AddReadDTO;
import com.bd.entitys.dto.UpdateReadDTO;
import com.bd.entitys.model.THistoricalReading;
import com.bd.entitys.parame.AddReadParam;
import com.bd.entitys.parame.PageParam;
import com.bd.entitys.parame.UpdateReadParam;
import com.bd.entitys.query.ReadQuery;
import com.bd.repository.FileClient;
import com.bd.repository.ReadFeignClient;
import com.bd.service.ReadService;
import com.bd.utils.BeanUtil;
import com.bd.utils.JsonUtils;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ReadServiceImpl implements ReadService {

    @Resource
    private ReadFeignClient readFeignClient;

    @Resource
    private FileClient fileClient;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 将整个方法的返回结果进行缓存处理
     * @param pageParam
     * @param query
     * @return
     */
    @Override
    @Cacheable(value = "read_list_retData", key = "{#query, #pageParam}")
    public PageInfo<THistoricalReading> pageByQuery(PageParam pageParam, ReadQuery query) {
        PageInfo<THistoricalReading> list = readFeignClient.list(pageParam.getCurrentPageNumber(),
                pageParam.getPageSize(),
                query.getBookName(),
                query.getAuthor(),
                query.getReadFlag(),
                query.getBegintime(),
                query.getEndtime(),
                query.getValidMark());
        return list;
    }

    @Override
    public PageInfo<THistoricalReading> pageByRemove(PageParam pageParam, ReadQuery query) {
        // valid_mark：0删除
        query.setValidMark(0);
        PageInfo<THistoricalReading> list = readFeignClient.list(pageParam.getCurrentPageNumber(),
                pageParam.getPageSize(),
                query.getBookName(),
                query.getAuthor(),
                query.getReadFlag(),
                query.getBegintime(),
                query.getEndtime(),
                query.getValidMark());
        return list;
    }

    @Override
    public THistoricalReading addReadInfo(AddReadParam param) {
        AddReadDTO addReadDTO = new AddReadDTO();
        BeanUtil.copyProperties(param, addReadDTO);

        //1、调用file-server服务上传截图
        MultipartFile[] readPicFiles = param.getReadPicFiles();
        if (Objects.nonNull(readPicFiles)){
            StringBuffer names = new StringBuffer();
            int length = readPicFiles.length;
            for (int i=0; i<length; i++){
                log.info("新增阅读信息开始上传截图");
                MultipartFile file = readPicFiles[i];
                if (!file.isEmpty()){
                    String uploadFileName = fileClient.uploadFile(file);
                    names.append(uploadFileName).append(",");
                }
            }

            String screenshotNames = names.toString();
            if (StringUtils.isNotEmpty(screenshotNames)){
                // 截掉最后一个逗号
                String substring = screenshotNames.substring(0, screenshotNames.length() - 1);
                log.info("新增后的阅读截图名：{}", substring);
                addReadDTO.setScreenshotName(substring);
            }
        }

        //2、调用read-server服务保存阅读信息
        THistoricalReading insertRes = readFeignClient.add(addReadDTO);
        log.info("阅读计划保存成功：{}", JSONObject.toJSONString(insertRes));
        return insertRes;
    }

    @Override
    @Cacheable(value = "read_info_retData", key = "{#id}")
    public THistoricalReading findById(Long id) {
        THistoricalReading reading = readFeignClient.readDetails(id);
        return reading;
    }

    @Override
    public THistoricalReading updateReadInfo(UpdateReadParam param) {
        UpdateReadDTO updateReadDTO = new UpdateReadDTO();
        BeanUtil.copyProperties(param, updateReadDTO);

        //1、调用file-server服务上传截图
        MultipartFile[] readPicFiles = param.getReadPicFiles();
        if (Objects.nonNull(readPicFiles)){
            StringBuffer names = new StringBuffer();
            int length = readPicFiles.length;
            for (int i=0; i<length; i++){
                MultipartFile file = readPicFiles[i];
                if (!file.isEmpty()){
                    String uploadFileName = fileClient.uploadFile(file);
                    names.append(uploadFileName).append(",");
                }
            }
            String screenshotNames = names.toString();
            if (StringUtils.isNotEmpty(screenshotNames)){
                // 截掉最后一个逗号
                String substring = screenshotNames.substring(0, screenshotNames.length() - 1);
                log.info("更新后的阅读截图名：{}", substring);
                updateReadDTO.setScreenshotName(substring);
            }
        }

        //2、调用read-server服务修改阅读信息
        THistoricalReading update = readFeignClient.update(updateReadDTO);
        return update;
    }

    @Override
    public THistoricalReading deleteById(Long id) {
        return readFeignClient.deleteById(id);
    }

    @Override
    public List<THistoricalReading> todayYearByReadFlag() {
        String keyName = RedisConstant.indexTodayYearReadList;
        try{
            // 命中缓存操作
            String todayYearReadListByRedisString = stringRedisTemplate.opsForValue().get(keyName);
            if (StringUtils.isNotEmpty(todayYearReadListByRedisString)){
                List<THistoricalReading> todayYearReadList = JsonUtils.
                        toBeanList(todayYearReadListByRedisString, THistoricalReading.class);
                log.info("首页信息——阅读简报数据命中缓存：{}", JSONObject.toJSONString(todayYearReadList));
                return todayYearReadList;
            }
        }catch (Exception ex){
            log.error("首页信息——阅读简报数据命中缓存异常", ex);
        }

        Result result = readFeignClient.todayYearByReadFlag();
        if (Objects.isNull(result)){
            return null;
        }

        int code = result.getCode();
        if (Objects.isNull(code) || code != 0){
            return null;
        }
        List data = (List<THistoricalReading>)result.getData();

        try{
            // 设置缓存(查询DB后), 过期时间为1小时
            // setIfAbsent如果key已经存在则不执行set
            String value = JsonUtils.toJSONString(data);
            Boolean writeRedisResult = redisTemplate.opsForValue().setIfAbsent(keyName, value, 60, TimeUnit.MINUTES);
            if (writeRedisResult){
                log.info("key：{}===value：{}写入缓存成功", keyName, value);
            }
        }catch (Exception ex){
            log.error("设置缓存失败——首页信息(阅读简报数据: {})", JSONObject.toJSONString(data), ex);
        }
        return data;
    }

    @Override
    public Integer countReadNumber() {
        String keyName = RedisConstant.indexReadNum;
        try{
            // 命中缓存操作
            String readNumByRedisString = stringRedisTemplate.opsForValue().get(keyName);
            if (StringUtils.isNotEmpty(readNumByRedisString)){
                int readNum = Integer.parseInt(readNumByRedisString);
                log.info("首页信息——累计阅读总数命中缓存：{}", JSONObject.toJSONString(readNum));
                return readNum;
            }
        }catch (Exception ex){
            log.error("首页信息——累计阅读总数命中缓存异常", ex);
        }

        Result result = readFeignClient.countReadNumber();
        if (Objects.isNull(result)){
            return null;
        }

        int code = result.getCode();
        if (Objects.isNull(code) || code != 0){
            return null;
        }
        Integer data = (Integer)result.getData();

        try{
            // 设置缓存(查询DB后), 过期时间为1小时
            String value = JsonUtils.toJSONString(data);
            Boolean writeRedisResult = redisTemplate.opsForValue().setIfAbsent(keyName, value, 60, TimeUnit.MINUTES);
            if (writeRedisResult){
                log.info("key：{}===value：{}写入缓存成功", keyName, value);
            }
        }catch (Exception ex){
            log.error("设置缓存失败——首页信息(累计阅读总数: {})", JSONObject.toJSONString(data), ex);
        }
        return data;
    }

    @Override
    public Integer batchDelete(String idListStr) {
        Result result = readFeignClient.batchDelete(idListStr);
        if (Objects.isNull(result)){
            return null;
        }

        int code = result.getCode();
        if (Objects.isNull(code) || code != 0){
            return null;
        }
        Integer data = (Integer)result.getData();
        return data;
    }

    @Override
    public Integer timeOutReadInfo(Long id) {
        Result result = readFeignClient.timeOutReadInfo(id);
        if (Objects.isNull(result)){
            log.error("调用timeOut方法返回的响应体为空, response: {}", JSONObject.toJSONString(result));
            return null;
        }

        int code = result.getCode();
        if (Objects.isNull(code) || code != 0){
            log.error("调用timeOut方法出现异常, code: {}", JSONObject.toJSONString(code));
            return null;
        }
        Integer data = (Integer)result.getData();
        return data;
    }

    @Override
    public Integer restartReadInfo(Long id) {
        Result result = readFeignClient.restartReadInfo(id);
        if (Objects.isNull(result)){
            log.error("调用restart方法返回的响应体为空, response: {}", JSONObject.toJSONString(result));
            return null;
        }

        int code = result.getCode();
        if (Objects.isNull(code) || code != 0){
            log.error("调用restart方法出现异常, code: {}", JSONObject.toJSONString(code));
            return null;
        }
        Integer data = (Integer)result.getData();
        return data;
    }
}
