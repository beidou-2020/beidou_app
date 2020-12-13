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
    private Boolean indexTodayYearReadListKey;

    /**
     * 首页key：累计的阅读总数
     */
    private Boolean indexReadNumKey;

    /**
     * 首页key：本月计划结束的学习计划列表
     */
    private Boolean indexEndStudyListKey;

    /**
     * 首页key：累计计划总数
     */
    private Boolean indexStudyNumKey;


}
