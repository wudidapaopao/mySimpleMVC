package com.yxz.mySimpleMVC.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

/**
 * @author Yu
 * 动态代理执行链
 */
public class ProxyChain {

	private final List<Proxy> proxyList;
	private final Object targetObject;
	private final Class<?> targetClass;
	private final Method targetMethod;
	private final Object[] methodParams;
	private final MethodProxy methodProxy;
	
	private int index = 0;

	public ProxyChain(List<Proxy> proxyList, Object targetObject, Class<?> targetClass, Method targetMethod,
			Object[] methodParams, MethodProxy methodProxy) {
		this.proxyList = proxyList;
		this.targetObject = targetObject;
		this.targetClass = targetClass;
		this.targetMethod = targetMethod;
		this.methodParams = methodParams;
		this.methodProxy = methodProxy;
	}

	public List<Proxy> getProxyList() {
		return proxyList;
	}
	public Object getTargetObject() {
		return targetObject;
	}
	public Class<?> getTargetClass() {
		return targetClass;
	}
	public Method getTargetMethod() {
		return targetMethod;
	}
	public Object[] getMethodParams() {
		return methodParams;
	}

	/*
	 * 链式代理
	 */
	public Object doProxyChain() throws Throwable {
		Object result = null;
		if(index < this.proxyList.size()) {
			result = this.proxyList.get(index++).doProxy(this);
		}
		else {
			if(methodProxy != null) { //cglib动态代理
				result = this.methodProxy.invokeSuper(targetObject, methodParams);
			}
			else { //jdk动态代理
				result = this.targetMethod.invoke(targetObject, methodParams);
			}
		}
		return result;
	}
	
}
