package com.bd.simultaneously;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class IndexThreadPool {

    /**
     * 加载个人控制台
     *
     *      任务拒绝策略：直接丢弃新任务并且抛出RejectedExecutionException
     *      任务队列: 指定有界队列并且限定队列容量为100
     */
    public static final ThreadPoolExecutor indexPool =
            new ThreadPoolExecutor(6, 24, 3, TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(100), new ThreadPoolExecutor.AbortPolicy());
}
