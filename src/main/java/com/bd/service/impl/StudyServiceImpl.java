package com.bd.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bd.constant.RedisConstant;
import com.bd.controller.common.Result;
import com.bd.entitys.enumerate.ResultCode;
import com.bd.entitys.model.TZxzStudy;
import com.bd.entitys.parame.AddStudyParam;
import com.bd.entitys.parame.ImportStudyParam;
import com.bd.entitys.parame.PageParam;
import com.bd.entitys.parame.UpdateStudyParam;
import com.bd.entitys.query.StudyQuery;
import com.bd.repository.FileClient;
import com.bd.repository.StudyClient;
import com.bd.service.StudyService;
import com.bd.service.common.StudyRedisKeyService;
import com.bd.utils.FileUtils;
import com.bd.utils.JsonUtils;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class StudyServiceImpl implements StudyService {

    @Resource
    private StudyClient studyClient;

    @Resource
    private FileClient fileClient;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private StudyRedisKeyService studyRedisKeyService;

    @Override
    @Cacheable(value = "study_list_retData", key = "{#queryStudy, #pageQuery}")
    public PageInfo<TZxzStudy> pageFindByQuery(StudyQuery queryStudy, PageParam pageQuery) {
        return studyClient.list(queryStudy, pageQuery);
    }

    @Override
    public PageInfo<TZxzStudy> pageFindByRemove(StudyQuery queryStudy, PageParam pageQuery) {
        // valid_mark：0删除
        queryStudy.setValidMark(0);
        return studyClient.list(queryStudy, pageQuery);
    }

    @Override
    public TZxzStudy addStudyInfo(AddStudyParam param) {
        // 同步相关的缓存数据
        Boolean syncResult = studyRedisKeyService.syncIndexViewByStudyKey();
        if (syncResult){
            log.info("添加计划时：成功同步首页相关key");
        }
        return studyClient.add(param);
    }

    @Override
    public TZxzStudy deleteStudyPlan(Long id) {
        // 同步相关的缓存数据
        Boolean syncResult = studyRedisKeyService.syncIndexViewByStudyKey();
        if (syncResult){
            log.info("删除计划时：成功同步首页相关key");
        }
        return studyClient.delete(id);
    }

    @Override
    @Cacheable(value = "study_info_retData", key = "{#id}")
    public TZxzStudy findById(Long id) {
        return studyClient.details(id);
    }

    @Override
    public TZxzStudy updateStudyById(UpdateStudyParam param) {
        // 同步相关的缓存数据
        Boolean syncResult = studyRedisKeyService.syncIndexViewByStudyKey();
        if (syncResult){
            log.info("更新计划时：成功同步首页相关key");
        }
        return studyClient.update(param);
    }

    @Override
    public JSONObject importStudyPlanExcelFile(MultipartFile planFile) {
        JSONObject resultJsonObject = new JSONObject();
        // 文件上传到文件服务器，返回存储后的新文件名称
        String fileAbsolutePath = "";
        try{
            //文件直传存储
            fileAbsolutePath = fileClient.uploadFile(planFile);
        }catch (Exception ex){
            log.error("文件存储异常：{}", ExceptionUtils.getFullStackTrace(ex));
        }

        // 解析文件
        List<TZxzStudy> studyList = this.ParsingExcelFileData(planFile);
        if (CollectionUtils.isEmpty(studyList)){
            resultJsonObject.put("code", 0);
            resultJsonObject.put("msg", "文件解析异常或上传文件中不包含所需数据");
            return resultJsonObject;
        }
        ImportStudyParam param = new ImportStudyParam();
        param.setList(studyList);
        Integer batchInsert = studyClient.batchInsert(param);

        // 同步相关的缓存数据
        Boolean syncResult = studyRedisKeyService.syncIndexViewByStudyKey();
        if (syncResult){
            log.info("导入计划时：成功同步首页相关key");
        }

        resultJsonObject.put("code", 1);
        resultJsonObject.put("msg", "文件数据导入成功");
        resultJsonObject.put("data", batchInsert.intValue());
        resultJsonObject.put("path", fileAbsolutePath);
        return resultJsonObject;
    }

    /**
     * 解析特定Excel文件数据
     * @return
     */
    private List<TZxzStudy> ParsingExcelFileData(MultipartFile planFile){
        String sourceFileName = planFile.getOriginalFilename();		//获取源文件名
        // 解析Excel文件数据保存到DB
        List<TZxzStudy> planList = new ArrayList<>();
        try {
            // 1：创建HSSFWorkbook对象，读取整个文档内容
            InputStream inputStream = planFile.getInputStream();
            Workbook workbook = null;
            if (FileUtils.isExcel2003(sourceFileName)) {
                // 如果是.xls文件
                workbook = new HSSFWorkbook(inputStream);
            }else if (FileUtils.isExcel2007(sourceFileName)) {
                // 如果是.xlsx文件
                workbook = new XSSFWorkbook(inputStream);
            }

            // 2：根据名称或索引，读取数据页sheet内容并且获得数据总行数
            Sheet sheet = workbook.getSheet("特种兵训练大纲");
            if (Objects.isNull(sheet)) {
                log.error("上传文件中不包含所需数据，终止解析");
                return null;
            }
            int totalRow = sheet.getPhysicalNumberOfRows();

            // 3：获取行数据(默认从第三行读取，标题不入库)
            for (int i = 2; i < totalRow; i++) {
                Row row = sheet.getRow(i);
                String titleData = row.getCell(0).getStringCellValue();
                if (Objects.isNull(row) || StringUtils.isEmpty(titleData)) {
                    // 如果主题为空，放弃本行数据
                    continue;
                }

                // 4：行数据不为空的前提下，获取当前行的列数据
                TZxzStudy dataInfo = new TZxzStudy();
                dataInfo.setTitle(titleData); 										//主题
                /*dataInfo.setPlanBegintime(row.getCell(1).getDateCellValue()); 		//计划开始时间
                dataInfo.setPlanEndtime(row.getCell(2).getDateCellValue()); 		    //计划截止时间*/
                dataInfo.setPlanBegintime(row.getCell(1).getStringCellValue());
                dataInfo.setPlanEndtime(row.getCell(2).getStringCellValue());
                dataInfo.setItems(row.getCell(3).getStringCellValue());	 			//学习项目
                dataInfo.setRemark(row.getCell(4).getStringCellValue()); 			//备注

                planList.add(dataInfo);
            }
        } catch (Exception e) {
            log.error("解析Excel文件数据异常：{}", ExceptionUtils.getFullStackTrace(e));
        }
        return planList;
    }

    @Override
    public Result syncTaskStatus() {
        // 同步相关的缓存数据
        Boolean syncResult = studyRedisKeyService.syncIndexViewByStudyKey();
        if (syncResult){
            log.info("手动同步计划状态时：成功同步首页相关key");
        }
        return studyClient.syncTaskStatus();
    }

    @Override
    public List<TZxzStudy> endStudyByCurrentMonth() {
        String keyName = RedisConstant.indexEndStudyList;
        try{
            // 命中缓存操作
            String endStudyListByRedisString = stringRedisTemplate.opsForValue().get(keyName);
            if (StringUtils.isNotEmpty(endStudyListByRedisString)){
            List<TZxzStudy> endStudyList =
                JsonUtils.toBeanList(endStudyListByRedisString, TZxzStudy.class);
                log.info("首页信息——本月计划结束的数据命中缓存：{}", JSONObject.toJSONString(endStudyList));
                return endStudyList;
            }
        }catch (Exception ex){
            log.error("首页信息——本月计划结束的数据命中缓存异常", ex);
        }

        Result result = studyClient.endStudyByCurrentMonth();
        if (Objects.isNull(result)){
            return null;
        }

        int code = result.getCode();
        if (ResultCode.RESPONSE_FUSE.code() == code){
            log.error("调用study-server服务的endStudyByCurrentMonth方法异常，开启服务熔断机制");
            return new ArrayList<>();
        }
        if (Objects.isNull(code) || code != 0){
            return null;
        }
        List data = (List<TZxzStudy>)result.getData();

        try{
            // 设置缓存(查询DB后), 过期时间为1小时
            String value = JsonUtils.toJSONString(data);
            Boolean writeRedisResult = redisTemplate.opsForValue().setIfAbsent(keyName, value, 60, TimeUnit.MINUTES);
            if (writeRedisResult){
                log.info("key：{}===value：{}写入缓存成功", keyName, value);
            }
        }catch (Exception ex){
            log.error("设置缓存失败——首页信息(本月计划结束的数据: {})", JSONObject.toJSONString(data), ex);
        }
        return data;
    }

    @Override
    public Integer countStudyNumber() {
        String keyName = RedisConstant.indexStudyNum;
        try{
            // 命中缓存操作
            String studyNumByRedisString = stringRedisTemplate.opsForValue().get(keyName);
            if (StringUtils.isNotEmpty(studyNumByRedisString)){
                int studyNum = Integer.parseInt(studyNumByRedisString);
                log.info("首页信息——累计学习计划总数命中缓存：{}", JSONObject.toJSONString(studyNum));
                return studyNum;
            }
        }catch (Exception ex){
            log.error("首页信息——累计学习计划总数命中缓存异常", ex);
        }

        Result result = studyClient.countStudyNumber();
        if (Objects.isNull(result)){
            return null;
        }

        int code = result.getCode();
        if (ResultCode.RESPONSE_FUSE.code() == code){
            log.error("调用study-server服务的countStudyNumber方法异常，开启服务熔断机制");
            return 0;
        }
        if (Objects.isNull(code) || code != 0){
            return null;
        }
        Integer data = (Integer)result.getData();

        try{
            // 设置缓存(查询DB后), 过期时间为1小时
            String value = JsonUtils.toJSONString(data);
            Boolean writeRedisResult = redisTemplate.opsForValue().setIfAbsent(keyName, value, 60, TimeUnit.MINUTES);
            if (writeRedisResult){
                log.info("key：{}===value：{}写入缓存成功", keyName, value);
            }
        }catch (Exception ex){
            log.error("设置缓存失败——首页信息(累计学习计划总数: {})", JSONObject.toJSONString(data), ex);
        }
        return data;
    }

    @Override
    public Integer hangStudy(Long id) {
        // 同步相关的缓存数据
        Boolean syncResult = studyRedisKeyService.syncIndexViewByStudyKey();
        if (syncResult){
            log.info("挂起计划时：成功同步首页相关key");
        }
        return studyClient.hangStudy(id);
    }

    @Override
    public Integer trunOnStudy(Long id) {
        // 同步相关的缓存数据
        Boolean syncResult = studyRedisKeyService.syncIndexViewByStudyKey();
        if (syncResult){
            log.info("重启计划时：成功同步首页相关key");
        }
        return studyClient.trunOnStudy(id);
    }

    @Override
    public Integer batchDelete(String idListStr) {
        // 同步相关的缓存数据
        Boolean syncResult = studyRedisKeyService.syncIndexViewByStudyKey();
        if (syncResult){
            log.info("批量删除计划时：成功同步首页相关key");
        }
        return studyClient.batchDelete(idListStr);
    }
}
