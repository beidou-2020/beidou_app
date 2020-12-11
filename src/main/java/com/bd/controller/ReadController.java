package com.bd.controller;

import com.alibaba.fastjson.JSONObject;
import com.bd.constant.FileConstant;
import com.bd.controller.common.Result;
import com.bd.entitys.enumerate.ResultCode;
import com.bd.entitys.model.THistoricalReading;
import com.bd.entitys.parame.AddReadParam;
import com.bd.entitys.parame.PageParam;
import com.bd.entitys.parame.UpdateReadParam;
import com.bd.entitys.query.ReadQuery;
import com.bd.service.ReadService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/read")
@Slf4j
public class ReadController {

    @Resource
    private ReadService readService;

    @Resource
    private FileConstant fileConstant;

    /**
     * 阅读信息列表：缓存阻挡
     * @param pageParam
     * @param query
     * @return
     */
    @GetMapping(value = "/list", produces = "application/json;charset=utf-8")
    public ModelAndView list(@Valid PageParam pageParam, ReadQuery query) {
        PageInfo<THistoricalReading> pageInfo = readService.pageByQuery(pageParam, query);
        return new ModelAndView("/read/list").
                addObject("list", pageInfo.getList()).
                addObject("total", pageInfo.getTotal()).
                addObject("totalPage", pageInfo.getPages()).
                addObject("currPageNumber", pageParam.getCurrentPageNumber()).
                addObject("query", query);
    }

    /**
     * 跳转至添加页面
     * @return
     */
    @GetMapping("/toAddView")
    public ModelAndView toAddView() {
        return new ModelAndView("/read/add");
    }

    /**
     * 添加阅读信息
     * @param param
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@Valid AddReadParam param) {
        THistoricalReading addReadInfo = readService.addReadInfo(param);
        return JSONObject.toJSONString(addReadInfo);
    }

    /**
     * 查看阅读详情：缓存阻挡
     * @param id
     * @return
     */
    @GetMapping(value = "/toDetailsView/{id}", produces = "application/json;charset=utf-8")
    public ModelAndView toDetailsView(@PathVariable(name = "id") Long id) {
        THistoricalReading readInfo = readService.findById(id);
        return new ModelAndView("/read/details").
                addObject("readInfo", readInfo).
                addObject("serverPath", fileConstant.getAccessPath());
    }

    /**
     * 跳转至编辑页面
     * @param id
     * @return
     */
    @RequestMapping(value = "/toEditView/{id}", method = RequestMethod.GET)
    public ModelAndView toEditView(@PathVariable(name = "id") Long id) {
        THistoricalReading readInfo = readService.findById(id);
        return new ModelAndView("/read/edit").
                addObject("readInfo", readInfo).
                addObject("serverPath", fileConstant.getAccessPath());
    }

    /**
     * 更新阅读信息
     * @param param
     * @return
     */
    @PostMapping("/update")
    public String update(@Valid UpdateReadParam param) {
        THistoricalReading readInfo = readService.updateReadInfo(param);
        return JSONObject.toJSONString(readInfo);
    }

    /**
     * 删除阅读信息
     */
    @PostMapping(value = "/deleteById")
    @ResponseBody
    public String deleteById(@RequestParam(name = "id", required = true) Long id) {
        THistoricalReading delete = readService.deleteById(id);
        return JSONObject.toJSONString(delete);
    }

    /**
     * 批量删除
     * @param idListStr
     * @return
     */
    @PostMapping("/batchDelete")
    @ResponseBody
    public Result batchDelete(@RequestParam("idListStr") String idListStr){
        // 参数不能为空
        if (StringUtils.isEmpty(idListStr)){
            return Result.fail(ResultCode.PARAM_ERROR.code(), ResultCode.PARAM_ERROR.msg());
        }

        Integer data = readService.batchDelete(idListStr);
        if (Objects.isNull(data)) {
            return Result.fail(ResultCode.RESPONSE_NULL.code(), ResultCode.RESPONSE_NULL.msg());
        }
        return Result.ok(data);
    }

    /**
     * 暂停阅读信息
     * @param id
     * @return
     */
    @PostMapping("/timeOut/{id}")
    @ResponseBody
    public Result timeOutReadInfo(@PathVariable(name = "id") Long id){
        Integer result = readService.timeOutReadInfo(id);
        if (Objects.isNull(result)){
            return Result.fail(ResultCode.ERROR.code(), ResultCode.ERROR.msg());
        }
        return Result.ok(result);
    }

    /**
     * 重新开始阅读
     * @param id
     * @return
     */
    @PostMapping("/restart/{id}")
    @ResponseBody
    public Result restartReadInfo(@PathVariable(name = "id") Long id){
        Integer result = readService.restartReadInfo(id);
        if (Objects.isNull(result)){
            return Result.fail(ResultCode.ERROR.code(), ResultCode.ERROR.msg());
        }
        return Result.ok(result);
    }


}
