package com.bd.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bd.controller.common.Result;
import com.bd.entitys.dto.AddUserDTO;
import com.bd.entitys.dto.UpdateUserDTO;
import com.bd.entitys.dto.UserLoginDTO;
import com.bd.entitys.model.User;
import com.bd.entitys.parame.PageParam;
import com.bd.entitys.parame.RegisterUserParame;
import com.bd.entitys.query.UserQuery;
import com.bd.repository.UserClient;
import com.bd.repository.UserFeignClient;
import com.bd.service.UserService;
import com.bd.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserClient userClient;

    @Resource
    private UserFeignClient userFeignClient;

    @Override
    public Result login(UserLoginDTO userLoginDTO) {
        return userClient.userServiceLogin(userLoginDTO);
    }

    @Override
    public Result insertUserInfo(RegisterUserParame user) {
        return userClient.userServiceRegister(user);
    }

    @Override
    public Result list(UserQuery query, PageParam pageParam) {
        return userFeignClient.list(pageParam.getCurrentPageNumber(),
                pageParam.getPageSize(),
                query.getAccount(),
                query.getName(),
                query.getGender(),
                query.getUserType());
    }

    @Override
    public User add(AddUserDTO addUserDTO) {
        Result addResult = userFeignClient.add(addUserDTO);
        if (Objects.isNull(addResult)) {
            log.error("添加用户信息调用user-service服务的响应信息为空, response: {}", JSONObject.toJSONString(addResult));
            return null;
        }

        int code = addResult.getCode();
        String message = addResult.getMessage();
        Object data = addResult.getData();
        if (Objects.isNull(code) || 0!=code || Objects.isNull(data)){
            log.error("添加用户信息调用user-service服务异常, msg: {}", message);
            return null;
        }

        User user = JsonUtils.toBean(JsonUtils.toJSONString(data), User.class);
        return user;
    }

    @Override
    public Result update(UpdateUserDTO updateUserDTO) {
        return userFeignClient.update(updateUserDTO);
    }

    @Override
    public Result delete(Long id) {
        return userFeignClient.delete(id);
    }

    @Override
    public Result userDetails(Long id) {
        return userFeignClient.userDetails(id);
    }

    @Override
    public Integer batchDelete(String idListStr) {
        Result result = userFeignClient.batchDelete(idListStr);
        if (Objects.isNull(result)){
            return null;
        }
        int code = result.getCode();
        String message = result.getMessage();
        Object data = result.getData();
        if (Objects.isNull(code) || 0!=code || Objects.isNull(data)){
            log.error("批量删除用户信息时调用user-service服务异常, msg: {}", message);
            return null;
        }

        Integer batchDelData = JsonUtils.toBean(JsonUtils.toJSONString(data), Integer.class);
        return batchDelData;
    }
}
