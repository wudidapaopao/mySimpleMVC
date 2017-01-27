package com.yxz.mySimpleMVC.ioc.support;

/**
 * @author Yu
 * bean引用的包装类,引用的bean不能是prototype
 */
public class BeanReference {

	private String name;
	private Object object;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}

}
