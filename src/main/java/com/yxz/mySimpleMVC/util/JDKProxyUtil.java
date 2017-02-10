package com.yxz.mySimpleMVC.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import com.yxz.mySimpleMVC.aop.ProxyChain;

/**
 * @author Yu
 * JDK动态代理工具类
 */
public class JDKProxyUtil {
	
	@SuppressWarnings("unchecked")
	public static <T> T getDynamicProxy(final Class<?> targetClass, final List<com.yxz.mySimpleMVC.aop.Proxy> proxyList, final Object targetObject) {
		return (T) Proxy.newProxyInstance(targetClass.getClassLoader(), targetClass.getInterfaces(), new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				return new ProxyChain(proxyList, targetObject, targetClass, method, args, null).doProxyChain();
			}
		});
	}
	
}
