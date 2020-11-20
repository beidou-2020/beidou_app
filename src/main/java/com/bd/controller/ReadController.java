package com.bd.controller;

import com.alibaba.fastjson.JSONObject;
import com.bd.constant.FileConstant;
import com.bd.entitys.model.THistoricalReading;
import com.bd.entitys.parame.AddReadParam;
import com.bd.entitys.parame.PageParam;
import com.bd.entitys.parame.UpdateReadParam;
import com.bd.entitys.query.ReadQuery;
import com.bd.service.ReadService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping("/read")
public class ReadController {

    @Resource
    private ReadService readService;

    @Resource
    private FileConstant fileConstant;

    /**
     * 阅读信息列表
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
     * 查看阅读详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/toDetailsView/{id}", method = RequestMethod.GET)
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
    public String deleteById(@RequestParam(name = "id", required = true) Long id) {
        THistoricalReading delete = readService.deleteById(id);
        return JSONObject.toJSONString(delete);
    }


}
