package com.bd.entitys.parame;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ToString
public class AddReadParam {
	
	/**
     * 书名
     */
	@NotEmpty(message = "添加阅读信息时书名不能为空")
    private String bookName;

    /**
     * 作者
     */
    @NotEmpty(message = "添加阅读信息时作者不能为空")
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
     * 附件
     */
    private MultipartFile[] readPicFiles;

    /**
     * 阅读内容分类
     */
    @NotNull(message = "添加阅读信息时内容分类不能为空")
    private Integer category;
    
}
