package com.bd.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Slf4j
public class FileUtils {
	
	/**
     * @描述：是否是2003的excel，返回true是2003
     * @param filePath
     * @return
     */
    public static boolean isExcel2003(String filePath)  {  
          return filePath.matches("^.+\\.(?i)(xls)$");  
    }  
         
    /**
     * @描述：是否是2007的excel，返回true是2007
     * @param filePath
     * @return
     */
    public static boolean isExcel2007(String filePath)  {  
          return filePath.matches("^.+\\.(?i)(xlsx)$");  
    }
      
    /**
     * 验证是否是EXCEL文件
     * @param filePath
     * @return
     */
    public static boolean validateExcel(String filePath){
          if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))){  
              return false;  
          }  
          return true;
    }

    /**
     * 根据当前文件名和当前时间戳生成一个新文件名
     * @param sourceFileName
     * @return
     */
    public static String getNewFileName(String sourceFileName){
        String name = sourceFileName.substring(0, sourceFileName.lastIndexOf("."));	    //获取不含后缀名的源文件名
        String suffixName = sourceFileName.substring(sourceFileName.lastIndexOf("."));	//获取后缀名
        StringBuffer sBuffer = new StringBuffer();
        Date date = new Date();
        sBuffer.append(name).append("_").append(date.getTime()).append(suffixName);
        return sBuffer.toString();
    }

    /**
     * MultipartFile转换File
     * @param multipartFile
     * @param tempDirPath：生成临时File时的文件绝对路径(例如：C:\zxz_log\java\bd_temp\study_plan_1604626835349.xlsx)
     * @return
     */
    public static File convert(MultipartFile multipartFile, String tempDirPath) {
        File convFile = new File(tempDirPath);
        if (!convFile.getParentFile().exists()) {
            convFile.getParentFile().mkdirs();
        }
        try {
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(multipartFile.getBytes());
            fos.close();
        } catch (IOException e) {
            log.error("MultipartFile转换File对象异常：{}", ExceptionUtils.getFullStackTrace(e));
        }
        return convFile;
    }
}
