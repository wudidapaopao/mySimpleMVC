package com.yxz.mySimpleMVC.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yu
 * 反射工具类
 */
public class ReflectionUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class); 

	/*
	 * 获得对应类的实例对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getObject(Class<?> clazz) {
		T object = null;
		try {
			object = (T) clazz.newInstance();
		} catch (Exception e) {
			logger.error("failed to get the instance", e);
			throw new RuntimeException(e);
		} 
		return object;
	}
	
	/*
	 * 反射调用对象的方法
	 */
	public static Object invokeMethod(Object object, Method method, Object... args) {
		Object ret = null;
		try {
			method.setAccessible(true);
			ret = method.invoke(object, args);
		} catch (Exception e) {
			logger.error("failed to invoke the method", e);
			throw new RuntimeException(e);
		}
		return ret;
	}
	
	/*
	 * 反射设置对象的属性
	 */
	public static void setField(Object object, Field field, Object arg) {
		field.setAccessible(true);
		try {
			field.set(object, arg);
		} catch (Exception e) {
			logger.error("failed to set the field", e);
			throw new RuntimeException(e);
		} 
	}
	
}
