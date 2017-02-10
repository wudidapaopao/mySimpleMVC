package com.yxz.mySimpleMVC.urlMapping;

import java.lang.reflect.Method;

/**
 * @author Yu
 * url对应的处理方法的包装类
 */
public class Handler {

	private Method method;
	private Class<?> handlerClass;
	
	public Handler(Method method, Class<?> handlerClass) {
		this.method = method;
		this.handlerClass = handlerClass;
	}

	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	public Class<?> getHandlerClass() {
		return handlerClass;
	}
	public void setHandlerClass(Class<?> handlerClass) {
		this.handlerClass = handlerClass;
	}
	
}
