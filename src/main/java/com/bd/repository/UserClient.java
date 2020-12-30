package com.bd.repository;

import com.alibaba.fastjson.JSONObject;
import com.bd.controller.common.Result;
import com.bd.entitys.dto.AddUserDTO;
import com.bd.entitys.dto.UpdateUserDTO;
import com.bd.entitys.dto.UserLoginDTO;
import com.bd.entitys.parame.PageParam;
import com.bd.entitys.parame.RegisterUserParame;
import com.bd.entitys.query.UserQuery;
import com.bd.exception.HttpErrorException;
import com.bd.utils.HttpClientUtil;
import com.bd.utils.RestClientHelper;
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
public class UserClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${user.login}")
    private String loginUrl;

    @Value("${user.register}")
    private String registerUrl;

    @Value("${user.list}")
    private String listUrl;

    @Value("${user.add}")
    private String addUrl;

    @Value("${user.update}")
    private String updateUrl;

    @Value("${user.delete}")
    private String deleteUrl;

    @Value("${user.userDetails}")
    private String detailsUrl;

    @Value("${user.batchDelete}")
    private String batchDeleteUrl;

    /**
     * 根据用户账号和密码查询用户信息
     * @param userLoginDTO
     * @return
     */
    public Result userServiceLogin(UserLoginDTO userLoginDTO){
        String account = userLoginDTO.getAccount();
        String password = userLoginDTO.getPassword();
        try{
            ResponseEntity<Result> responseEntity = restTemplate.getForEntity(loginUrl, Result.class, account, password);
            log.info("调用user-service服务的login方法的返回值：{}", JSONObject.toJSONString(responseEntity));
            if (Objects.isNull(responseEntity)){
                return null;
            }

            Result body = responseEntity.getBody();
            if (Objects.isNull(body)){
                return null;
            }
            return body;
        }catch (Exception ex){
            throw new HttpErrorException("调用user-service服务的login方法异常", ex);
        }
    }

    /**
     * 保存用户信息
     * @param user
     * @return
     */
    public Result userServiceRegister(RegisterUserParame user){
        try{
            ResponseEntity<Result> responseEntity = restTemplate.postForEntity(registerUrl, user, Result.class);
            log.info("调用user-service服务的register方法的返回值：{}", JSONObject.toJSONString(responseEntity));
            if (Objects.isNull(responseEntity)){
                return null;
            }

            Result body = responseEntity.getBody();
            if (Objects.isNull(body)){
                return null;
            }
            return body;
        }catch (Exception ex){
            throw new HttpErrorException("调用user-service服务的register方法异常", ex);
        }
    }

    /**
     * 用户列表
     */
    public Result list(PageParam pageParam, UserQuery query){
        Map<String, Object> param = new HashMap<>();
        param.put("currentPageNumber", pageParam.getCurrentPageNumber());
        param.put("pageSize", pageParam.getPageSize());
        param.put("account", query.getAccount());
        param.put("name", query.getName());
        param.put("gender", query.getGender());
        param.put("userType", query.getUserType());
        try{
            // 多参数下处理get请求的URL地址
            String sendUrl = HttpClientUtil.getForObject(listUrl, param);
            ResponseEntity<Result> responseEntity = restTemplate.getForEntity(sendUrl, Result.class);
            if (Objects.isNull(responseEntity)){
                log.error("请求user-list接口为空：{}", JSONObject.toJSONString(responseEntity));
                return null;
            }

            Result body = responseEntity.getBody();
            log.info("list:{}", JSONObject.toJSONString(body));
            return body;
        }catch (Exception ex){
            log.error("调用user-server服务的list方法异常：{}", RestClientHelper.getUrl(listUrl, param), ex);
        }

        return null;
    }

    /**
     * 添加用户信息
     */
    public Result add(AddUserDTO param){
        try{
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            HttpEntity entity = new HttpEntity(param, headers);
            ResponseEntity<Result> responseEntity = restTemplate.
                    postForEntity(addUrl, entity, Result.class);
            if (Objects.isNull(responseEntity)){
                return null;
            }

            Result body = responseEntity.getBody();
            return body;
        }catch (Exception ex){
            log.error("调用user-server服务的add方法异常：", RestClientHelper.getUrl(addUrl, param));
            throw new HttpErrorException("调用user-server服务的add方法异常", ex);
        }
    }

    /**
     * 更新用户信息
     * @return
     */
    public Result update(UpdateUserDTO param){
        try{
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            HttpEntity entity = new HttpEntity(param, headers);
            ResponseEntity<Result> responseEntity = restTemplate.
                    postForEntity(updateUrl, entity, Result.class);
            if (Objects.isNull(responseEntity)){
                return null;
            }

            Result body = responseEntity.getBody();
            log.info("用户信息更新成功：{}", JSONObject.toJSONString(body));
            return body;
        }catch (Exception ex){
            log.error("调用user-server服务的update方法异常：", RestClientHelper.getUrl(updateUrl, param));
            throw new HttpErrorException("调用user-server服务的update方法异常", ex);
        }
    }

    /**
     * 删除用户信息
     */
    public Result delete(Long id){
        try{
            ResponseEntity<Result> responseEntity = restTemplate.
                    postForEntity(deleteUrl + id, null, Result.class);
            if (Objects.isNull(responseEntity)){
                return null;
            }

            Result body = responseEntity.getBody();
            log.info("用户已删除：{}", JSONObject.toJSONString(body));
            return body;
        }catch (Exception ex){
            log.error("调用user-server服务的delete方法异常：{}", RestClientHelper.getUrl(deleteUrl, id), ex);
        }
        return null;
    }

    /**
     * 获取用户详情
     */
    public Result userDetails(Long id){
        try{
            ResponseEntity<Result> responseEntity = restTemplate.
                    getForEntity(detailsUrl, Result.class, id);
            if (Objects.isNull(responseEntity)){
                return null;
            }

            Result body = responseEntity.getBody();
            log.info("用户详情：{}", JSONObject.toJSONString(body));
            return body;
        }catch (Exception ex){
            log.error("调用user-server服务的userDetails方法异常：{}", RestClientHelper.getUrl(detailsUrl, id), ex);
        }
        return null;
    }

    /**
     * 批量删除用户信息
     */
    public Result batchDelete(String idListStr){
        try{
            ResponseEntity<Result> responseEntity = restTemplate.
                    postForEntity(batchDeleteUrl + idListStr, null, Result.class);
            if (Objects.isNull(responseEntity)){
                return null;
            }

            Result body = responseEntity.getBody();
            log.info("用户已批量删除：{}", JSONObject.toJSONString(body));
            return body;
        }catch (Exception ex){
            log.error("调用user-server服务的batchDelete方法异常：{}", RestClientHelper.getUrl(batchDeleteUrl, idListStr), ex);
        }
        return null;
    }


}
