package com.yxz.mySimpleMVC.ioc.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Yu
 * 资源配置入口
 */
public interface Resource {
	
	InputStream getInputStream() throws IOException;

}
