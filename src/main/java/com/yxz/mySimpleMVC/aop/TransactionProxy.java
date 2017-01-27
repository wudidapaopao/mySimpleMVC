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
	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		Method targetMethod = proxyChain.getTargetMethod();
		Class<?> targetClass = proxyChain.getTargetClass();
		Object result = null;
		try {
			if(targetMethod.isAnnotationPresent(Transaction.class)) {
				TransactionUtil.startTransaction();
				logger.debug("transaction start");
				result = proxyChain.doProxyChain();
				TransactionUtil.commitTransaction();
				logger.debug("transaction end");
			}
			else {
				result = proxyChain.doProxyChain();
			}
		} catch(Exception e) {
			TransactionUtil.rollbackTransaction();
			logger.debug("transaction rollback");
			throw e;
		}
		return result;
	}
	
}
