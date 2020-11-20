package com.bd.repository;

import com.alibaba.fastjson.JSONObject;
import com.bd.constant.FileConstant;
import com.bd.utils.FileUtils;
import com.bd.utils.RestClientHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.io.File;
import java.util.Objects;

@Component
@Slf4j
public class FileClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${file.fileStreamUpload}")
    private String fileStreamUploadUrl;

    @Value("${file.uploadFile}")
    private String uploadFileUrl;

    @Resource
    private FileConstant fileConstant;

    /**
     * 上传单文件
     * @param uploadFile
     * @return
     */
    public String uploadFile(MultipartFile uploadFile){
        try{
            MultiValueMap<String, Object> multipartRequest = new LinkedMultiValueMap<>();
            // 生成临时文件名称
            String tempFileName = FileUtils.getNewFileName(uploadFile.getOriginalFilename());
            // 通过restTemplate自带的消息转换器将MultipartFile转成File再进行向下传递
            File tempFile = FileUtils.convert(uploadFile, fileConstant.getTempDir() + tempFileName);
            multipartRequest.add("uploadFile", new FileSystemResource(tempFile));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity (multipartRequest,headers);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(uploadFileUrl, requestEntity, String.class);
            if (Objects.isNull(responseEntity)){
                return null;
            }

            String body = responseEntity.getBody();
            log.info("文件直传：上传成功后的文件名：{}", JSONObject.toJSONString(body));
            return body;
        }catch (Exception ex){
            log.error("调用file-server服务的uploadFile方法异常：{}", RestClientHelper.getUrl(uploadFileUrl, uploadFile), ex);
        }
        return null;
    }
}
