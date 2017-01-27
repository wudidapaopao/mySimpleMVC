package com.yxz.mySimpleMVC.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
* @author Yu 
* 请求url对应的方法注解
* restful风格，get是获取资源，post是创建新资源，put是更新资源，delelte是删除资源，patch是更新部分资源
*/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {
	
	public enum RequestMethod {POST, GET, PUT, DELETE, PATCH}
	
	public String url();
	
	public RequestMethod method() default RequestMethod.GET;
	
}