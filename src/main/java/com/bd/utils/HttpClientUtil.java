package com.bd.utils;

import lombok.extern.slf4j.Slf4j;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class HttpClientUtil {

    /**
     * get请求将基本URL和参数拼接
     * @param url
     * @param object
     * @return
     */
    public static String getForObject(String url, Object object) {
        StringBuffer stringBuffer = new StringBuffer(url);
        if (object instanceof Map) {
            Iterator iterator = ((Map) object).entrySet().iterator();
            if (iterator.hasNext()) {
                stringBuffer.append("?");
                Object element;
                while (iterator.hasNext()) {
                    element = iterator.next();
                    Map.Entry<String, Object> entry = (Map.Entry) element;
                    //过滤value为null，value为null时进行拼接字符串会变成 "null"字符串
                    if (entry.getValue() != null) {
                        stringBuffer.append(element).append("&");
                    }
                    url = stringBuffer.substring(0, stringBuffer.length() - 1);
                }
            }

            return url;
        } else {
            throw new RuntimeException("发送get请求拼接URL异常");
        }
    }
}
