package com.yxz.mySimpleMVC.config;

import com.yxz.mySimpleMVC.util.ConfigUtil;

/**
 * @author Yu
 * 获取配置属性类
 */
public class ConfigGet {
	
	public static String getJspFilePath() {
		return ConfigUtil.getProperties(ConfigConst.JSP_FILE_PATH, "");
	}
	
	public static String getJDBCDriver() {
		return ConfigUtil.getProperties(ConfigConst.JDBC_DRIVER, "");
	}
	
	public static String getJDBCPassword() {
		return ConfigUtil.getProperties(ConfigConst.JDBC_PASSWORD, "");
	}
	
	public static String getJDBCUsername() {
		return ConfigUtil.getProperties(ConfigConst.JDBC_USERNAME, "");
	}
	
	public static String getJDBCUrl() {
		return ConfigUtil.getProperties(ConfigConst.JDBC_URL, "");
	}
	
	public static String getBasePackage() {
		return ConfigUtil.getProperties(ConfigConst.BASE_PACKGE, "");
	}
	
	public static String getStaticFilePath() {
		return ConfigUtil.getProperties(ConfigConst.STATIC_FILE_PATH, "");
	}
	
}
