package com.bd.conf;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;

//@Configuration
@Slf4j
public class FTPClientConf {

    private String url = "10.229.250.49";
    private int port = 21;
    private String username = "";
    private String password = "";

    //@Bean
    public FTPClient connectFtpServer(){
        FTPClient ftpClient = new FTPClient();
        ftpClient.setConnectTimeout(1000*30);//设置连接超时时间
        ftpClient.setControlEncoding("utf-8");//设置ftp字符集
        ftpClient.enterLocalPassiveMode();//设置被动模式，文件传输端口设置
        try {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);//设置文件传输模式为二进制，可以保证传输的内容不会被改变
            ftpClient.connect(url, port);
            ftpClient.login(username,password);
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)){
                log.error("ftp服务器连接成功");
                ftpClient.disconnect();
                return null;
            }
            log.info("ftp replyCode==========={}",replyCode);
        } catch (IOException e) {
            log.error("ftp 服务器连接异常------->>>{}",e.getCause());
            return null;
        }
        return ftpClient;
    }

}
