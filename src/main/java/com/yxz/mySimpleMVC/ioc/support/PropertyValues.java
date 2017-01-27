package com.yxz.mySimpleMVC.ioc.support;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.List;

public class PropertyValues {
	
	private List<PropertyValue> propertyValueList = new ArrayList<>();
	
	public PropertyValues() {
		
	}
	
	public PropertyValues addPropertyValue(PropertyValue pv) {
		int size = this.propertyValueList.size();
		String name = pv.getName();
		for(int i = 0; i < size; i++) {
			PropertyValue pv2 = this.propertyValueList.get(i);
			if(pv2.getName().equals(pv.getName())) {
				return this;
			}
		}
		this.propertyValueList.add(pv);
		return this;
	}
	
	public List<PropertyValue> getPropertyValues() {
		return this.propertyValueList;
	}
	
}
