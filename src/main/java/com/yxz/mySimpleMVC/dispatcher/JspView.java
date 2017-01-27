package com.yxz.mySimpleMVC.dispatcher;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yu
 * jsp视图
 */
public class JspView implements View {
	
	private String path;
	private Map<String, Object> paramMap = new HashMap<>();
	
	public JspView(String path, Map<String, Object> paramMap) {
		this.path = path;
		this.paramMap = paramMap;
	}

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Map<String, Object> getParamMap() {
		return paramMap;
	}
	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}
	
}
