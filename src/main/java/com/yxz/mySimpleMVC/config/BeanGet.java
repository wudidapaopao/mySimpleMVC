package com.yxz.mySimpleMVC.config;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxz.mySimpleMVC.annotation.Injection;
import com.yxz.mySimpleMVC.exception.BeanNotFoundException;
import com.yxz.mySimpleMVC.util.ReflectionUtil;

/**
 * @author Yu
 * 加载所管理的bean
 */
public class BeanGet {

	private static final Logger logger = LoggerFactory.getLogger(BeanGet.class);
	
	private static final Map<Class<?>, Object> beanMap = new HashMap<Class<?>, Object>();
	
	//初始化bean容器
	static {
		Set<Class<?>> sets = AnnotationGet.getBeanClass();
		for(Class<?> clazz : sets) { //将Service和Controller注解的类注入bean容器中
			beanMap.put(clazz, ReflectionUtil.getObject(clazz));
		}
		beanMap.putAll(AopGet.getAopBeanMap()); //将动态代理对象注入bean容器
		BeanGet.autoInjected(); //自动注入属性
	}
	
	/*
	 * 根据Class类型获取指定bean
	 */
	public static Object getBean(Class<?> clazz) throws BeanNotFoundException {
		if(!beanMap.containsKey(clazz)) {
			throw new BeanNotFoundException();
		}
		return beanMap.get(clazz);
	}
	
	/*
	 * 自动注入容器管理的对象中Inject注解标记的属性
	 */
	public static void autoInjected() {
		for(Entry<Class<?>, Object> entrySet : beanMap.entrySet()) {
			Class<?> clazz = entrySet.getKey();
			Object object = entrySet.getValue();
			Field[] fields = clazz.getDeclaredFields();
			for(Field field : fields) {
				if(field.isAnnotationPresent(Injection.class)) {
					Class<?> fieldClass = field.getType();
					Object fieldObject = beanMap.get(fieldClass);
					ReflectionUtil.setField(object, field, fieldObject);
				}
			}
		}
	}
	
}
