package com.bd.entitys.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    /**
     * ID
     */
    private Long id;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String name;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 是否有效
     */
    private Integer validMark;

    /**
     * 注册时间
     */
    private String createtime;
    
    /**
     * 更新时间
     */
    private String updatetime;

}