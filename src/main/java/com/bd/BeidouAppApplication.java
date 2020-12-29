package com.bd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
//加载自定义配置文件(可以加载多个)
// @PropertySources({@PropertySource(value = "classpath:rest_dev.properties")})
// 本地生产环境
@PropertySources({@PropertySource(value = "classpath:rest_prod.properties")})
@EnableDiscoveryClient
@EnableFeignClients         // Fegin客户端
@EnableHystrix              //开启Hystrix断路器
@EnableHystrixDashboard     //开启熔断监控面板
public class BeidouAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeidouAppApplication.class, args);
    }

}
