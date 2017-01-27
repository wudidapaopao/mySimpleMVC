package com.yxz.mySimpleMVC.dispatcher;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yu
 * http请求参数包装类
 */
public class Param {
	
	private Map<String, String> paramMap = new HashMap<>();

	public Param() {
		
	}
	
	public void addParam(String key, String value) {
		this.paramMap.put(key, value);
	}
	
}
