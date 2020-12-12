package com.bd.constant;


/**
 * redis中数据模块的key管理
 */
public class RedisKeyConstant {

    /**
     * 数据模块的key前缀
     */
    public static final String READ_KEY_PREFIX = "read_info_";
    public static final String STUDY_KEY_PREIFX = "study_info_";
    public static final String USER_KEY_PREFIX = "user_info_";

    /**
     * 阅读计划模块相关的key管理
     */
    // 首页key：今年读完的阅读简报
    public static final String indexTodayYearReadList = READ_KEY_PREFIX + "todayYearReadList";
    // 首页key：累计的阅读总数
    public static final String indexReadNum = READ_KEY_PREFIX + "readNum";

    /**
     * 学习计划模块相关的key管理
     */
    // 首页key：本月计划结束的学习计划列表
    public static final String indexEndStudyList = STUDY_KEY_PREIFX + "endStudyList";
    // 首页key：累计计划总数
    public static final String indexStudyNum = STUDY_KEY_PREIFX + "studyNum";
}
