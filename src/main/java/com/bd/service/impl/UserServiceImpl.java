package com.bd.service.impl;

import com.bd.controller.common.Result;
import com.bd.entitys.dto.AddUserDTO;
import com.bd.entitys.dto.UpdateUserDTO;
import com.bd.entitys.dto.UserLoginDTO;
import com.bd.entitys.parame.PageParam;
import com.bd.entitys.parame.RegisterUserParame;
import com.bd.entitys.query.UserQuery;
import com.bd.repository.UserClient;
import com.bd.repository.UserFeignClient;
import com.bd.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
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
    public Result add(AddUserDTO addUserDTO) {
        return userFeignClient.add(addUserDTO);
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
}
