package com.bd.constant;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
@ToString
public class FileConstant {

    /**
     * 调用文件服务时将上传的文件存储的临时路径(因为通过RestTemplate直接传递MultipartFile是不可行的)
     */
    @Value("${filepath.tempDir}")
    private String tempDir;

    /**
     * 文件上传到文件服务器的存储路径
     */
    @Value("${filepath.uploadServerPath}")
    private String uploadServerPath;

    /**
     * 文件服务器存储资源的绝对路径对应的虚拟路径(用于UploadFileConfig配置文件)
     */
    @Value("${filepath.storageVirtualPath}")
    private String storageVirtualPath;

    /**
     * 文件服务器存储资源的绝对路径对应的虚拟路径(用于前端访问)
     */
    @Value("${filepath.accessPath}")
    private String accessPath;
}
