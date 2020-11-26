package com.bd.entitys.query;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StudyQuery {
	
	/**
     * 主题
     */
    private String title;

    /**
     * 学习项
     */
    private String items;
    
    /**
     * 计划开始时间
     */
    private String planBegintime;

    /**
     * 计划结束时间
     */
    private String planEndtime;

    /**
     * 默认查询未删除的
     */
    private Integer validMark = 1;
}
