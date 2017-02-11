package com.yxz.mySimpleMVC.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yu
 * 数据库连接池工具类，默认使用dbcp连接池
 */
public class DbUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(DbUtil.class);
	
	private static DataSource dataSource;
	private static final String DB_CONFIG = "/dbConfig.properties";
	
	static {
		try {
			Properties properties = new Properties();
			properties.load(DbUtil.class.getResourceAsStream(DB_CONFIG));
			dataSource = BasicDataSourceFactory.createDataSource(properties);
		} catch(Exception e) {
			logger.error("failed to get db config", e);
			throw new RuntimeException(e);
		}
	}
	
	//使用TreadLocal封装connection，从而可以保证在一个事务中调用多个方法，但connection是用一个。
	private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>();
	
	/*
	 * 获取数据库连接Connection
	 */
	public static Connection getConnection() {
		if(connectionHolder.get() == null) {
			try {
				connectionHolder.set(DbUtil.getConnection2());
			} catch (SQLException e) {
				logger.error("failed to get connection", e);
				throw new RuntimeException(e);
			}
		}
		return connectionHolder.get();
	}
	
	private static Connection getConnection2() throws SQLException {
		return dataSource.getConnection();
	}
	
	public static void release(ResultSet rs,Statement stmt) {  
        if(rs != null){  
            try {  
                rs.close();  
            } catch (SQLException e) {  
                logger.error("failed to close ResultSet", e);
            }   
        }  
        if(stmt != null){  
            try {  
                stmt.close();  
            } catch (SQLException e) {  
            	logger.error("failed to close StateMent", e);
            } 
        }  
        release(); 
    }
	
	/*
	 * 释放数据库连接Connection
	 */
	public static void release() {
		Connection connection = connectionHolder.get();
		if(connection != null){  
			try {  
				connection.close();  
			} catch (SQLException e) {  
				logger.error("failed to close connection", e);
			}
		} 
		connectionHolder.remove();
	}  
	
}
