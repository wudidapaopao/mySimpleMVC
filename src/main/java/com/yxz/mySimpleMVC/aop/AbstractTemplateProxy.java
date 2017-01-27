package com.yxz.mySimpleMVC.aop;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yu
 * 抽象模板代理类
 */
public class AbstractTemplateProxy implements Proxy{

	private static final Logger logger = LoggerFactory.getLogger(AbstractTemplateProxy.class);
	
	@Override
	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		Class<?> targetClass = proxyChain.getTargetClass();
		Method targetMethod = proxyChain.getTargetMethod();
		Object result = null;
		try {
			if(needAdvice(targetClass, targetMethod)) {
				before();
				result = proxyChain.doProxyChain();
				after();
			}
			else {
				result = proxyChain.doProxyChain();
			}
		} catch(Throwable e) {
			logger.error("failed during doProxy", e);
			error();
			throw e;
		}
		return result;
	}

	public void after() {
		
	}

	public void before() {
		
	}

	public void error() {
		
	}

	public boolean needAdvice(Class<?> targetClass, Method targetMethod) {
		return true;
	}

}
