package com.bd.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/ftp")
@RestController
@Slf4j
public class FTPClientController {

    /*@Resource
    private FTPClient ftpClient;

    @RequestMapping("/getFTPFileNameList")
    @ResponseBody
    public String getFTPFileNameList(String pathName){
        if (StringUtils.isEmpty(pathName)){
            pathName = "/";
        }

        try{
            if (!ftpClient.makeDirectory(pathName)){
                log.error("检索目录不存在：{}", pathName);
                pathName = "/";
            }

            FTPFile[] ftpFiles = ftpClient.listFiles(pathName);
            int length = ftpFiles.length;
            log.info("资源个数为：{}", JSONObject.toJSONString(length));
        }catch (Exception ex){

        }

        return JSONObject.toJSONString("");
    }*/

    @GetMapping("/ftp/login")
    public String login(String address, int port, String username, String password) {
        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(address, port);
            ftp.login(username, password);
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.setControlEncoding("UTF-8");
            //限制缓冲区大小
            ftp.setBufferSize(1024 * 1024 * 4);
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                ftp = null;
                return "fail";
            }

            ftp.enterLocalPassiveMode();//设置为被动模式登陆
            ftp.changeWorkingDirectory("/");
            String currPath = ftp.printWorkingDirectory();
            System.out.println("当前的目录为："+currPath);
            FTPFile[] ftpFiles = ftp.listFiles();
            int length = ftpFiles.length;
            log.info("资源个数为：{}", JSONObject.toJSONString(length));
        } catch (Exception e) {
            ftp = null;
            return "fail";
        }

        return "success";
    }



}
