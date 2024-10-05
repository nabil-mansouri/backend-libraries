package com.nm.app.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang3.StringUtils;

import com.google.common.io.Files;

/**
 * @author Nabil Mansouri
 */
public class ZipUtils {

	public static final void zipDirectory(File directory, File zip) throws IOException {
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip));
		zip(directory, directory, zos);
		zos.close();
	}

	private static final void zip(File directory, File base, ZipOutputStream zos) throws IOException {
		File[] files = directory.listFiles();

		byte[] buffer = new byte[8192];
		int read = 0;

		for (int i = 0, n = files.length; i < n; i++) {
			if (files[i].isDirectory()) {
				zip(files[i], base, zos);
			} else {
				FileInputStream in = new FileInputStream(files[i]);
				ZipEntry entry = new ZipEntry(files[i].getPath().substring(base.getPath().length() + 1));
				zos.putNextEntry(entry);
				while (-1 != (read = in.read(buffer))) {
					zos.write(buffer, 0, read);
				}
				in.close();
			}
		}
	}

	public static final File zipDirectory(File unziped) throws IOException {
		File zipped = File.createTempFile("temp_" + System.currentTimeMillis(), ".zip");
		zipDirectory(unziped, zipped);
		return zipped;
	}

	public static final File unzip(File zip) throws IOException {
		File unziped = Files.createTempDir();
		unziped.mkdirs();
		ZipUtils.unzip(zip, unziped);
		return unziped;
	}

	public static final String getRootName(File zip) throws IOException {
		ZipFile archive = new ZipFile(zip);
		try {
			Enumeration<?> e = archive.entries();
			while (e.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) e.nextElement();
				return StringUtils.substringBefore(entry.getName(), "/");
			}
		} finally {
			archive.close();
		}
		return null;
	}

	public static final void unzip(File zip, File extractTo) throws IOException {
		ZipFile archive = new ZipFile(zip);
		Enumeration<?> e = archive.entries();
		while (e.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) e.nextElement();
			File file = new File(extractTo, entry.getName());
			if (entry.isDirectory() && !file.exists()) {
				file.mkdirs();
			} else {
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				InputStream in = archive.getInputStream(entry);
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
				byte[] buffer = new byte[8192];
				int read;

				while (-1 != (read = in.read(buffer))) {
					out.write(buffer, 0, read);
				}
				in.close();
				out.close();
			}
		}
		archive.close();
	}
}
