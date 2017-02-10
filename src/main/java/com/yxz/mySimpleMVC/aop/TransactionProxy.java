package com.yxz.mySimpleMVC.aop;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxz.mySimpleMVC.annotation.Service;
import com.yxz.mySimpleMVC.annotation.Transaction;
import com.yxz.mySimpleMVC.util.TransactionUtil;

/**
 * @author Yu
 * 事务代理类
 */
public class TransactionProxy extends AbstractTemplateProxy {
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionProxy.class);
	
	@Override
	protected void after() {
		TransactionUtil.commitTransaction();
		logger.debug("transaction end");
	}

	@Override
	protected void before() {
		TransactionUtil.startTransaction();
		logger.debug("transaction start");
	}

	@Override
	protected void error() {
		TransactionUtil.rollbackTransaction();
		logger.debug("transaction rollback");
	}

	@Override
	protected boolean needAdvice(Class<?> targetClass, Method targetMethod) {
		return targetMethod.isAnnotationPresent(Transaction.class);
	}
	
}
