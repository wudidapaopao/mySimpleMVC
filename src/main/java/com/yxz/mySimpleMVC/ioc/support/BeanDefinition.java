package com.yxz.mySimpleMVC.ioc.support;

import com.yxz.mySimpleMVC.ioc.io.Resource;

/*
 * 包装bean及其元数据
 */
public class BeanDefinition {
	
	private String name;
	private Object bean;
	private String beanClassName;
	private PropertyValues pVs;
	private Resource resource;
	private Class beanClass;
	private String scope;
	private boolean lazyInit;
	
	public BeanDefinition() {
		
	}

	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getBean() {
		return bean;
	}
	public void setBean(Object bean) {
		this.bean = bean;
	}
	public String getBeanClassName() {
		return beanClassName;
	}
	public void setBeanClassName(String beanClassName) {
		this.beanClassName = beanClassName;
	}
	public PropertyValues getpVs() {
		return pVs;
	}
	public void setpVs(PropertyValues pVs) {
		this.pVs = pVs;
	}
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	public Class getBeanClass() {
		return beanClass;
	}
	public void setBeanClass(Class beanClass) {
		this.beanClass = beanClass;
	}
	public boolean isLazyInit() {
		return lazyInit;
	}
	public void setLazyInit(boolean lazyInit) {
		this.lazyInit = lazyInit;
	}
	
}
