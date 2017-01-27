package com.yxz.mySimpleMVC.urlMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Yu
 * url和handler映射接口
 */
public interface UrlMapping {
	
	public Handler getHandler(HttpServletRequest req);
	
	public void init();
	
}
