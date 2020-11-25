package com.bd.entitys.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class THistoricalReading {
    /**
     * ID
     */
    private Long id;

    /**
     * 书名
     */
    private String bookName;

    /**
     * 作者
     */
    private String author;

    /**
     * 备注
     */
    private String remark;

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
     * 创建时间
     */
    private String createtime;

    /**
     * 更新时间
     */
    private String updatetime;
    
    /**
     * 有效标志
     */
    private Integer validMark;
    
    /**
     * 阅读截图名(存储在文件服务器上的)
     */
    private String screenshotName;

    /**
     * 阅读内容分类
     */
    private Integer category;
}