package com.yxz.mySimpleMVC.config;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yxz.mySimpleMVC.annotation.Advice;
import com.yxz.mySimpleMVC.annotation.Service;
import com.yxz.mySimpleMVC.aop.Proxy;
import com.yxz.mySimpleMVC.aop.TransactionProxy;
import com.yxz.mySimpleMVC.util.CglibProxyUtil;
import com.yxz.mySimpleMVC.util.ReflectionUtil;
import com.yxz.mySimpleMVC.util.TransactionUtil;

/**
 * @author Yu
 * aop的初始化
 */
public class AopGet {

	/*
	 * 获得动态代理的class-Object映射map
	 */
	public static Map<Class<?>, Object> getAopBeanMap() {
		Map<Class<?>, List<Proxy>> proxyMap = getProxyMap();
		Map<Class<?>, Object> aopBeanMap = new HashMap<>();
		for(Map.Entry<Class<?>, List<Proxy>> entry : proxyMap.entrySet()) {
			Class<?> clazz = entry.getKey();
			List<Proxy> proxyList = entry.getValue();
			Object dynamicProxy = CglibProxyUtil.getDynamicProxy(clazz, proxyList);
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
