package com.yxz.mySimpleMVC.dispatcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.iterators.EntrySetMapIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxz.mySimpleMVC.config.BeanGet;
import com.yxz.mySimpleMVC.exception.BeanNotFoundException;
import com.yxz.mySimpleMVC.urlMapping.DefaultUrlMapping;
import com.yxz.mySimpleMVC.urlMapping.Handler;
import com.yxz.mySimpleMVC.urlMapping.UrlMapping;
import com.yxz.mySimpleMVC.util.JsonUtil;
import com.yxz.mySimpleMVC.util.ReflectionUtil;

/**
 * @author Yu
 * 请求分发Servlet
 */
public class DispatcherServlet extends HttpServlet {

	private static final Logger logger =LoggerFactory.getLogger(DispatcherServlet.class);
	
	private UrlMapping urlMapping;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 Handler handler = this.urlMapping.getHandler(req);
		 Class<?> controllerClass = handler.getClass();
		 Method method = handler.getMethod();
		 Param param = new Param();
		 Enumeration<String> names = req.getAttributeNames();
		 while(names.hasMoreElements()) {
			 String name = names.nextElement();
			 param.addParam(name, req.getParameter(name));
		 }
		 InputStream inputStream = req.getInputStream();
		 getParamsFromBody(inputStream, param);
		 Object bean;
			try {
				bean = BeanGet.getBean(controllerClass);
			} catch (BeanNotFoundException e) {
				logger.error("bean is not found", e);
				throw new RuntimeException(e);
			}
		 Object[] args = null;
		 Object result = ReflectionUtil.invokeMethod(bean, method, args); //暂时不支持方法参数的自动注入
		 if(result instanceof JspView) { //jsp视图
			 JspView jv = (JspView) result;
			 String path = jv.getPath();
			 if(path != null && path.length() != 0) {
				 if(true) { //请求forward
					 Map<String, Object> paramMap = jv.getParamMap();
					 for(Map.Entry<String, Object> entry : paramMap.entrySet()) {
						 req.setAttribute(entry.getKey(), entry.getValue());
					 }
					 req.getRequestDispatcher(path).forward(req, resp);
				 }
				 else { //请求redirect,暂时不支持
					 resp.sendRedirect(path);
				 }
			 }
		 }
		 else if(result instanceof JsonView) { //返回json数据
			 PrintWriter writer = resp.getWriter();
			 JsonView jv = (JsonView) result;
			 String jsonStr = JsonUtil.getJsonStr(jv);
			 if(jsonStr != null) {
				 resp.setContentType("application/json");
				 resp.setCharacterEncoding("UTF-8");
				 writer.write(jsonStr);
			 }
			 writer.flush();
			 writer.close();
		 }
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		this.urlMapping = new DefaultUrlMapping();
		this.urlMapping.init();
	}
	
	//从请求体中获取参数
	private void getParamsFromBody(InputStream inputStream, Param para) {
		try {
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String tmp = null;
			while((tmp = br.readLine()) != null) {
				sb.append(tmp);
			}
			String body = sb.toString();
			String[] params = body.split("&");
			if(params != null) {
				for(String param : params) {
					if(param != null) {
						String[] kv = param.split("=");
						if(kv != null && kv.length == 2) {
							para.addParam(kv[0], kv[1]);
						}
					}
				}
			}
		} catch(Exception e) {
			logger.error("failed to get params", e);
			throw new RuntimeException(e);
		}	
	}

}
