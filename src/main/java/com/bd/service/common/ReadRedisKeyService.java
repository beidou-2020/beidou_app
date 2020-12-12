package com.bd.service.common;

public interface ReadRedisKeyService {

    /**
     * 同步read模块的首页相关key(删除所有涉及到的key，第一次查询时自动设置)
     * @return
     */
    Boolean syncIndexViewByReadKey();


}
