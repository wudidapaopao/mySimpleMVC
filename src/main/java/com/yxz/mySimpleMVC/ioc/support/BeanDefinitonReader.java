package com.yxz.mySimpleMVC.ioc.support;

import com.yxz.mySimpleMVC.ioc.io.Resource;
import com.yxz.mySimpleMVC.ioc.io.ResourceLoader;

/*
 * 读取bean定义的接口
 */
public interface BeanDefinitonReader {

	void loadBeanDefiniton(String path) throws Exception;
	
	void loadBeanDefiniton(Resource resource) throws Exception;
	
	BeanDefinitionRegistry getRegistry();
	
	ResourceLoader getResourceLoader();
	
}
