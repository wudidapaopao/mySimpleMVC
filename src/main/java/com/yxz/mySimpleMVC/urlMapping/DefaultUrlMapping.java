package com.yxz.mySimpleMVC.urlMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.yxz.mySimpleMVC.annotation.Action;
import com.yxz.mySimpleMVC.annotation.Action.RequestMethod;
import com.yxz.mySimpleMVC.config.AnnotationGet;

/**
 * @author Yu
 * UrlMapping默认实现类
 */
public class DefaultUrlMapping implements UrlMapping {

	private Map<Request, Handler> urlMap = new HashMap<>();
	
	/*
	 * 初始化请求和处理器的映射
	 */
	public void init() {
		Set<Class<?>> controllerClasses = AnnotationGet.getControllerClass();
		if(controllerClasses != null) {
			for(Class<?> clazz : controllerClasses) {
				Method[] methods = clazz.getDeclaredMethods();
				if(methods != null) {
					for(Method method : methods) {
						if(method.isAnnotationPresent(Action.class)) {
							Action action = method.getAnnotation(Action.class);
							String url = action.url();
							RequestMethod rm = action.method();
							String methodStr = rm.toString();
							if(url != null && method != null) {
								Request request = new Request(url, methodStr);
								Handler handler = new Handler(method, clazz);
								this.urlMap.put(request, handler);
							}
						}
					}
				}
			}
		}
	}
	
	/*
	 * 获取请求对应的处理器Handler
	 */
	public Handler getHandler(HttpServletRequest req) {
		String url = req.getRequestURI();
		String method = req.getMethod();
		Request request = new Request(url, method);
		return this.urlMap.get(request);
	}
	
}
