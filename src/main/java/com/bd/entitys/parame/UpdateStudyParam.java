package com.bd.entitys.parame;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ToString
public class UpdateStudyParam {
	
	@NotNull(message = "修改学习计划时ID不能为空")
	private Long id;
	
	/**
     * 主题
     */
	@NotEmpty(message = "修改学习计划时主题不能为空")
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
    @NotNull(message = "修改学习计划时开始时间不能为空")
    private Date planBegintime;

    /**
     * 计划结束时间
     */
    @NotNull(message = "修改学习计划时截止时间不能为空")
    private Date planEndtime;
}
