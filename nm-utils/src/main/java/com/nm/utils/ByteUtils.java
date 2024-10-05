package com.nm.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Base64;

import org.apache.any23.encoding.TikaEncodingDetector;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;

/**
 * 
 * @author Nabil
 * 
 */
public class ByteUtils {

	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	public static String hexStringToString(String hexString) throws Exception {
		if (hexString.startsWith("0x")) {
			hexString = hexString.substring(2);
		}
		byte[] bytes = Hex.decodeHex(hexString.toCharArray());
		return (new String(bytes, "UTF-8"));
	}

	public static byte[] hexStringToByteArray(String s) {
		if (s.startsWith("0x")) {
			s = s.substring(2);
		}
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	public static byte[] toByteArray(double value) {
		byte[] bytes = new byte[8];
		ByteBuffer.wrap(bytes).putDouble(value);
		return bytes;
	}

	public static double toDouble(byte[] bytes) {
		return ByteBuffer.wrap(bytes).getDouble();
	}

	public static String toBase64Encode(byte[] file) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		sb.append(toStrings(Base64.getEncoder().encode(file)));
		return sb.toString();
	}

	public static String toStrings(byte[] bytes) throws UnsupportedEncodingException {
		return new String(bytes, Charset.forName("UTF-8"));
	}

	public static String toStrings(InputStream bytes) throws IOException {
		return toStrings(IOUtils.toByteArray(bytes));
	}

	public static byte[] toBytes(String string) throws UnsupportedEncodingException {
		return string.getBytes(Charset.forName("UTF-8"));
	}

	public static byte[] toCharset(byte[] source, Charset dest) throws IOException {
		Charset sourceCar = Charset.forName(new TikaEncodingDetector().guessEncoding(new ByteArrayInputStream(source)));
		if (sourceCar.equals(dest)) {
			return source;
		} else {
			String string = new String(source, sourceCar);
			return string.getBytes(dest);
		}
	}
}
