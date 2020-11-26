package com.bd.service;

import com.alibaba.fastjson.JSONObject;
import com.bd.controller.common.Result;
import com.bd.entitys.model.TZxzStudy;
import com.bd.entitys.parame.AddStudyParam;
import com.bd.entitys.parame.PageParam;
import com.bd.entitys.parame.UpdateStudyParam;
import com.bd.entitys.query.StudyQuery;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudyService {

    /**
     * 分页查询
     * @param queryStudy
     * @param pageQuery
     * @return
     */
    PageInfo<TZxzStudy> pageFindByQuery(StudyQuery queryStudy, PageParam pageQuery);

    /**
     * 分页查询已经被删除的数据列表
     * @param queryStudy
     * @param pageQuery
     * @return
     */
    PageInfo<TZxzStudy> pageFindByRemove(StudyQuery queryStudy, PageParam pageQuery);

    /**
     * 添加学习计划
     * @param param
     * @return
     */
    TZxzStudy addStudyInfo(AddStudyParam param);

    /**
     * 逻辑删除学习计划，计划默认终止
     * @param id
     * @return
     */
    TZxzStudy deleteStudyPlan(Long id);

    /**
     * 根据主键获取学习计划
     * @param id
     * @return
     */
    TZxzStudy findById(Long id);

    /**
     * 根据主键修改学习计划
     * @param param
     * @return
     */
    TZxzStudy updateStudyById(UpdateStudyParam param);

    /**
     * 上传文件和批量导入Excel文件数据
     * @param planFile
     * @return
     */
    JSONObject importStudyPlanExcelFile(MultipartFile planFile);

    /**
     * 手动同步各项计划的执行状态
     */
    Result syncTaskStatus();

    /**
     * 获取当月计划结束的学习计划内容(执行中的)
     */
    List<TZxzStudy> endStudyByCurrentMonth();

    /**
     * 获取累计计划项
     */
    Integer countStudyNumber();

    /**
     * 挂起计划
     * @return
     */
    Integer hangStudy(Long id);

    /**
     * 重启计划
     * @param id
     * @return
     */
    Integer trunOnStudy(Long id);

    /**
     * 批量删除计划(逻辑删除)
     * @return
     */
    Integer batchDelete(String idListStr);

}
