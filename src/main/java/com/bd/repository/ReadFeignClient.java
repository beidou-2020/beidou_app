package com.bd.repository;

import com.bd.controller.common.Result;
import com.bd.entitys.dto.AddReadDTO;
import com.bd.entitys.dto.UpdateReadDTO;
import com.bd.entitys.model.THistoricalReading;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//@Component
// 用于通知Feign组件对该接口进行代理
// name的值应该要和注册中心中被调用服务注册成功后的设置的服务名保持一致
//@FeignClient(name = "read-service-001", path = "/read")
public interface ReadFeignClient {

    /**
     * 阅读信息列表
     */
    @GetMapping(value = "/list", produces = "application/json;charset=utf-8")
    PageInfo<THistoricalReading> list(@RequestParam(name = "currentPageNumber") Integer currentPageNumber,
                                      @RequestParam(name = "pageSize") Integer pageSize,
                                      @RequestParam(name = "bookName") String bookName,
                                      @RequestParam(name = "author") String author,
                                      @RequestParam(name = "readFlag") Integer readFlag,
                                      @RequestParam(name = "begintime") String begintime,
                                      @RequestParam(name = "endtime") String endtime,
                                      @RequestParam(name = "validMark") Integer validMark);

    /**
     * 添加阅读信息
     */
    @PostMapping(value = "/add", produces = "application/json;charset=utf-8")
    THistoricalReading add(@RequestBody @Valid AddReadDTO param);

    /**
     * 查看阅读详情
     */
    @GetMapping(value = "/readDetails/{id}", produces = "application/json;charset=utf-8")
    THistoricalReading readDetails(@PathVariable(name = "id") Long id);

    /**
     * 更新阅读信息
     */
    @PostMapping(value = "/update", produces = "application/json;charset=utf-8")
    THistoricalReading update(@RequestBody UpdateReadDTO param);

    /**
     * 删除阅读信息
     */
    @PostMapping(value = "/deleteById", produces = "application/json;charset=utf-8")
    THistoricalReading deleteById(@RequestParam(name = "id", required = true) Long id);

    /**
     * 根据阅读状态获取今年的阅读历史数据(2：读完)
     */
    @GetMapping("/todayYearByReadFlag")
    @ResponseBody
    Result todayYearByReadFlag();

    /**
     * 获取累计阅读量
     */
    @GetMapping("/countStudyNumber")
    @ResponseBody
    Result countReadNumber();

    /**
     * 批量删除阅读信息
     */
    @PostMapping("/batchDelete")
    @ResponseBody
    Result batchDelete(@RequestParam("idListStr") String idListStr);

    /**
     * 暂停阅读信息
     */
    @PostMapping("/timeOut/{id}")
    @ResponseBody
    Result timeOutReadInfo(@PathVariable(name = "id") Long id);

    /**
     * 重新开始阅读
     */
    @PostMapping("/restart/{id}")
    @ResponseBody
    Result restartReadInfo(@PathVariable(name = "id") Long id);


}
