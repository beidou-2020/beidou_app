package com.bd.conf;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpClientConf {

    @Value("${spring.httpclient.timeout.connect}")
    private int connectTimeout;

    @Value("${spring.httpclient.timeout.read}")
    private int readTimeout;

    @Value("${spring.httpclient.connection.pool.maxTotal}")
    private int maxTotal;

    @Value("${spring.httpclient.connection.pool.defaultMaxPerRoute}")
    private int defaultMaxPerRoute;

    @Value("${spring.httpclient.retry.times}")
    private int retryTimes;

    @Bean
    @DependsOn  //作用：控制实例化bean的顺序
    RestTemplate getRestTemplate(ClientHttpRequestFactory factory){
        RestTemplate restTemplate= new RestTemplate();
        restTemplate.setRequestFactory(factory);
        return restTemplate;
    }

    @Bean
    @Primary
    ClientHttpRequestFactory getHttpRequestFactory(){
        HttpClientBuilder builder = getHttpClientBuilder();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        // 从连接池获取连接的timeout
        factory.setConnectionRequestTimeout(connectTimeout);
        /**
         *指客户端和服务器建立连接的timeout
         * (就是http请求的三个阶段，一：建立连接；二：数据传送；三，断开连接。超时后会ConnectionTimeOutException)
         */
        //factory.setConnectTimeout(1);     (一般不设置)
        // 指客户端和服务器建立连接后，客户端从服务器读取数据的timeout，超出后会抛出SocketTimeOutException
        factory.setReadTimeout(readTimeout);
        factory.setHttpClient(builder.build());
        return factory;
    }

    HttpClientBuilder getHttpClientBuilder(){
        HttpClientConnectionManager manager = getConnectionManager();
        // 设置http client的重试次数，默认是3次；当前是禁用掉
        DefaultHttpRequestRetryHandler requestRetryHandler = new DefaultHttpRequestRetryHandler(retryTimes, false);
        return HttpClientBuilder.create().setConnectionManager(manager).setRetryHandler(requestRetryHandler);
    }

    HttpClientConnectionManager getConnectionManager(){
        PoolingHttpClientConnectionManager poolManager = new PoolingHttpClientConnectionManager();
        poolManager.setMaxTotal(maxTotal);          // 连接池最大连接数(默认为0)
        // 路由的默认最大连接(该值默认为2)，请求并发的限制数量实际使用DefaultMaxPerRoute并非MaxTotal。
        poolManager.setDefaultMaxPerRoute(defaultMaxPerRoute);      // 设置每个主机地址的并发数
        return poolManager;
    }
}
