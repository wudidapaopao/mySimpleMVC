package com.yxz.mySimpleMVC.ioc;

/**
 * @author Yu
 * ioc容器接口
 */
public interface BeanFactory {

	Object getBean(String name) throws Exception;
	
	boolean containsBean(String name);
	
}
