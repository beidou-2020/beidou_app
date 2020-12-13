package com.bd.service.common;

import java.util.Map;

public interface ConcurrentIndexService {

    /**
     * 并发获取个人控制台的各项数据
     * @return
     */
    Map<String, Object> getIndexData();
}
