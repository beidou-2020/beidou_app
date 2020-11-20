package com.bd.constant;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
//加载指定的自定义配置文件
@PropertySource(value = "classpath:application.properties")
@ConfigurationProperties(prefix = "filepath")
@Data
@ToString
public class FileConstant {

    /**
     * 调用文件服务时将上传的文件存储的临时路径(因为通过RestTemplate直接传递MultipartFile是不可行的)
     */
    private String tempDir;

    /**
     * 文件上传到文件服务器的存储路径
     */
    private String uploadServerPath;

    /**
     * 文件服务器存储资源的绝对路径对应的虚拟路径(用于UploadFileConfig配置文件)
     */
    private String storageVirtualPath;

    /**
     * 文件服务器存储资源的绝对路径对应的虚拟路径(用于前端访问)
     */
    private String accessPath;
}
