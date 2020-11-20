package com.bd.repository;

import com.alibaba.fastjson.JSONObject;
import com.bd.controller.common.Result;
import com.bd.entitys.enumerate.ResultCode;
import com.bd.entitys.model.TZxzStudy;
import com.bd.entitys.parame.AddStudyParam;
import com.bd.entitys.parame.ImportStudyParam;
import com.bd.entitys.parame.PageParam;
import com.bd.entitys.parame.UpdateStudyParam;
import com.bd.entitys.query.StudyQuery;
import com.bd.exception.HttpErrorException;
import com.bd.utils.HttpClientUtil;
import com.bd.utils.RestClientHelper;
import com.github.pagehelper.PageInfo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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
public class StudyClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${study.list}")
    private String listUrl;

    @Value("${study.add}")
    private String addUrl;

    @Value("${study.delete}")
    private String deleteUrl;

    @Value("${study.details}")
    private String detailsUrl;

    @Value("${study.update}")
    private String updateUrl;

    @Value("${study.batchInsert}")
    private String batchInsertUrl;

    @Value("${study.syncTaskStatus}")
    private String syncTaskStatusUrl;

    @Value("${study.endStudyByCurrentMonth}")
    private String endStudyByCurrentMonthUrl;

    @Value("${study.countStudyNumber}")
    private String countStudyNumberUrl;

    @Value("${study.hangStudy}")
    private String hangUrl;

    @Value("${study.trunOnStudy}")
    private String trunOnUrl;

    /**
     * 学习计划列表
     */
    public PageInfo<TZxzStudy> list(StudyQuery queryStudy, PageParam pageQuery){
        Map<String, Object> param = new HashMap<>();
        param.put("title", queryStudy.getTitle());
        param.put("items", queryStudy.getItems());
        param.put("planBegintime", queryStudy.getPlanBegintime());
        param.put("planEndtime", queryStudy.getPlanEndtime());
        param.put("currentPageNumber", pageQuery.getCurrentPageNumber());
        param.put("pageSize", pageQuery.getPageSize());

        try{
            // 多参数下处理get请求的URL地址
            String sendUrl = HttpClientUtil.getForObject(listUrl, param);
            ResponseEntity<PageInfo> responseEntity = restTemplate.getForEntity(sendUrl, PageInfo.class);
            if (Objects.isNull(responseEntity)){
                return null;
            }

            PageInfo body = responseEntity.getBody();
            log.info("list:{}", JSONObject.toJSONString(body));
            return body;
        }catch (Exception ex){
            log.error("调用study-server服务的list方法异常：{}", RestClientHelper.getUrl(listUrl, param), ex);
        }

        return null;
    }

    /**
     *添加学习计划
     */
    public TZxzStudy add(AddStudyParam param){
        try{
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            HttpEntity entity = new HttpEntity(param, headers);
            ResponseEntity<TZxzStudy> responseEntity = restTemplate.postForEntity(addUrl, entity, TZxzStudy.class);
            if (Objects.isNull(responseEntity)){
                return null;
            }

            TZxzStudy body = responseEntity.getBody();
            log.info("studyInfo信息保存成功：{}", JSONObject.toJSONString(body));
            return body;
        }catch (Exception ex){
            log.error("调用study-server服务的add方法异常：", RestClientHelper.getUrl(addUrl, param));
            throw new HttpErrorException("调用study-server服务的add方法异常", ex);
        }
    }

    /**
     * 删除学习计划
     */
    public TZxzStudy delete(Long id){
        try{
            ResponseEntity<TZxzStudy> responseEntity = restTemplate.postForEntity(deleteUrl + id, null, TZxzStudy.class);
            if (Objects.isNull(responseEntity)){
                return null;
            }

            TZxzStudy body = responseEntity.getBody();
            log.info("学习计划已删除：{}", JSONObject.toJSONString(body));
            return body;
        }catch (Exception ex){
            log.error("调用study-server服务的delete方法异常：{}", RestClientHelper.getUrl(deleteUrl, id), ex);
        }
        return null;
    }

    /**
     * 查看学习计划详情信息
     */
    public TZxzStudy details(Long id){
        try{
            ResponseEntity<TZxzStudy> responseEntity = restTemplate.getForEntity(detailsUrl, TZxzStudy.class, id);
            if (Objects.isNull(responseEntity)){
                return null;
            }

            TZxzStudy body = responseEntity.getBody();
            log.info("学习计划详情：{}", JSONObject.toJSONString(body));
            return body;
        }catch (Exception ex){
            log.error("调用study-server服务的studyDetails方法异常：{}", RestClientHelper.getUrl(detailsUrl, id), ex);
        }
        return null;
    }

    /**
     * 更新学习计划信息
     */
    public TZxzStudy update(UpdateStudyParam param){
        try{
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            HttpEntity entity = new HttpEntity(param, headers);
            ResponseEntity<TZxzStudy> responseEntity = restTemplate.postForEntity(updateUrl, entity, TZxzStudy.class);
            if (Objects.isNull(responseEntity)){
                return null;
            }

            TZxzStudy body = responseEntity.getBody();
            return body;
        }catch (Exception ex){
            log.error("调用study-server服务的update方法异常：{}", RestClientHelper.getUrl(updateUrl, param), ex);
        }
        return null;
    }

    /**
     * 批量插入学习计划
     */
    public Integer batchInsert(ImportStudyParam param){
        try{
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            HttpEntity entity = new HttpEntity(param, headers);
            ResponseEntity<Integer> responseEntity = restTemplate.postForEntity(batchInsertUrl, entity, Integer.class);
            if (Objects.isNull(responseEntity)){
                return null;
            }

            Integer body = responseEntity.getBody();
            return body;
        }catch (Exception ex){
            log.error("调用study-server服务的batchInsert方法异常：{}", RestClientHelper.getUrl(batchInsertUrl, param), ex);
        }
        return null;
    }

    /**
     * 手动同步各项计划的执行状态
     */
    public Result syncTaskStatus(){
        try{
            ResponseEntity<Result> responseEntity = restTemplate.getForEntity(syncTaskStatusUrl, Result.class);
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
            throw new HttpErrorException("调用study-server服务的syncTaskStatus方法异常", ex);
        }
    }

    /**
     * 获取当月计划结束的学习计划内容(执行中的)
     */
    @HystrixCommand(fallbackMethod = "endStudyByCurrentMonthFallback")
    public Result endStudyByCurrentMonth(){
        try{
            ResponseEntity<Result> responseEntity = restTemplate.getForEntity(endStudyByCurrentMonthUrl, Result.class);
            log.info("调用study-server服务的endStudyByCurrentMonth方法的返回值：{}", JSONObject.toJSONString(responseEntity));
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
            throw new HttpErrorException("调用study-server服务的endStudyByCurrentMonth方法异常", ex);
        }
    }
    public Result endStudyByCurrentMonthFallback(){
        return Result.fail(ResultCode.RESPONSE_FUSE.code(),ResultCode.RESPONSE_FUSE.msg());
    }

    /**
     * 获取累计计划项
     */
    @HystrixCommand(fallbackMethod = "countStudyNumberFallback")
    public Result countStudyNumber(){
        try{
            ResponseEntity<Result> responseEntity = restTemplate.getForEntity(countStudyNumberUrl, Result.class);
            log.info("调用study-server服务的countStudyNumber方法的返回值：{}", JSONObject.toJSONString(responseEntity));
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
            throw new HttpErrorException("调用study-server服务的countStudyNumber方法异常", ex);
        }
    }
    public Result countStudyNumberFallback(){
        return Result.fail(ResultCode.RESPONSE_FUSE.code(),ResultCode.RESPONSE_FUSE.msg());
    }


    /**
     * 挂起计划
     * @return
     */
    public Integer hangStudy(Long id){
        try{
            ResponseEntity<Result> responseEntity = restTemplate.postForEntity(hangUrl + id, null, Result.class);
            log.info("调用study-server服务的hang方法的返回值：{}", JSONObject.toJSONString(responseEntity));
            if (Objects.isNull(responseEntity)){
                return 0;
            }

            Result body = responseEntity.getBody();
            if (Objects.isNull(body)){
                return 0;
            }
            Integer code = body.getCode();
            if (Objects.isNull(code) || code != 0){
                return 0;
            }

            Integer data = (Integer) body.getData();
            return data;
        }catch (Exception ex){
            throw new HttpErrorException("调用study-server服务的hang方法异常", ex);
        }
    }

    /**
     * 重启计划
     * @return
     */
    public Integer trunOnStudy(Long id){
        try{
            ResponseEntity<Result> responseEntity = restTemplate.postForEntity(trunOnUrl + id, null, Result.class);
            log.info("调用study-server服务的trunOnStudy方法的返回值：{}", JSONObject.toJSONString(responseEntity));
            if (Objects.isNull(responseEntity)){
                return 0;
            }

            Result body = responseEntity.getBody();
            if (Objects.isNull(body)){
                return 0;
            }
            Integer code = body.getCode();
            if (Objects.isNull(code) || code != 0){
                return 0;
            }

            Integer data = (Integer) body.getData();
            return data;
        }catch (Exception ex){
            throw new HttpErrorException("调用study-server服务的trunOnStudy方法异常", ex);
        }
    }


}
