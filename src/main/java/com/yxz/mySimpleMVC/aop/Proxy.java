package com.yxz.mySimpleMVC.aop;

/**
 * @author Yu
 * 代理接口
 */
public interface Proxy {
	
	/*
	 * 链式代理 
	 */
	Object doProxy(ProxyChain proxyChain) throws Throwable;
	
}
