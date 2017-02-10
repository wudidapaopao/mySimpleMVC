package com.yxz.mySimpleMVC.initialization;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxz.mySimpleMVC.config.BeanGet;
import com.yxz.mySimpleMVC.urlMapping.DefaultUrlMapping;
import com.yxz.mySimpleMVC.urlMapping.UrlMapping;
import com.yxz.mySimpleMVC.util.ClassLoaderUtil;

/**
* @author Yu 
* web应用启动的监听类，进行相应参数的初始化
*/
public class ApplicationLoaderListener implements ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationLoaderListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.info("contextInitialized...");
		init();
		// to do
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("contextDestroyed...");
		// to do
	}
	
	/*
	 * 初始化bean容器和urlMapping
	 */
	private void init() {
		ClassLoaderUtil.getClass(BeanGet.class.getName(), true); //执行BeanGet的静态语句块,初始化ioc容器
	}
	
}
