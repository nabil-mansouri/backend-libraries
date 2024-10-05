package com.nm.app.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.net.util.Base64;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.RijndaelEngine;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.paddings.ZeroBytePadding;
import org.bouncycastle.crypto.params.KeyParameter;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class HashUtils {

	public static String toMD5(String data) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.update(data.getBytes());
		byte[] digest = messageDigest.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(Integer.toHexString((int) (b & 0xff)));
		}
		return sb.toString();
	}

	public static byte[] encryptWithRijndael256(String text, byte[] givenKey)
			throws DataLengthException, IllegalStateException, InvalidCipherTextException, IOException {
		final int keysize;

		if (givenKey.length <= 192 / Byte.SIZE) {
			keysize = 192;
		} else {
			keysize = 256;
		}

		byte[] keyData = new byte[keysize / Byte.SIZE];
		System.arraycopy(givenKey, 0, keyData, 0, Math.min(givenKey.length, keyData.length));
		KeyParameter key = new KeyParameter(keyData);
		BlockCipher rijndael = new RijndaelEngine(256);
		ZeroBytePadding c = new ZeroBytePadding();
		PaddedBufferedBlockCipher pbbc = new PaddedBufferedBlockCipher(rijndael, c);
		pbbc.init(true, key);

		byte[] plaintext = text.getBytes(Charset.forName("UTF8"));
		byte[] ciphertext = new byte[pbbc.getOutputSize(plaintext.length)];
		int offset = 0;
		offset += pbbc.processBytes(plaintext, 0, plaintext.length, ciphertext, offset);
		offset += pbbc.doFinal(ciphertext, offset);
		return ciphertext;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(toMD5("2lv7epnipbkahud"));
		System.out.println(encrypt("3050960260007", "2lKJkAo7CgwK7HdyjX0vY7Uc4AzZBspN"));
	}

	public static String encrypt(String text, String secretKey) throws Exception {

		byte[] givenKey = secretKey.getBytes(Charset.forName("ASCII"));
		byte[] encrypted = encryptWithRijndael256(text, givenKey);
		String result = new String(Base64.encodeBase64(encrypted));
		result = StringMoreUtils.strReplace(new String[] { "\\+", "/", "=" }, new String[] { "-", "_", "." }, result);
		return result;
	}
}
