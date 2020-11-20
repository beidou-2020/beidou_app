package com.bd.entitys.parame;

import com.bd.entitys.model.TZxzStudy;
import lombok.Data;
import lombok.ToString;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@ToString
public class ImportStudyParam {

    @NotNull(message = "导入时学习计划列表不能为空")
    @Size(min = 1, message = "数据列表不能为0")
    private List<TZxzStudy> list;
}
