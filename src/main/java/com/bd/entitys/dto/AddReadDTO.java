package com.bd.entitys.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class AddReadDTO {
	
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
     * 开始阅读时间
     */
    private Date begintime;

    /**
     * 结束阅读时间
     */
    private Date endtime;
    
    /**
     * 阅读标志：1在读、2读完、3待读
     */
    private Integer readFlag;

    /**
     * 阅读截图名(存储在文件服务器上的图片路径，多张图片名称之间用逗号分隔)
     */
    private String screenshotName;
}
