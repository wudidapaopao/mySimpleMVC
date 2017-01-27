package com.yxz.mySimpleMVC.ioc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxz.mySimpleMVC.exception.BeanNotFoundException;
import com.yxz.mySimpleMVC.exception.IocFactoyException;
import com.yxz.mySimpleMVC.ioc.io.DefaultResourceLoader;
import com.yxz.mySimpleMVC.ioc.io.Resource;
import com.yxz.mySimpleMVC.ioc.io.ResourceLoader;
import com.yxz.mySimpleMVC.ioc.support.BeanDefinition;
import com.yxz.mySimpleMVC.ioc.support.BeanDefinitionRegistry;
import com.yxz.mySimpleMVC.ioc.support.BeanReference;
import com.yxz.mySimpleMVC.ioc.support.DefaultBeanDefinitonReader;
import com.yxz.mySimpleMVC.ioc.support.PropertyValue;
import com.yxz.mySimpleMVC.ioc.support.PropertyValues;

/**
 * @author Yu
 * 基于xml配置的ioc容器
 */
public class XmlBeanFactory implements BeanDefinitionRegistry, BeanFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(XmlBeanFactory.class);
	
	public static final String SINGLE = "singleton";
	public static final String PROTO = "prototype";
	
	private final ResourceLoader resourceLoader = new DefaultResourceLoader();
	private final DefaultBeanDefinitonReader reader = new DefaultBeanDefinitonReader(this, this.resourceLoader);
	private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
	private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();
	
	public XmlBeanFactory(String path) {
		Resource resource = this.resourceLoader.getReource(path);
		try {
			this.reader.loadBeanDefiniton(resource);
		} catch (Exception e) {
			logger.error("failed to build XmlFactory", e);
			throw new IocFactoyException("failed to build XmlFactory", e);
		}
		initSingleton();
	}
	
	/**
	 * 初始化非延迟加载的单例对象
	 */
	private void initSingleton() {
		for(Map.Entry<String, BeanDefinition> entry : this.beanDefinitionMap.entrySet()) {
			String name = entry.getKey();
			if(!singletonObjects.containsKey(name)) {
				BeanDefinition bd = entry.getValue();
				if(!bd.isLazyInit() && SINGLE.equals(bd.getScope())) {
					try {
						creatBean(name, bd);
					} catch (Exception e) {
						logger.error("failed to creat bean : " + name, e);
					} 
				}
			}
		}
	}
	
	/*
	 * 暂时只支持单例的延迟加载和非延迟加载，不支持多例
	 */
	@Override
	public Object getBean(String name) throws BeanNotFoundException {
		try {
			BeanDefinition bd = beanDefinitionMap.get(name);
			if(!PROTO.equals(bd.getScope())) {
				if(this.singletonObjects.containsKey(name)) {
					return this.singletonObjects.get(name);
				}
				else {
					creatBean(name, this.beanDefinitionMap.get(name));
					return this.singletonObjects.get(name);
				}
			}
			else {
				//todo 多例对象的返回
				return null;
			}	
		} catch(Exception e) {
			throw new BeanNotFoundException(e);
		}
	}

	/*
	 * 生产出实体bean
	 */
	private Object creatBean(String name, BeanDefinition bd) throws Exception {
		Class clazz = bd.getBeanClass();
		Object instance = clazz.newInstance();
		this.singletonObjects.put(name, instance);
		PropertyValues pVs = bd.getpVs();
		applyPropertyValues(instance, pVs, bd, name);
		return instance;
	}

	@Override
	public boolean containsBean(String name) {
		return this.beanDefinitionMap.containsKey(name);
	}

	@Override
	public BeanDefinition getBeanDefinition(String name) {
		return this.beanDefinitionMap.get(name);
	}

	@Override
	public void removeBeanDefinition(String name) {
		this.beanDefinitionMap.remove(name);
	}

	@Override
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
		this.beanDefinitionMap.put(beanName, beanDefinition);
	}

	@Override
	public boolean containsBeanDefintion(String name) {
		return this.beanDefinitionMap.containsKey(name);
	}
	
	private void applyPropertyValues(Object bean, PropertyValues pVs, BeanDefinition bd, String name) throws Exception {
		if(pVs == null)
			return;
		List<PropertyValue> pvlist = pVs.getPropertyValues();
		for(int i = 0; i < pvlist.size(); i++) {
			PropertyValue pv = pvlist.get(i);
			Object resolvedValue;
			resolvedValue = resolveValueIfNecessary(pv, bd, name);
			setPropertyValue(bean, pv.getName(), resolvedValue);
		}
	}

	private void setPropertyValue(Object bean, String name, Object value) throws Exception {
		String methodName = "set" + name.substring(0,1).toUpperCase() + name.substring(1);
		try {
			Method declaredMethod = bean.getClass().getDeclaredMethod(methodName, value.getClass());
			declaredMethod.setAccessible(true);
			declaredMethod.invoke(bean, value);
		} catch (NoSuchMethodException ex) {
			Field declaredField = bean.getClass().getDeclaredField(name);
			declaredField.setAccessible(true);
			declaredField.set(bean, value);
		}
	}

	private Object resolveValueIfNecessary(PropertyValue pv, BeanDefinition bd, String name) throws Exception {
		Object value = pv.getValue();
		if(value instanceof String)
			return value;
		else if(value instanceof BeanReference) {
			value = (BeanReference) value;
			String ref = ((BeanReference) value).getName();
			Object bean = this.singletonObjects.get(ref);
			if(bean == null) {
				bean = creatBean(name, bd);
			}
			return bean;
		}
		return null;
	}

}
