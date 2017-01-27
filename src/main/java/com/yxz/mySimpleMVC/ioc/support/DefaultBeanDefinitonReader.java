package com.yxz.mySimpleMVC.ioc.support;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.yxz.mySimpleMVC.ioc.BeanFactory;
import com.yxz.mySimpleMVC.ioc.io.Resource;
import com.yxz.mySimpleMVC.ioc.io.ResourceLoader;

public class DefaultBeanDefinitonReader implements BeanDefinitonReader {

	private BeanFactory beanFactory;
	private ResourceLoader resourceLoader;
	
	public DefaultBeanDefinitonReader(BeanFactory beanFactory, ResourceLoader resourceLoader) {
		this.beanFactory = beanFactory;
		this.resourceLoader = resourceLoader;
	}
	
	@Override
	public void loadBeanDefiniton(String path) throws Exception {
		loadBeanDefiniton(this.resourceLoader.getReource(path));
	}

	@Override
	public void loadBeanDefiniton(Resource resource) throws Exception {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbFactory.newDocumentBuilder();
		InputStream in = resource.getInputStream();
		try {
			Document doc = db.parse(in);
			Element root = doc.getDocumentElement();
			NodeList beans = root.getChildNodes();
			for(int i = 0; i < beans.getLength(); i++) {
				Node bean = beans.item(i);
				if(bean.getNodeType() != Node.ELEMENT_NODE)
					continue;
				BeanDefinition beanDefinition = DocumentReader.parseBeanDefinitionElement((Element)bean);
				this.getRegistry().registerBeanDefinition(beanDefinition.getName(), beanDefinition);
			}
		} finally {
			in.close();
		}
	}

	@Override
	public BeanDefinitionRegistry getRegistry() {
		return (BeanDefinitionRegistry) this.beanFactory;
	}

	@Override
	public ResourceLoader getResourceLoader() {
		return this.resourceLoader;
	}

}
