package com.springapp.common.util;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String处理的实用类
 * 
 */
public class StringUtil {
	/**
	 * 默认构造函数
	 */
	public StringUtil() {
	}

	/**
	 * null转换成空
	 * @param obj 对象
	 * @return
	 */
	public static String showNull2Empty(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return String.valueOf(obj);
		}
	}

	/**
	 * 替换字符串
	 * @param str 被替换的字符串
	 * @param oldStr 老字符串
	 * @param newStr 新字符串
	 * @return
	 */
	public static String replace(String str, String oldStr, String newStr) {
		String ret = "";
		String tStr = str;
		int i = -1;
		
		if (str == null || str.equals("") || oldStr == null || oldStr.equals("") || newStr == null) {
			return str;
		}
		int oldStrLen = oldStr.length();
		
		do {
			i = tStr.indexOf(oldStr);
			if (i != -1) {
				ret += tStr.substring(0, i) + newStr;
				if (i + oldStrLen >= tStr.length()) {
					tStr = "";
					break;
				} else {
					tStr = tStr.substring(i + oldStrLen);
				}
			} else {
				ret += tStr;
			}
		} while (i != -1);

		return ret;
	}

	/**
	 * 中文GBK转换成asc
	 * @param s 字符串
	 * @return
	 */
	public static String chineseToAscii(String s) {
		try {
			if (s == null) {
				return null;
			} else {
				s = new String(s.getBytes("GBK"), "ISO8859_1");
				return s;
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * asc转换成中文GBK
	 * @param s 字符串
	 * @return
	 */
	public static String asciiToChinese(String s) {
		try {
			if (s == null) {
				return null;
			} else {
				s = new String(s.getBytes("ISO8859_1"), "GBK");
				return s;
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * UTF-8转换成asc
	 * @param s 字符串
	 * @return
	 */
	public static String UTF8ToAscii(String s) {
		try {
			if (s == null) {
				return null;
			} else {
				s = new String(s.getBytes("UTF-8"), "ISO8859_1");
				return s;
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * asc转换成UTF-8
	 * @param s 字符串
	 * @return
	 */
	public static String asciiToUTF8(String s) {
		try {
			if (s == null) {
				return null;
			} else {
				s = new String(s.getBytes("ISO8859_1"), "UTF-8");
				return s;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @param regularExpression 正则表达式
	 * @param sub 
	 * @param input 
	 * @return
	 */
	public static synchronized String regexReplace(String regularExpression, String sub,
			String input) {
		Pattern pattern = PatternFactory.getPattern(regularExpression);
		Matcher matcher = pattern.matcher(input);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, sub);
		}

		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 
	 * @param regularExpression 正则表达式
	 * @param input 
	 * @return
	 */
	public static synchronized boolean exist(String regularExpression, String input) {
		Pattern pattern = PatternFactory.getPattern(regularExpression);
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}

	/**
	 * 
	 * @param str 字符串
	 * @return
	 */
	public static String parseCollection(String str) {
		String result = null;
		Pattern pattern = Pattern.compile("[a-z]{3}[0-9]{2}[0-9a-z]");
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
			result = matcher.group(0);
		}
		if (result == null) {
			return "";
		} else {
			return result.toLowerCase();
		}
	}

	/**
	 * 前补零
	 * @param str 字符串
	 * @param size 长度
	 * @return
	 */
	public static String fillZero(String str, int size) {
		String result;
		if (str.length() < size) {
			char[] s = new char[size - str.length()];
			for (int i = 0; i < size - str.length(); i++) {
				s[i] = '0';
			}

			result = new String(s) + str;
		} else {
			result = str;
		}
		return result;
	}
	
	/**
	 * 后补零
	 * @param str 字符串
	 * @param size 长度
	 * @return
	 */
	public static String fillZeroAfter(String str, int size) {
		String result;
		if (str.length() < size) {
			char[] s = new char[size - str.length()];
			for (int i = 0; i < size - str.length(); i++) {
				s[i] = '0';
			}

			result =  str + new String(s);
		} else {
			result = str;
		}
		return result;
	}


	/**
	 * 前补零
	 * @param num 数字
	 * @param size 长度
	 * @return
	 */
	public static String fillZero(Integer num, int size) {
		String str = "";
		if (num != null) {
			str = num.toString();
		}
		
		String result;
		if (str.length() < size) {
			char[] s = new char[size - str.length()];
			for (int i = 0; i < size - str.length(); i++) {
				s[i] = '0';
			}

			result = new String(s) + str;
		} else {
			result = str;
		}
		return result;
	}
	
	/**
	 * 得到指定长度的字符串
	 * @param str 字符串
	 * @param size 长度
	 * @return
	 */
	public static String getStrOfLength(String str, int size) {
		String result = "";
		for (int i = 0; i < size; i++) {
			result += str;
		}
		return result;
	}

	/**
	 * 移除排序部分
	 * @param hql 
	 * @return
	 */
	public static synchronized String removeOrderBy(String hql) {
		String regexp = "order\\s+by";
		String spaceReg = "\\s+";
		hql = regexReplace(spaceReg, " ", hql);
		String orderHql = hql;
		if (exist(regexp, orderHql.toLowerCase())) {
			orderHql = regexReplace(regexp, "order by", orderHql.toLowerCase());
			int orderByIndex = orderHql.lastIndexOf("order by");
			hql = hql.substring(0, orderByIndex) + "order by" + " "
					+ hql.substring(orderByIndex + 8);
		}
		return hql;
	}

	/**
	 * 转换首字母为大写
	 * @param source 
	 * @return
	 */
	public static String converFirstUpper(String source) {
		if (source == null || source.equals("")) {
			return source;
		} else {
			return source.substring(0, 1).toUpperCase() + source.substring(1);
		}
	}

	/**
	 * 转换首字母为小写
	 * @param source
	 * @return
	 */
	public static String converFirstLower(String source) {
		if (source == null || source.equals("")) {
			return source;
		} else {
			return source.substring(0, 1).toLowerCase() + source.substring(1);
		}
	}

	/**
	 * 返回一个非null字符对象
	 * 
	 * @param obj 对象
	 * @return String
	 */
	public static String toString(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return obj.toString();
		}
	}

	/**
	 * 替换非法的字符（<,>,\n,空格,/）
	 * 
	 * @param input
	 *            转换前的中文字符串
	 * @return 返回替换后的字符串
	 */
	public static String replaceInvalid(String input) {
		if (input == null || input.length() == 0) {
			return input;
		}
		StringBuffer buf = new StringBuffer(input.length() + 6);
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == '<') {
				buf.append("&lt;");
			} else if (input.charAt(i) == '>') {
				buf.append("&gt;");
			} else if (input.charAt(i) == '\n') {
				buf.append("<br>;");
			} else if (input.charAt(i) == ' ') {
				buf.append("&nbsp;");
			} else if (input.charAt(i) == '\'') {
				buf.append("&acute;");
			} else {
				buf.append(input.charAt(i));
			}
		}
		return buf.toString();
	}

	/**
	 * 得到非空字符串（其中空的对象转换成空的字符窜）
	 * @param obj 对象
	 * @return
	 */
	public static String getNotNullString(Object obj) {
		if (obj == null) {
			return "";
		} else {
			if (obj.getClass().equals(Date.class)) {
				return (new SimpleDateFormat("yyyy-MM-dd")).format(obj);
			} else if (obj.getClass().equals(Timestamp.class)) {
				return (new SimpleDateFormat("yyyy-MM-dd")).format(obj);
			} else if (obj.getClass().equals(Double.class)) {
				return (new DecimalFormat("##0.00")).format(obj);
			} else {
				return obj.toString();
			}
		}
	}

	/**
	 * 
	 * @param data
	 *            2维数组,其中第2维为2个元素，第一个为key，第二个为value
	 * @return HashMap
	 */
	public static HashMap<String, String> transArray2HashMap(String[][] data) {
		HashMap<String, String> params = new HashMap<String, String>();
		if (data != null) {
			for (int i = 0; i < data.length; i++) {
				params.put(data[i][0], data[i][1]);
			}
		}
		return params;
	}

	/**
	 * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
	 * hexStr2ByteArr(String strIn) 互为可逆的转换过程
	 * 
	 * @param arrB
	 *            需要转换的byte数组
	 * @return 转换后的字符串
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出
	 */
	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
	 * 互为可逆的转换过程
	 * 
	 * @param strIn
	 *            需要转换的字符串
	 * @return 转换后的byte数组
	 * @throws Exception
	 *             本方法不处理任何异常，所有异常全部抛出
	 */
	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	/**
	 * 字符串转换成16进制字符串
	 * @param s 字符串
	 * @return
	 */
	public static String strToHexStr(String s) {
		String result = "";
		char[] temp = s.toCharArray();
		for (int i = 0; i < temp.length; i++) {
			result = result + Integer.toHexString((int) temp[i]);
		}

		return result;
	}

	/**
	 * 16进制字符串转换成字符串
	 * @param hex 
	 * @return
	 */
	public static String hexStrToStr(String hex) {
		char[] chars = hex.toCharArray();
		byte[] bytes = new byte[chars.length / 2];
		int byteCount = 0;
		for (int i = 0; i < chars.length; i += 2) {
			byte newByte = 0x00;
			newByte |= hexCharToByte(chars[i]);
			newByte <<= 4;
			newByte |= hexCharToByte(chars[i + 1]);
			bytes[byteCount] = newByte;
			byteCount++;
		}

		String result = "";
		for (int i = 0; i < bytes.length; i++) {
			char c = (char) (new Integer(bytes[i]).intValue());

			result += c;
		}

		return result;
	}

	/**
	 * 十六近制字符转换成byte
	 * 
	 * @param ch
	 * @return
	 */
	private static byte hexCharToByte(char ch) {
		switch (ch) {
		case '0':
			return 0x00;
		case '1':
			return 0x01;
		case '2':
			return 0x02;
		case '3':
			return 0x03;
		case '4':
			return 0x04;
		case '5':
			return 0x05;
		case '6':
			return 0x06;
		case '7':
			return 0x07;
		case '8':
			return 0x08;
		case '9':
			return 0x09;
		case 'a':
			return 0x0A;
		case 'b':
			return 0x0B;
		case 'c':
			return 0x0C;
		case 'd':
			return 0x0D;
		case 'e':
			return 0x0E;
		case 'f':
			return 0x0F;
		}
		return 0x00;
	}
	
	/**
	 * 得到字符窜数组的字符
	 * @param strs 字符串数组
	 * @return
	 */
	public static String getArrayString(String[] strs) {
		StringBuffer str = new StringBuffer();
		
		if (strs != null) {
			for (int i = 0; i < strs.length; i++) {
				str.append(strs[i] + "\n");
			}
		}
		
		return str.toString();
	}
	
	/**
	 * 根据数据字段名称得到java的属性
	 * @param dbFieldName 数据库字段
	 * @return java对象属性
	 */
	public static String getJavaFieldName(String dbFieldName) {
		StringBuffer javaFieldName = new StringBuffer();
		
		String[] fieldToken = dbFieldName.toLowerCase().split("_");
		for (int i = 0; i < fieldToken.length; i++) {
			if ( i == 0 ) {
				javaFieldName.append(fieldToken[i]);
			} else {
				javaFieldName.append(StringUtil.converFirstUpper(fieldToken[i]));
			}
		}
		
		return javaFieldName.toString();
	}
	
	/**
	 * 得到java class名称
	 * @param sourceClassName 原类名
	 * @return java对象属性
	 */
	public static String getJavaClassName(String sourceClassName) {
		StringBuffer javaClassName = new StringBuffer();
		
		String[] token = sourceClassName.toLowerCase().split("_");
		for (int i = 0; i < token.length; i++) {
			if ( i == 0 ) {
				javaClassName.append(token[i]);
			} else {
				javaClassName.append(StringUtil.converFirstUpper(token[i]));
			}
		}
		
		return converFirstUpper(javaClassName.toString());
	}
	
	/**
	 * 格式化,把字符串中{n}用后面的字符串替换掉
	 * @param str
	 * @param values
	 * @return
	 */
	public static String format(String str,Object... values) {
		String retString = str;
		
		for (int i = 0; i < values.length; i++) {
			retString = replace(retString, "{" + i + "}", values[i].toString());
		}
		
		return retString;
	}
	
	/**
	 * 格式化,把字符串中{key}用key对应的的字符串替换掉
	 * @param str
	 * @param values
	 * @return
	 */
	public static String formatParam(String str, Map<String, String> values) {
		String retString = str;
		
		for (Iterator<String> iterator = values.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			String value = values.get(key);
			
			retString = replace(retString, "{" + key + "}", value);
		}
		
		return retString;
	}
	
	/**
	 * 得到str对应数的下一个数，不足长度部分填0
	 * 譬如输入"0001",5 输出为00002
	 * @param str 字符串
	 * @param length format的长度
	 * @return 下一个数
	 */
	public static String getNextFormatNumber(String str, int length) {
		Long number;
		try {
			number = Long.valueOf(str);
		} catch (NumberFormatException e) {
			number = Long.valueOf(0);
		}
		
		number++;
		
		return fillZero(number.toString(), length);
	}
	
	/**
	 * UTF-8转换成中文
	 * @param s 字符串
	 * @return
	 */
	public static String UTF8ToChinese(String s) {
		try {
			StringBuffer sb = new StringBuffer();
			Pattern p = Pattern.compile("(?i)\\\\u([\\da-f]{4})");
			Matcher m = p.matcher(s);
			while(m.find()) {
			    m.appendReplacement(sb, Character.toString((char)Integer.parseInt(m.group(1), 16)));
			}
			m.appendTail(sb);
			 
			String retString = sb.toString();
			
			return retString;
		} catch (Exception e) {
			return null;
		}
	}
}
