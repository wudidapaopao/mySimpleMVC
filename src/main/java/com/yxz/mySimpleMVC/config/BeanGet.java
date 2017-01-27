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
	
	static {
		Set<Class<?>> sets = AnnotationGet.getBeanClass();
		for(Class<?> clazz : sets) {
			beanMap.put(clazz, ReflectionUtil.getObject(clazz));
		}
		beanMap.putAll(AopGet.getAopBeanMap());
		BeanGet.autoInjected();
	}

	public static Object getBean(Class<?> clazz) throws BeanNotFoundException {
		if(!beanMap.containsKey(clazz)) {
			throw new BeanNotFoundException();
		}
		return beanMap.get(clazz);
	}
	
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
