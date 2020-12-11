package com.bd.entitys.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class TZxzStudy implements Serializable {
    /**
     * ID
     */
    private Long id;

    /**
     * 主题
     */
    private String title;

    /**
     * 学习项
     */
    private String items;

    /**
     * 备注
     */
    private String remark;

    /**
     * 计划开始时间
     */
    private String planBegintime;

    /**
     * 计划结束时间
     */
    private String planEndtime;

    /**
     * 创建时间
     */
    private String createtime;

    /**
     * 更新时间
     */
    private String updatetime;
    
    /**
     * 有效标志：1启用、0删除
     */
    private Integer validMark;
    
    /**
     * 计划状态：0待执行，1执行中，2已结束
     */
    private Integer planStatus;

    /**
     * 数据删除时间
     */
    private String removetime;

    /**
     * 数据删除操作者
     */
    private Long removeUser;
}