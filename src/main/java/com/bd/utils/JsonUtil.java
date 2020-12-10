package com.bd.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 对象和json字符串的转换工具类。
 * @author zxz
 *
 */

public class JsonUtil {

	private final static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	private static ObjectMapper objectMapper = new ObjectMapper();
	
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

	
	/**
	 * 将map集合转成json字符串
	 * @param map
	 * @return
	 */
	public static String mapToJson(@SuppressWarnings("rawtypes") Map map){
		return JSON.toJSONString(map);
	}
	
	/**
	 * 将对象转成json串
	 * @param object
	 * @return
	 */
	public static String toJSONString(Object object){
		//DisableCircularReferenceDetect来禁止循环引用检测
		return JSON.toJSONString(object,SerializerFeature.DisableCircularReferenceDetect);
	}
	
	/**
	 * 将对象转换成JSON
	 * 
	 * @param object
	 * @return 成功返回JSON格式数据，失败返回null。
	 */
	public static String object2Json(final Object object) {

		if (object == null)
			return null;

		ObjectMapper mapper = new ObjectMapper();

		try {
			return mapper.writeValueAsString(object);
		}
		catch (JsonProcessingException ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * json 转 List<T>
	 */
	public static <T> List<T> jsonToList(String jsonString, Class<T> clazz) {
		JSONArray jsonarray = JSONArray.fromObject(jsonString);
		@SuppressWarnings("unchecked")
		List<T> ts = (List<T>)JSONArray.toCollection(jsonarray, clazz);
		return ts;
	}


}
