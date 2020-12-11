/**
 * 
 */
package com.bd.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author zouqinghua
 * @date 2016年4月9日 下午6:01:54
 *
 */
public class JsonUtils {
    
    private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

	private static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJSONString(Object object) {
        String str = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            str = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
        	e.printStackTrace();
        }
        return str;
    }
    
    public static String toJSONStringFilterNull(Object object) {
        String str = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
        	//配置不写value为null的key  
        	mapper.setSerializationInclusion(Include.NON_NULL);
            str = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
        	e.printStackTrace();
        }
        return str;
    }
    
    public static <T> T toBean(String json, Class<T> clazz) {
    	ObjectMapper mapper = new ObjectMapper();
    	T t = null;
		try {
			t = mapper.readValue(json, clazz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return t;
    }
    
    public static <T> List<T> toBeanList(Object objects,Class<T> clazz){
    	List<T> list = null;
    	if(objects!=null){
    		String jsons = JSONArray.toJSONString(objects);
    		list = toBeanList(jsons, clazz);
    	}
    	return list;
    }
    
    public static <T> List<T> toBeanList(String jsons,Class<T> clazz){
    	List<T> list = Lists.newArrayList();
    	if(StringUtils.isNotBlank(jsons)){
    		JSONArray jsonArray = JSONArray.parseArray(jsons);
    		for (Object object : jsonArray) {
    			//String json = JSON.toJSONString(object);
    			//T t = toBean(json, clazz);
    			JSONObject json = (JSONObject) object;
				T t = JSON.toJavaObject(json, clazz);
    			list.add(t);
			}
    	}
    	return list;
    }


    public static <T> List<T> json2List(String json, Class<T> clazz) {
        try {
            return JSON.parseArray(json, clazz);
        } catch (Exception e) {
            logger.error("解析对象错误:{}",e.getMessage());
        }
        return null;
    }
    
    public static JSON toJSON(Object object){
		JSON json = null;
		if(object!=null){
			json = (JSON) JSON.toJSON(object);
		}
		return json;
	}
    
    public static JSONArray toJSONArray(Object object){
    	JSONArray jsonArray = null;
    	if(object!=null){
    		jsonArray = (JSONArray) JSON.toJSON(object);
    	}
    	return jsonArray;
    }
    
    public static JSONObject toJSONObject(Object object){
    	JSONObject jsonObject = null;
    	if(object!=null){
    		jsonObject = (JSONObject) JSON.toJSON(object);
    	}
    	return jsonObject;
    }
    
    public static JSONObject parseToJSONObject(String str){
    	JSONObject jsonObject = null;
    	if(StringUtils.isNotBlank(str)){
    		jsonObject = JSONObject.parseObject(str);
    	}
    	return jsonObject;
    }
    
    public static JSONArray parseToJSONArray(String str){
    	JSONArray jsonArray = null;
    	if(StringUtils.isNotBlank(str)){
    		jsonArray = JSONArray.parseArray(str);
    	}
    	return jsonArray;
    }
    
    public static <T> T json2Object(String json, TypeReference<T> type) {
        try {
            return JSON.parseObject(json, type.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

	static {
		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		// Include.NON_EMPTY 属性为 空（“”） 或者为 NULL 都不序列化
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		// 配置不写value为null的key
		objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
	}

	/**
	 * 将JSON格式数据转换成对象
	 *
	 * @param json
	 * @param clazz
	 * @return 成功返回对象，失败返回null。
	 */
	public static <T> T json2Object(final String json, final Class<T> clazz) {

		if (json == null)
			return null;

		try {
			return objectMapper.readValue(json, clazz);
		}
		catch (JsonParseException ex) {
			logger.error("将JSON转换成对象失败：", ex);
			logger.error("JSON: " + json);
			logger.error("Object: " + clazz);
		}
		catch (JsonMappingException ex) {
			logger.error("将JSON转换成对象失败：", ex);
			logger.error("JSON: " + json);
			logger.error("Object: " + clazz);
			ex.printStackTrace();
		}
		catch (IOException ex) {
			logger.error("将JSON转换成对象失败：", ex);
			logger.error("JSON: " + json);
			logger.error("Object: " + clazz);
			ex.printStackTrace();
		}

		return null;
	}
}
