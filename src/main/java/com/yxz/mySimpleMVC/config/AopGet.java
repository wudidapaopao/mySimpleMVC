package com.yxz.mySimpleMVC.config;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxz.mySimpleMVC.annotation.Advice;
import com.yxz.mySimpleMVC.annotation.Service;
import com.yxz.mySimpleMVC.aop.Proxy;
import com.yxz.mySimpleMVC.aop.TransactionProxy;
import com.yxz.mySimpleMVC.exception.BeanNotFoundException;
import com.yxz.mySimpleMVC.util.CglibProxyUtil;
import com.yxz.mySimpleMVC.util.JDKProxyUtil;
import com.yxz.mySimpleMVC.util.ReflectionUtil;
import com.yxz.mySimpleMVC.util.TransactionUtil;

/**
 * @author Yu
 * aop的初始化
 */
public class AopGet {

	public static final Logger logger = LoggerFactory.getLogger(AopGet.class);
	
	/*
	 * 获得动态代理的class-Object映射map
	 */
	public static Map<Class<?>, Object> getAopBeanMap() {
		Map<Class<?>, List<Proxy>> proxyMap = getProxyMap(); //获取需要被代理的Class类及其相关切面的map
		Map<Class<?>, Object> aopBeanMap = new HashMap<>();
		for(Map.Entry<Class<?>, List<Proxy>> entry : proxyMap.entrySet()) {
			Class<?> clazz = entry.getKey();
			List<Proxy> proxyList = entry.getValue();
			Object dynamicProxy = CglibProxyUtil.getDynamicProxy(clazz, proxyList); //使用cglib动态代理
//			Object targetObject;                                                    //使用jdk动态代理
//			try {
//				targetObject = BeanGet.getBean(clazz);
//			} catch (BeanNotFoundException e) {
//				logger.error("", e);
//				continue;
//			}
//			Object dynamicProxy = JDKProxyUtil.getDynamicProxy(clazz, proxyList, targetObject);
			aopBeanMap.put(clazz, dynamicProxy);
		}
		return aopBeanMap;
	}
	
	private static Map<Class<?>, List<Proxy>> getProxyMap() {
		Map<Class<?>, List<Proxy>> map = new HashMap<>();
		Set<Class<?>> adviceSet = AnnotationGet.getClassSetWithAnnotation(Advice.class);
		for(Class<?> clazz : adviceSet) {
			Advice advice = clazz.getAnnotation(Advice.class);
			Class<? extends Annotation> annotation = advice.value();
			if(annotation != null) {
				Set<Class<?>> annotationSet = AnnotationGet.getClassSetWithAnnotation(annotation);
				for(Class<?> clazz2 : annotationSet) {
					if(map.containsKey(clazz2)) {
						List<Proxy> list = map.get(clazz2);
						Proxy proxy = ReflectionUtil.getObject(clazz);
						list.add(proxy);
					}
					else {
						List<Proxy> list = new ArrayList<>();
						Proxy proxy = ReflectionUtil.getObject(clazz);
						list.add(proxy);
						map.put(clazz2, list);
					}
				}
			}
		}
		setTransactionMap(map);
		return map;
	}

	private static void setTransactionMap(Map<Class<?>, List<Proxy>> map) {
		Proxy proxy = ReflectionUtil.getObject(TransactionProxy.class);
		for(Class<?> clazz : AnnotationGet.getClassSetWithAnnotation(Service.class)) {
			if(map.containsKey(clazz)) {
				List<Proxy> list = map.get(clazz);
				list.add(proxy);
			}
			else {
				List<Proxy> list = new ArrayList<>();
				list.add(proxy);
				map.put(clazz, list);
			}
		}
	}
	
}
