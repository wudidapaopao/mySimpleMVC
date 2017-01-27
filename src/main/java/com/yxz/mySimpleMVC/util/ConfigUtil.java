package com.yxz.mySimpleMVC.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxz.mySimpleMVC.config.ConfigConst;

/**
 * @author Yu
 * 配置读取工具类
 */
public class ConfigUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigUtil.class);
	
	private static Properties prop;
	
	static  {    
        prop =  new  Properties();    
        InputStream in = ConfigUtil.class.getResourceAsStream(ConfigConst.CONFIG_FILENAME);    
         try  {    
            prop.load(in);        
        }  catch  (IOException e) {    
            logger.error("failed to load config file");
        }    
    } 
	
	public static String getProperties(String key, String defaultValue) {
		return prop.getProperty(key, defaultValue);
	}
	
}
