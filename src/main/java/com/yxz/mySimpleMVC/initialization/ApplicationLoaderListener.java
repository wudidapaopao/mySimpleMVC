package com.yxz.mySimpleMVC.initialization;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxz.mySimpleMVC.config.BeanGet;
import com.yxz.mySimpleMVC.exception.IocFactoyException;
import com.yxz.mySimpleMVC.urlMapping.DefaultUrlMapping;
import com.yxz.mySimpleMVC.urlMapping.UrlMapping;
import com.yxz.mySimpleMVC.util.ClassLoaderUtil;
import com.yxz.mySimpleMVC.util.ReflectionUtil;

/**
* @author Yu 
* web应用启动的监听类，进行相应参数的初始化
*/
public class ApplicationLoaderListener implements ServletContextListener {

//	private static final Logger logger = LoggerFactory.getLogger(ApplicationLoaderListener.class);
//	
//	public static final String IOC_FACTORY_ClASS = "iocClass";
//	public static final String DEFAULT_IOC_FACTORY_ClASS = "com.yxz.mymvc.iocFactory.SimpleIocFactory";
//	public static final String CONFIGURATION_LOCATION = "iocLoaction";
//	public static final String DEFAULT_CONFIGURATION_LOCATION = "/web-inf/iocFactor.xml";
//	public static final String IOC_FACTORY_ATTRIBUTE_NAME = "iocFactoy";
	
	public void contextInitialized(ServletContextEvent event) {
//		//logger.info("initializing iocFactoy...");
//		ServletContext servletContext = event.getServletContext();
//		String className = servletContext.getInitParameter(IOC_FACTORY_ClASS);
//		String location = servletContext.getInitParameter(CONFIGURATION_LOCATION);
//		if(className == null) {
//			className = DEFAULT_IOC_FACTORY_ClASS;
//		}
//		if(location == null) {
//			location = DEFAULT_CONFIGURATION_LOCATION;
//		}
//		Class clazz = ClassLoaderUtil.getClass(className);
//		Object object = ReflectionUtil.getObject(clazz);
//		if(!(object instanceof IocFactory)) {
//			throw new IocFactoyException("failed to initialization iocFactoy, iocFactoy is not legal");
//		}
//		IocFactory iocFactoy = (IocFactory) object;
//		//iocFactoy.init(location);
//		servletContext.setAttribute(IOC_FACTORY_ATTRIBUTE_NAME, iocFactoy);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		//logger.info("destroying iocFactoy...");
	}
	
	/*
	 * 初始化bean容器和urlMapping
	 */
	private void init() {
		ClassLoaderUtil.getClass(BeanGet.class.getName(), true);//执行BeanGet的静态语句块
		UrlMapping urlMapping = new DefaultUrlMapping();
		urlMapping.init();
	}
	
}

