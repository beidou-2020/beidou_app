package com.bd.entitys.query;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ReadQuery {
	
	/**
     * 书名
     */
    private String bookName;

    /**
     * 作者
     */
    private String author;
    
    /**
     * 阅读标志：1在读、2读完、3待读
     */
    private Integer readFlag;

    /**
     * 开始阅读时间
     */
    private String begintime;

    /**
     * 结束阅读时间
     */
    private String endtime;

    /**
     * 默认查询未删除的
     */
    private Integer validMark = 1;
    
}
