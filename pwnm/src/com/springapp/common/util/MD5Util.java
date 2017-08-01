package com.springapp.common.util;

import java.io.File;
import java.security.MessageDigest;

import org.apache.commons.io.FileUtils;

public class MD5Util {

	/**
	 * 把字节转换为字符串
	 * 
	 * @param str
	 * @return String
	 * 
	 */
	public static String MD5(String str) {
		try {
			return MD5(str.getBytes("UTF8"));
		} catch (Exception e) {
			return null;
		}
	}

	public static String MD5(File file) {
		try {
			byte[] data = FileUtils.readFileToByteArray(file);
			return MD5(data);
		} catch (Exception e) {
			return null;
		}
	}

	public static String MD5(byte[] data) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(data);
			byte s[] = md.digest();
			String result = "";
			for (int i = 0; i < s.length; i++)
				result = result + Integer.toHexString(0xff & s[i] | 0xffffff00).substring(6);
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	public static byte[] MD5byte(byte[] data) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(data);
			byte s[] = md.digest();
			return s;
		} catch (Exception e) {
			return null;
		}
	}
}
