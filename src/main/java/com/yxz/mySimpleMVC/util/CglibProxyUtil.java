package com.yxz.mySimpleMVC.util;

import java.lang.reflect.Method;
import java.util.List;

import com.yxz.mySimpleMVC.aop.Proxy;
import com.yxz.mySimpleMVC.aop.ProxyChain;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @author Yu
 * cglib动态代理工具类
 */
public class CglibProxyUtil {
	
	/*
	 * 获取cglib动态代理对象，cglib通过动态生成被代理类的子类的字节码的方式实现动态代理
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getDynamicProxy(final Class<?> targetClass, final List<Proxy> proxyList) {
		return (T) Enhancer.create(targetClass, new MethodInterceptor() {
			@Override
			public Object intercept(Object targetObject, Method method, Object[] methodParams, MethodProxy methodProxy) throws Throwable {
				return new ProxyChain(proxyList, targetObject, targetClass, method, methodParams, methodProxy).doProxyChain();
			}
		});
	}
	
}
