package com.bd.entitys.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class UpdateReadDTO {
	
	/**
	 * 主键
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
    private Date begintime;

    /**
     * 结束阅读时间
     */
    private Date endtime;

	/**
	 * 阅读截图名(存储在文件服务器上的图片路径)
	 */
	private String screenshotName;

    /**
     * 阅读内容分类
     */
    private Integer category;
}
