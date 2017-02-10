package com.yxz.mySimpleMVC.config;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import com.yxz.mySimpleMVC.annotation.Controller;
import com.yxz.mySimpleMVC.annotation.Service;
import com.yxz.mySimpleMVC.util.ClassLoaderUtil;

/**
 * @author Yu
 * 获取注解标记的类集合
 */
public class AnnotationGet {
	
	private static Set<Class<?>> sets;
	
	static { //加载basePackage下面所有的类，并存储在sets中
		String basePackage = ConfigGet.getBasePackage();
		sets = ClassLoaderUtil.doLoadClass(basePackage);
	}
	
	/*
	 * 获取Service注解的所有类
	 */
	public static Set<Class<?>> getServiceClass() {
		Set<Class<?>> ret = new HashSet<Class<?>>();
		for(Class<?> clazz : sets) {
			if(clazz.isAnnotationPresent(Service.class)) {
				ret.add(clazz);
			}
		}
		return ret;
	}
	
	/*
	 * 获取Controller注解的所有类
	 */
	public static Set<Class<?>> getControllerClass() {
		Set<Class<?>> ret = new HashSet<Class<?>>();
		for(Class<?> clazz : sets) {
			if(clazz.isAnnotationPresent(Controller.class)) {
				ret.add(clazz);
			}
		}
		return ret;
	}
	
	/*
	 * 获取Service和Controller注解的所有类
	 */
	public static Set<Class<?>> getBeanClass() {
		Set<Class<?>> ret = new HashSet<Class<?>>();
		for(Class<?> clazz : sets) {
			if(clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(Service.class)) {
				ret.add(clazz);
			}
		}
		return ret;
	}
	
	/*
	 * 获取指定注解的所有类
	 */
	public static Set<Class<?>> getClassSetWithAnnotation(Class<? extends Annotation> clazz) {
		Set<Class<?>> ret = new HashSet<Class<?>>();
		for(Class<?> cla : sets) {
			if(cla.isAnnotationPresent(clazz)) {
				ret.add(clazz);
			}
		}
		return ret;
	}
	
}
