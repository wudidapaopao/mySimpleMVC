package com.yxz.mySimpleMVC.exception;

/**
 * @author Yu
 * 初始化IOC工厂异常
 */
public class IocFactoyException extends RuntimeException {

	public IocFactoyException(String string) {
		super(string);
	}

	public IocFactoyException(String string, Exception e) {
		super(string, e);
	}

}
