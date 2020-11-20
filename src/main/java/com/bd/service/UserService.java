package com.bd.service;

import com.bd.controller.common.Result;
import com.bd.entitys.dto.AddUserDTO;
import com.bd.entitys.dto.UpdateUserDTO;
import com.bd.entitys.dto.UserLoginDTO;
import com.bd.entitys.parame.PageParam;
import com.bd.entitys.parame.RegisterUserParame;
import com.bd.entitys.query.UserQuery;

public interface UserService {

    /**
     * 校验用户的账号和密码
     */
    Result login(UserLoginDTO userLoginDTO);

    /**
     * 注册用户信息
     */
    Result insertUserInfo(RegisterUserParame user);

    /**
     * 用户列表
     * @param query
     * @param pageParam
     * @return
     */
    Result list(UserQuery query, PageParam pageParam);

    /**
     * 添加用户信息
     */
    Result add(AddUserDTO addUserDTO);

    /**
     * 更新用户信息
     * @param updateUserDTO
     * @return
     */
    Result update(UpdateUserDTO updateUserDTO);

    /**
     * 删除用户信息
     */
    Result delete(Long id);

    /**
     * 获取用户详情
     */
    Result userDetails(Long id);
}
