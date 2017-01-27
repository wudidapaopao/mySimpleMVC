package com.yxz.mySimpleMVC.exception;

/**
 * @author Yu
 * 找不到bean异常
 */
public class BeanNotFoundException extends Exception {
	
	public BeanNotFoundException() {
		super();
	}
	
	public BeanNotFoundException(String arg) {
		super(arg);
	}

	public BeanNotFoundException(Exception e) {
		super(e);
	}
	
}
