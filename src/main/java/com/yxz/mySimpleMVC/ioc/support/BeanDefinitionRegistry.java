package com.yxz.mySimpleMVC.ioc.support;

public interface BeanDefinitionRegistry {

	BeanDefinition getBeanDefinition(String name);
	
	void removeBeanDefinition(String name);
	
	void registerBeanDefinition(String name, BeanDefinition beanDefiniton);
	
	boolean containsBeanDefintion(String name);
	
}
