package com.nm.utils;

import java.io.UnsupportedEncodingException;

/**
 * 
 * @author Nabil
 * 
 */
public class SageUtils {

	public static class SageResult {
		String flag1;
		String flag2;
		String text;
		String number;
		String padding;

		public SageResult(String key) {
			flag1 = key.substring(0, 2);
			flag2 = key.substring(2, 4);
			if (isNumber()) {
				number = key.substring(4, 20);
				padding = key.substring(20, 24);
			} else {
				text = key.substring(4, 24);
			}
		}

		public Double getNumber() {
			return ByteUtils.toDouble(ByteUtils.hexStringToByteArray(number));
		}

		public String getText() throws UnsupportedEncodingException {
			return ByteUtils.toStrings(ByteUtils.hexStringToByteArray(text));
		}

		public boolean isNumber() {
			return flag2.equals("00");
		}

		public boolean isText() {
			return !isNumber();
		}

		@Override
		public String toString() {
			return "ResultSage [flag1=" + flag1 + ", flag2=" + flag2 + ", text=" + text + ", number=" + number + ", padding=" + padding
					+ "]";
		}

	}

	public static void main(String[] args) throws Exception {
		String s = "000040D871BEB851EB850000";
		SageResult sage = transform(s);
		System.out.println(sage);
		System.out.println(sage.getNumber());
		//
		s = "0x0000409C200A3D70A3D70000";
		sage = transform(s);
		System.out.println(sage);
		System.out.println(sage.getNumber());
		//
		s = "0x0000409146F5C28F5C290000";
		sage = transform(s);
		System.out.println(sage);
		System.out.println(sage.getNumber());
		//
		s = "0x373130353033";
		System.out.println(ByteUtils.hexStringToString(s));
	}

	public static SageResult transform(String value) {
		return transform(ByteUtils.hexStringToByteArray(value));
	}

	public static SageResult transform(byte[] value) {
		return new SageResult(ByteUtils.bytesToHex(value));
	}

}
