package com.bd.service.common;

public interface StudyRedisKeyService {


    /**
     * 同步study模块的首页相关key(删除所有涉及到的key，第一次查询时自动设置)
     * @return
     */
    Boolean syncIndexViewByStudyKey();


}
