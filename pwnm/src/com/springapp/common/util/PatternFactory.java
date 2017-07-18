package com.springapp.common.util;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.FastHashMap;

/**
 * 正则表达式
 * 
 */
public class PatternFactory {
	@SuppressWarnings("unchecked")
	private static Map<String, Pattern> patternMap = new FastHashMap();

	/**
	 * 默认构造函数
	 */
	public PatternFactory() {
	}

	/**
	 * 根据正则表达式得到正则表达式对象
	 * @param regExp 正则表达式
	 * @return
	 */
	public static Pattern getPattern(String regExp) {
		Pattern pattern = null;
		if (patternMap.containsKey(regExp)) {
			pattern = patternMap.get(regExp);
		} else {
			pattern = Pattern.compile(regExp);
			patternMap.put(regExp, pattern);
		}
		return pattern;
	}

	/**
	 * 根据正则表达式 过滤数据，提取与正则表达式相符和的数据
	 * @param regExp	正则表达式
	 * @param value		要处理的数据源
	 * @return	提取后的数据列表
	 */
	public static Set<String> filterByPattern(String regExp,String value){
		Set<String> set = new HashSet<String>();
		Matcher matcher =  Pattern.compile(regExp, Pattern.DOTALL).matcher(value);
		while (matcher != null && matcher.find()) {
			int a = matcher.groupCount();
			while ((a--) > 0) {
				set.add(matcher.group(a));
			}
		}
		return set;
	}
	
	public static void main(String arg[]){
		Set<String> filterByPattern = filterByPattern("(/jfly.cityos/[^&]+)","<div align=\"center\"><p><object type=\"application/x-shockwave-flash\" classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\""
				+ " width=\"550\" height=\"400\"><param name=\"movie\" value=\"flvplayer.swf\" /><param name=\"quality\" value=\"high\" />"
				+ "<param name=\"allowfullscreen\" value=\"true\" /><param name=\"allowscriptaccess\" value=\"always\" />"
				+ "<param name=\"flashvars\" value=\"file=/jfly.cityos/upload/media/video.flv&autostart=true\" /"
				+ "><embed type=\"application/x-shockwave-flash\" src=\"flvplayer.swf\" width=\"550\" height=\"400\" allowscriptaccess=\"always\" "
				+ "flashvars=\"file=/jfly.cityos/upload/media/video.flv&autostart=true\" quality=\"high\" /></embed /></object></div>");
		System.out.print(filterByPattern);
	}
}
