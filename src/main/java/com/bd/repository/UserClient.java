package com.bd.repository;

import com.alibaba.fastjson.JSONObject;
import com.bd.controller.common.Result;
import com.bd.entitys.dto.UserLoginDTO;
import com.bd.entitys.parame.RegisterUserParame;
import com.bd.exception.HttpErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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


}
