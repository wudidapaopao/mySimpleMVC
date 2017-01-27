package com.yxz.mySimpleMVC.util;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yu
 * 手动类加载器工具类
 */
public class ClassLoaderUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(ClassLoaderUtil.class);
	
	private static ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}
	
	public static Set<Class<?>> doLoadClass(String basePackage) {
		Set<Class<?>> sets = new HashSet<Class<?>>();
		try {
			String packagePath = basePackage.replace('.', '/');
			Enumeration<URL> urls = getClassLoader().getResources(basePackage);
			while(urls.hasMoreElements()) {
				URL url = urls.nextElement();
				String protocol = url.getProtocol();
				if("file".equals(protocol)) {
					String path = url.getPath();
					path = path.replace("%20", " ");
					File file = new File(path);
					addFileClass(file, basePackage, sets);
				}
				else if("jar".equals(protocol)) {
					JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
					JarFile jarFile = jarURLConnection.getJarFile();
					if(jarFile != null) {
						Enumeration<JarEntry> entries = jarFile.entries();
						while(entries.hasMoreElements()) {
							JarEntry entry = entries.nextElement();
							String className = entry.getName();
							if(className.endsWith("class")) {
								className = className.substring(0, className.lastIndexOf(".")).replace('/', '.');
								Class<?> clazz = getClass(className, false);
								sets.add(clazz);
							}
						}
					}
				}
			}
		} catch (IOException e) {
			logger.error("", e);
		}
		return sets;
	}

	private static void addFileClass(File file, String basePackage, Set<Class<?>> sets) {
		if(file == null)
			return;
		if(file.isDirectory()) {
			String fileName = file.getName();
			File[] files = file.listFiles();
			for(File f : files) {
				addFileClass(f, basePackage + "." + fileName, sets);
			}
		}
		else if(file.isFile()) {
			String fileName = file.getName();
			if(!fileName.endsWith("class"))
				return;
			fileName = fileName.substring(0, fileName.lastIndexOf('.'));
			String className = basePackage + "." + fileName;
			Class<?> clazz = getClass(className, false);
			sets.add(clazz);
		}
	}
	
	public static Class<?> getClass(String className, boolean auto) {
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className, auto, getClassLoader());//false表示加载类，但不执行类的静态语句块，以提升加载类的速度
		} catch (ClassNotFoundException e) {
			logger.error("failed to load class : " + className);
			throw new RuntimeException(e);
		}
		return clazz;
	}
	
}
