package com.bd.controller;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Controller
@RequestMapping("/start")
@Slf4j
public class StartController {

    /**
     * 远程执行Linux命令
     * @param hostname：目标服务器IP
     * @param username：目标服务器用户名
     * @param password：目标服务器登录密码
     * @param commandStr：目标服务器要执行的命令
     * @return
     */
    @GetMapping("/runCommand")
    @ResponseBody
    public String runCommand(String hostname, String username, String password, String commandStr) {
        Connection conn = new Connection(hostname);
        Session sess = null;
        try {
              conn.connect();
              boolean isAuthenticated = conn.authenticateWithPassword(username, password);
              if (isAuthenticated == false) {
                throw new IOException("Authentication failed.");
              }
              sess = conn.openSession();
              sess.execCommand(commandStr);
              InputStream stdout = sess.getStdout();
              BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
              StringBuilder sb = new StringBuilder();
              while (true) {
                String line = br.readLine();
                if (line == null) break;
                sb.append(line + "  ");
              }

              log.info(sb.toString());
              return JSONObject.toJSONString(sb.toString());
        } catch (IOException e) {
              e.printStackTrace(System.err);
              return JSONObject.toJSONString("远程调用指令异常");
        } finally {
              sess.close();
              conn.close();
        }
    }
}
