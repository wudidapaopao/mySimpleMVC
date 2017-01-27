package com.yxz.mySimpleMVC.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yu
 * jdbc事务工具类
 */
public class TransactionUtil {

	private static final Logger logger = LoggerFactory.getLogger(TransactionUtil.class);
	
	/**
	 * 开始事务
	 */
	public static void startTransaction() {
		Connection connection = DbUtil.getConnection();
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			logger.error("failed to start transaction", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 提交事务
	 */
	public static void commitTransaction() {
		Connection connection = DbUtil.getConnection();
		try {
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			logger.error("failed to commit transaction", e);
			throw new RuntimeException(e);
		} finally {
			DbUtil.release();
		}
	}

	/**
	 * 回滚事务
	 */
	public static void rollbackTransaction() {
		Connection connection = DbUtil.getConnection();
		try {
			connection.rollback();
			connection.close();
		} catch (SQLException e) {
			logger.error("failed to commit rollback transaction", e);
			throw new RuntimeException(e);
		} finally {
			DbUtil.release();
		}
	}

}
