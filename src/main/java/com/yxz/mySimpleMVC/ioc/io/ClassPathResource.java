package com.yxz.mySimpleMVC.ioc.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Yu
 * ClassPath下的资源
 */
public class ClassPathResource implements Resource {

	private final URL url;
	
	public ClassPathResource(URL url) {
		this.url = url;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		URLConnection conn = url.openConnection();
		return conn.getInputStream();
	}

}
