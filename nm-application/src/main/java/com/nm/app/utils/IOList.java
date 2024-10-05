package com.nm.app.utils;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nabil Mansouri
 * */
public class IOList {

	private List<Object> list = new ArrayList<Object>();

	public <T extends InputStream> T add(T arg0) {
		list.add(arg0);
		return arg0;
	}

	public <T extends OutputStream> T add(T arg0) {
		list.add(arg0);
		return arg0;
	}

	public void close() {
		for (Object o : list) {
			if (o instanceof Flushable) {
				try {
					((Flushable) o).flush();
				} catch (IOException e) {
				}
			}
			//
			if (o instanceof Closeable) {
				try {
					((Closeable) o).close();
				} catch (IOException e) {
				}
			}
		}
	}
}
