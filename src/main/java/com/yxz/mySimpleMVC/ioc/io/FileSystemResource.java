package com.yxz.mySimpleMVC.ioc.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Yu
 *
 */
public class FileSystemResource implements Resource {

	private final File file;

	public FileSystemResource(File file) {
		this.file = file;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new FileInputStream(file);
	}	
	
}
