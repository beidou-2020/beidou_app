package com.bd.constant;


import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 控制缓存key的开关
 */
@Component
@PropertySource(value = "classpath:switch.properties")
@ConfigurationProperties(prefix = "redis")
@Data
@ToString
public class RedisSwitchConstant {

    /**
     * 首页key：今年读完的阅读简报
     */
    private String indexTodayYearReadListKey;

    /**
     * 首页key：累计的阅读总数
     */
    private String indexReadNumKey;


}
