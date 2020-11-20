package com.bd.entitys.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class TZxzStudy {
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
    private Date planBegintime;

    /**
     * 计划结束时间
     */
    private Date planEndtime;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;
    
    /**
     * 有效标志：1启用、0删除
     */
    private Integer validMark;
    
    /**
     * 计划状态：0待执行，1执行中，2已结束
     */
    private Integer planStatus;
}