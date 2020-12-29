package com.bd.repository;

import com.alibaba.fastjson.JSONObject;
import com.bd.controller.common.Result;
import com.bd.entitys.dto.AddReadDTO;
import com.bd.entitys.dto.UpdateReadDTO;
import com.bd.entitys.model.THistoricalReading;
import com.bd.entitys.parame.PageParam;
import com.bd.entitys.query.ReadQuery;
import com.bd.exception.HttpErrorException;
import com.bd.utils.HttpClientUtil;
import com.bd.utils.RestClientHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class ReadClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${read.list}")
    private String listUrl;

    @Value("${read.add}")
    private String addUrl;

    @Value("${read.details}")
    private String detailsUrl;

    @Value("${read.update}")
    private String updateUrl;

    @Value("${read.delete}")
    private String deleteUrl;

    @Value("${read.todayYearByReadFlag}")
    private String todayYearByReadFlagUrl;

    @Value("${read.countReadNumber}")
    private String countReadNumberUrl;

    @Value("${read.batchDelete}")
    private String batchDeleteUrl;

    @Value("${read.timeOut}")
    private String timeOutUrl;

    @Value("${read.restart}")
    private String restartUrl;

    /**
     * 阅读信息列表
     */
    public PageInfo<THistoricalReading> list(PageParam pageParam, ReadQuery query){
        Map<String, Object> param = new HashMap<>();
        param.put("bookName", query.getBookName());
        param.put("author", query.getAuthor());
        param.put("readFlag", query.getReadFlag());
        param.put("begintime", query.getBegintime());
        param.put("endtime", query.getEndtime());
        param.put("validMark", query.getValidMark());
        param.put("currentPageNumber", pageParam.getCurrentPageNumber());
        param.put("pageSize", pageParam.getPageSize());
        try{
            // 多参数下处理get请求的URL地址
            String sendUrl = HttpClientUtil.getForObject(listUrl, param);
            ResponseEntity<PageInfo> responseEntity = restTemplate.getForEntity(sendUrl, PageInfo.class);
            if (Objects.isNull(responseEntity)){
                log.error("请求read-list接口为空：{}", JSONObject.toJSONString(responseEntity));
                return null;
            }

            PageInfo body = responseEntity.getBody();
            log.info("list:{}", JSONObject.toJSONString(body));
            return body;
        }catch (Exception ex){
            log.error("调用read-server服务的list方法异常：{}", RestClientHelper.getUrl(listUrl, param), ex);
        }

        return null;
    }

    /**
     * 添加阅读信息
     */
    public THistoricalReading add(AddReadDTO param){
        try{
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            HttpEntity entity = new HttpEntity(param, headers);
            ResponseEntity<THistoricalReading> responseEntity = restTemplate.
                    postForEntity(addUrl, entity, THistoricalReading.class);
            if (Objects.isNull(responseEntity)){
                return null;
            }

            THistoricalReading body = responseEntity.getBody();
            log.info("readInfo信息保存成功：{}", JSONObject.toJSONString(body));
            return body;
        }catch (Exception ex){
            log.error("调用read-server服务的add方法异常：", RestClientHelper.getUrl(addUrl, param));
            throw new HttpErrorException("调用read-server服务的add方法异常", ex);
        }
    }

    /**
     * 查看阅读详情
     */
    public THistoricalReading readDetails(Long id){
        try{
            ResponseEntity<THistoricalReading> responseEntity = restTemplate.
                    getForEntity(detailsUrl, THistoricalReading.class, id);
            if (Objects.isNull(responseEntity)){
                return null;
            }

            THistoricalReading body = responseEntity.getBody();
            log.info("阅读计划详情：{}", JSONObject.toJSONString(body));
            return body;
        }catch (Exception ex){
            log.error("调用read-server服务的readDetails方法异常：{}", RestClientHelper.getUrl(detailsUrl, id), ex);
        }
        return null;
    }

    /**
     * 更新阅读信息
     */
    public THistoricalReading update(UpdateReadDTO param){
        try{
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            HttpEntity entity = new HttpEntity(param, headers);
            ResponseEntity<THistoricalReading> responseEntity = restTemplate.
                    postForEntity(updateUrl, entity, THistoricalReading.class);
            if (Objects.isNull(responseEntity)){
                return null;
            }

            THistoricalReading body = responseEntity.getBody();
            return body;
        }catch (Exception ex){
            log.error("调用read-server服务的update方法异常：{}", RestClientHelper.getUrl(updateUrl, param), ex);
        }
        return null;
    }

    /**
     * 删除阅读信息
     */
    public THistoricalReading deleteById(Long id){
        try{
            ResponseEntity<THistoricalReading> responseEntity = restTemplate.
                    postForEntity(deleteUrl + id, null, THistoricalReading.class);
            if (Objects.isNull(responseEntity)){
                return null;
            }

            THistoricalReading body = responseEntity.getBody();
            log.info("阅读计划已删除：{}", JSONObject.toJSONString(body));
            return body;
        }catch (Exception ex){
            log.error("调用read-server服务的delete方法异常：{}", RestClientHelper.getUrl(deleteUrl, id), ex);
        }
        return null;
    }

    /**
     * 根据阅读状态获取今年的阅读历史数据(2：读完)
     */
    public Result todayYearByReadFlag(){
        try{
            ResponseEntity<Result> responseEntity = restTemplate.getForEntity(todayYearByReadFlagUrl, Result.class);
            log.info("调用read-server服务的todayYearByReadFlagUrl方法的返回值：{}", JSONObject.toJSONString(responseEntity));
            if (Objects.isNull(responseEntity)){
                return null;
            }

            Result body = responseEntity.getBody();
            if (Objects.isNull(body)){
                return null;
            }
            Integer code = body.getCode();
            if (Objects.isNull(code) || code != 0){
                return null;
            }

            return body;
        }catch (Exception ex){
            throw new HttpErrorException("调用read-server服务的todayYearByReadFlagUrl方法异常", ex);
        }
    }

    /**
     * 获取累计阅读量
     */
    public Result countReadNumber(){
        try{
            ResponseEntity<Result> responseEntity = restTemplate.getForEntity(countReadNumberUrl, Result.class);
            log.info("调用read-server服务的countReadNumber方法的返回值：{}", JSONObject.toJSONString(responseEntity));
            if (Objects.isNull(responseEntity)){
                return null;
            }

            Result body = responseEntity.getBody();
            if (Objects.isNull(body)){
                return null;
            }
            Integer code = body.getCode();
            if (Objects.isNull(code) || code != 0){
                return null;
            }

            return body;
        }catch (Exception ex){
            throw new HttpErrorException("调用read-server服务的countReadNumber方法异常", ex);
        }
    }

    /**
     * 批量删除阅读信息
     */
    public Result batchDelete(String idListStr){
        try{
            ResponseEntity<Result> responseEntity = restTemplate.
                    postForEntity(batchDeleteUrl + idListStr, null, Result.class);
            log.info("调用read-server服务的batchDelete方法的返回值：{}", JSONObject.toJSONString(responseEntity));
            if (Objects.isNull(responseEntity)){
                return null;
            }

            Result body = responseEntity.getBody();
            if (Objects.isNull(body)){
                return null;
            }
            Integer code = body.getCode();
            if (Objects.isNull(code) || code != 0){
                return null;
            }

            return body;
        }catch (Exception ex){
            throw new HttpErrorException("调用read-server服务的batchDelete方法异常", ex);
        }
    }

    /**
     * 暂停阅读信息
     */
    public Result timeOutReadInfo(Long id){
        try{
            ResponseEntity<Result> responseEntity = restTemplate.postForEntity(timeOutUrl + id, null, Result.class);
            log.info("调用read-server服务的timeOut方法的返回值：{}", JSONObject.toJSONString(responseEntity));
            if (Objects.isNull(responseEntity)){
                return null;
            }

            Result body = responseEntity.getBody();
            if (Objects.isNull(body)){
                return null;
            }
            Integer code = body.getCode();
            if (Objects.isNull(code) || code != 0){
                return null;
            }

            return body;
        }catch (Exception ex){
            throw new HttpErrorException("调用read-server服务的timeOut方法异常", ex);
        }
    }

    /**
     * 重新开始阅读
     */
    public Result restartReadInfo(Long id){
        try{
            ResponseEntity<Result> responseEntity = restTemplate.postForEntity(restartUrl + id, null, Result.class);
            log.info("调用read-server服务的restart方法的返回值：{}", JSONObject.toJSONString(responseEntity));
            if (Objects.isNull(responseEntity)){
                return null;
            }

            Result body = responseEntity.getBody();
            if (Objects.isNull(body)){
                return null;
            }
            Integer code = body.getCode();
            if (Objects.isNull(code) || code != 0){
                return null;
            }

            return body;
        }catch (Exception ex){
            throw new HttpErrorException("调用read-server服务的restart方法异常", ex);
        }
    }


}
