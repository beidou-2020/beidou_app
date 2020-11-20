package com.bd.service;

import com.bd.entitys.model.THistoricalReading;
import com.bd.entitys.parame.AddReadParam;
import com.bd.entitys.parame.PageParam;
import com.bd.entitys.parame.UpdateReadParam;
import com.bd.entitys.query.ReadQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ReadService {

    /**
     * 分页获取阅读信息
     * @param pageParam
     * @param query
     * @return
     */
    PageInfo<THistoricalReading> pageByQuery(PageParam pageParam, ReadQuery query);

    /**
     * 添加阅读信息
     * @param param
     * @return
     */
    THistoricalReading addReadInfo(AddReadParam param);

    /**
     * 根据主键获取阅读信息
     * @param id
     * @return
     */
    THistoricalReading findById(Long id);

    /**
     * 更新阅读信息
     * @param param
     * @return
     */
    THistoricalReading updateReadInfo(UpdateReadParam param);

    /**
     * 逻辑删除阅读信息
     * @param id
     * @return
     */
    THistoricalReading deleteById(Long id);

    /**
     * 根据阅读状态获取今年的阅读历史数据(2：读完)
     */
    List<THistoricalReading> todayYearByReadFlag();

    /**
     * 获取累计阅读量
     */
    Integer countReadNumber();


}
