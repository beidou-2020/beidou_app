package com.bd.controller;

import com.alibaba.fastjson.JSONObject;
import com.bd.controller.common.Result;
import com.bd.entitys.enumerate.ResultCode;
import com.bd.entitys.model.TZxzStudy;
import com.bd.entitys.parame.AddStudyParam;
import com.bd.entitys.parame.PageParam;
import com.bd.entitys.parame.UpdateStudyParam;
import com.bd.entitys.query.StudyQuery;
import com.bd.service.StudyService;
import com.bd.utils.FileUtils;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/study")
@Slf4j
public class StudyController {

    @Resource
    private StudyService studyService;

    /**
     * 学习计划列表：缓存阻挡
     * @param queryStudy
     * @param pageParam
     * @return
     */
    @GetMapping(value = "/list", produces = "application/json;charset=utf-8")
    public ModelAndView list(StudyQuery queryStudy, @Valid PageParam pageParam){
        PageInfo<TZxzStudy> pageInfo = studyService.pageFindByQuery(queryStudy, pageParam);
        return new ModelAndView("study/list").
                addObject("list", pageInfo.getList()).
                addObject("total", pageInfo.getTotal()).
                addObject("totalPage", pageInfo.getPages()).
                addObject("currPageNumber", pageParam.getCurrentPageNumber()).
                addObject("query", queryStudy);
    }

    /**
     * 跳转到添加页面
     * @return
     */
    @GetMapping(value = "/toAddView")
    public ModelAndView toAddView() {
        return new ModelAndView("study/add");
    }

    /**
     * 添加学习计划(ajax请求)
     * @param param
     * @return
     */
    @PostMapping(value = "/add", produces = "application/json;charset=utf-8")
    public String add(@RequestBody @Valid AddStudyParam param) {
        TZxzStudy result = studyService.addStudyInfo(param);
        return JSONObject.toJSONString(result);
    }

    /**
     * 删除学习计划
     * @param id
     * @return
     */
    @PostMapping(value = "/delete/{id}")
    public String deleteById(@PathVariable(name = "id") Long id) {
        TZxzStudy result = studyService.deleteStudyPlan(id);
        return JSONObject.toJSONString(result);
    }

    /**
     * 跳转到详情页面：缓存阻挡
     * @param id
     * @return
     */
    @GetMapping("/toDetailsView/{id}")
    public ModelAndView toDetailsView(@PathVariable("id") Long id) {
        TZxzStudy study = studyService.findById(id);
        return new ModelAndView("study/details").
                addObject("studyInfo", study);
    }

    /**
     * 跳转到编辑页面
     * @param id
     * @return
     */
    @GetMapping("/toEditView/{id}")
    public ModelAndView toEditView(@PathVariable("id") Long id) {
        TZxzStudy study = studyService.findById(id);
        return new ModelAndView("study/edit").
                addObject("studyInfo", study);
    }

    /**
     * 更新学习计划信息
     * @param param
     * @return
     */
    @PostMapping("/update")
    public String updateStudyInfo(@RequestBody @Valid UpdateStudyParam param) {
        TZxzStudy result = studyService.updateStudyById(param);
        return JSONObject.toJSONString(result);
    }

    /**
     * 导入学习计划表
     * @param planFile
     * @return
     */
    @PostMapping("/import")
    public String importStudyPlan(@RequestParam("studyPlanExcel")MultipartFile planFile){
        JSONObject resultJsonObject = new JSONObject();
        // ==========基本校验开始
        if (Objects.isNull(planFile)) {
            log.info("导入学习计划文件数据时文件为空：{}", JSONObject.toJSONString(planFile));
            resultJsonObject.put("code", 0);
            resultJsonObject.put("msg", "文件不能为空");
            return resultJsonObject.toJSONString();
        }

        String sourceFileName = planFile.getOriginalFilename();		//获取源文件名
        if (StringUtils.isEmpty(sourceFileName) || !FileUtils.validateExcel(sourceFileName)) {
            log.info("导入学习计划文件数据时文件名为空或不是Excel类型的文件：{}", JSONObject.toJSONString(sourceFileName));
            resultJsonObject.put("code", 0);
            resultJsonObject.put("msg", "文件名为空或不是Excel类型的文件");
            return resultJsonObject.toJSONString();
        }

        JSONObject jsonObject = studyService.importStudyPlanExcelFile(planFile);
        return jsonObject.toJSONString();
    }

    /**
     * 手动同步各项计划的执行状态(忽略已经挂起的计划)
     * @return
     */
    @GetMapping("/syncTaskStatus")
    @ResponseBody
    public Result syncTaskStatus(){
        Result result = studyService.syncTaskStatus();
        if (Objects.nonNull(result)){
            return result;
        }
        return null;
    }

    /**
     * 挂起计划
     */
    @PostMapping("/hang/{id}")
    @ResponseBody
    public Result hangStudy(@PathVariable(name = "id") Long id){
        Integer result = studyService.hangStudy(id);
        return Result.ok(result);
    }

    /**
     * 重启计划
     */
    @PostMapping("/trunOn/{id}")
    @ResponseBody
    public Result trunOnStudy(@PathVariable(name = "id") Long id){
        Integer result = studyService.trunOnStudy(id);
        return Result.ok(result);
    }

    /**
     * 批量删除计划
     * @param idListStr
     * @return
     */
    @PostMapping("/batchDelete")
    @ResponseBody
    public Result batchDelete(@RequestParam(name = "idListStr") String idListStr){
        // 参数不能为空
        if (StringUtils.isEmpty(idListStr)){
            return Result.fail(ResultCode.PARAM_ERROR.code(), ResultCode.PARAM_ERROR.msg());
        }
        Integer resultData = studyService.batchDelete(idListStr);
        return Result.ok(resultData);
    }

}
