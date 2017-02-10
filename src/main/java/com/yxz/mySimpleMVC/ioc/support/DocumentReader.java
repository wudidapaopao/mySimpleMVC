package com.yxz.mySimpleMVC.ioc.support;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * 解析定义bean的xml文件
 */
public class DocumentReader {

	public static BeanDefinition parseBeanDefinitionElement(Element bean) {
		BeanDefinition beanDefinition = new BeanDefinition();
		beanDefinition.setBeanClassName(bean.getAttribute("class"));
		beanDefinition.setName(bean.getAttribute("id"));
		beanDefinition.setScope(bean.getAttribute("scope"));
		beanDefinition.setLazyInit(Boolean.valueOf(bean.getAttribute("lazy-init")));
		NodeList nodeList = bean.getElementsByTagName("property");
		for(int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			parsePropertyElement(node, beanDefinition);
		}
		return beanDefinition;
	}
	
	private static void parsePropertyElement(Node node, BeanDefinition beanDefinition) {
		if(node instanceof Element) {
			Element e = (Element) node;
			String name = e.getAttribute("name");
			String value = e.getAttribute("value");
			if(beanDefinition.getpVs() == null)
				beanDefinition.setpVs(new PropertyValues());
			if(!(value == null || value.length() == 0)) {
				beanDefinition.getpVs().addPropertyValue(new PropertyValue(name, value));
			}
			else {
				String ref = e.getAttribute("ref");
				if(ref == null || ref.length() == 0)
					throw new IllegalArgumentException();
				BeanReference br = new BeanReference();
				br.setName(ref);
				beanDefinition.getpVs().addPropertyValue(new PropertyValue(name, ref));
			}
		}
	}

}
