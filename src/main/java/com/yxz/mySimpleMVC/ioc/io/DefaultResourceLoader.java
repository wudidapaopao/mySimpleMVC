package com.yxz.mySimpleMVC.ioc.io;

import java.io.File;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * 默认配置资源加载器
 */
public class DefaultResourceLoader implements ResourceLoader {

	private static final Logger logger = LoggerFactory.getLogger(DefaultResourceLoader.class);
	
	@Override
	public Resource getReource(String path) {
		Resource resource = null;
		try {
			if(path.startsWith("classpath:")) {
				URL url = this.getClass().getClassLoader().getResource(path);
				resource =  new ClassPathResource(url);
			} else {
				resource =  new FileSystemResource(new File(path));
			}	
		} catch(Exception e) {
			logger.error("failed to load resource", e);
			throw new RuntimeException(e);
		}
		return resource;
	}
	
}
