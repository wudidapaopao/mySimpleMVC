package com.yxz.mySimpleMVC.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yxz.mySimpleMVC.dispatcher.JsonView;

/**
 * @author Yu
 * json工具类
 */
public class JsonUtil {

	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	/*
	 * java对象转化为json字符串
	 */
	public static String getJsonStr(JsonView jv) {
		Object object = jv.getJsonObject();
		String jsonStr = null;
		try {
			jsonStr = objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			logger.error("failed to write json", e);
			throw new RuntimeException(e);
		}
		return jsonStr;
	}
	
	/*
	 * json字符串转化为指定类的java对象
	 */
	public static <T> T readJson2Bean(String jsonStr, Class<T> clazz) {
		T object = null;
		try {
			object = objectMapper.readValue(jsonStr, clazz);
		} catch (Exception e) {
			logger.error("failed to read json", e);
			throw new RuntimeException(e);
		} 
		return object;
	}
	
}
