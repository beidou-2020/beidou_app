package com.bd.repository;

import com.bd.controller.common.Result;
import com.bd.entitys.dto.AddUserDTO;
import com.bd.entitys.dto.UpdateUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service", path = "/user")
public interface UserFeignClient {

    /**
     * 用户列表
     */
    @GetMapping(value = "/list", produces = "application/json;charset=utf-8")
    @ResponseBody
    Result list(@RequestParam(name = "currentPageNumber") Integer currentPageNumber,
                @RequestParam(name = "pageSize") Integer pageSize,
                @RequestParam(name = "account")String account,
                @RequestParam(name = "name")String name,
                @RequestParam(name = "gender")Integer gender,
                @RequestParam(name = "userType")Integer userType);

    /**
     * 添加用户信息
     */
    @PostMapping("/add")
    @ResponseBody
    Result add(@RequestBody AddUserDTO addUserDTO);

    /**
     * 更新用户信息
     * @param updateUserDTO
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    Result update(@RequestBody UpdateUserDTO updateUserDTO);

    /**
     * 删除用户信息
     */
    @PostMapping("/delete/{id}")
    @ResponseBody
    Result delete(@PathVariable(name = "id") Long id);

    /**
     * 获取用户详情
     */
    @GetMapping("/userDetails/{id}")
    @ResponseBody
    Result userDetails(@PathVariable(name = "id") Long id);

    /**
     * 批量删除用户信息
     */
    @PostMapping("/batchDelete/{idListStr}")
    @ResponseBody
    Result batchDelete(@PathVariable("idListStr") String idListStr);
}
