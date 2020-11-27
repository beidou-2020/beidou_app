package com.bd.service.impl;

import com.alibaba.fastjson.JSONObject;
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
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ReadServiceImpl implements ReadService {

    @Resource
    private ReadFeignClient readFeignClient;

    @Resource
    private FileClient fileClient;

    @Override
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
        Result result = readFeignClient.todayYearByReadFlag();
        if (Objects.isNull(result)){
            return null;
        }

        int code = result.getCode();
        if (Objects.isNull(code) || code != 0){
            return null;
        }
        List data = (List<THistoricalReading>)result.getData();
        return data;
    }

    @Override
    public Integer countReadNumber() {
        Result result = readFeignClient.countReadNumber();
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
