/*
 * 日期：2007-11-21
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
/**
 *
 */
package com.springapp.common.util;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Ticket:
 *
 */
@SuppressWarnings("unchecked")
public class DateTimeUtil {

	/**
	 * 十分钟的毫秒数
	 */
	public static final long MSEL_OF_TENMINUTE = 60 * 1000 * 10;

	/**
	 * 一天的时间毫秒数
	 */
	public static final long MSEL_OF_DAY = 86400000;

	/**
	 * 时间间隔
	 */
	public static final short TIME_PERIOD_IN_MINUTES = 5;

	/**
	 * 12个月份
	 */
	public static final String[] MONTH_12 = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };

	/**
	 * 1，3，5，7，8，10，12月份包含天数
	 */
	public static final String[] DAY_31 = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14",
			"15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };

	/**
	 * 4，6，9，11月份包含天数
	 */
	public static final String[] DAY_30 = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14",
			"15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" };

	/**
	 * 2月份包含天数(非闰年)
	 */
	public static final String[] DAY_28 = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14",
			"15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28" };

	/**
	 * 2月份包含天数(闰年)
	 */
	public static final String[] DAY_29 = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14",
			"15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29" };

	/**
	 * 返回 格式HH:mm:ss的字符串
	 *
	 * @param seconds
	 *            距离凌晨0点的秒数
	 * @return
	 */
	public static String formatTimeString(long seconds) {
		String pattern = "HH:mm:ss";
		Date date = new Date(seconds * 1000);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * Ticket# 8069 2009-7-28 下午04:29:58
	 *
	 * @param seconds
	 *            距离凌晨0点的秒数
	 * @param timeZone
	 *            时区(eg.GMT+0)
	 * @Description 根据所指定市区获取对应秒数距凌晨0点的具体时间值
	 */
	public static String formatTimeStringByTimeZoneGMT(Long seconds, String timeZone) {
		String pattern = "HH:mm:ss";
		Date date = new Date(seconds * 1000);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
		return sdf.format(date);
	}

	/**
	 * 返回距离凌晨0点的秒数
	 *
	 * @param timeString
	 *            格式HH:mm:ss的字符串
	 * @return
	 */
	public static long parseTimeString(String timeString) {
		String pattern = "HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(timeString).getTime() / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 把Date类型转化为short数组，返回格式：日，月，年
	 *
	 * @param date
	 * @return
	 */
	public static short[] switchDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		short day = (short) c.get(Calendar.DAY_OF_MONTH);
		short month = (short) (c.get(Calendar.MONTH) + 1);
		short year = (short) c.get(Calendar.YEAR);
		return new short[] { day, month, year };
	}

	/**
	 * 根据long时间类型返回Date时间类型
	 *
	 * @param longTime
	 *            距离当天凌晨0点的秒数
	 * @return
	 */
	public static Date getDate(Long longTime) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, longTime.intValue());
		return c.getTime();

	}

	/**
	 * 根据long时间类型返回Date时间类型
	 *
	 * @param longTime
	 *            距离1970年1月1日 0点的毫秒数
	 * @return
	 */
	public static Date getDateFromLong(long longTime) {
		return new Date(longTime);
	}

	/**
	 * 转换Date 为 特定格式的int数；<code>yyyymmdd</code>
	 *
	 * @param date
	 * @return
	 */
	public static int getIntFromDate(Date date) {
		String temp = formatDateToString(date, "yyyyMMdd");
		return Integer.valueOf(temp);
	}

	/**
	 * 特定格式的int数yyyyMMdd,转换为Date类型；
	 *
	 * @param intDate
	 * @return
	 */
	public static Date getDateFromInt(int intDate) {
		String tempDate = String.valueOf(intDate);
		return parseStringToDate(tempDate, "yyyyMMdd");
	}

	public static Date getCurrentMonthFirstDay() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 00);
		c.set(Calendar.MINUTE, 00);
		c.set(Calendar.SECOND, 00);
		return c.getTime();
	}

	public static String getMonthFirstDay(int year, int month) {
		return year + "-" + (month < 10 ? "0" + month : month) + "-" + 01;
	}

	public static String getMonthLastDay(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return year + "-" + (month < 10 ? "0" + month : month) + "-" + c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获得当前时间到秒的ＤＡＴＥ类型
	 *
	 * @return
	 */
	public static Date getNow() {
		Calendar c = Calendar.getInstance();
		return c.getTime();
	}

	/**
	 * 获得当前时间距离1970.1.1.0.0.0的秒数，
	 *
	 * @return
	 */
	public static long getCurrentSeconds() {
		Calendar c = Calendar.getInstance();
		return (c.getTime().getTime() / 1000);
	}

	/**
	 * 获取当前时间 从本地时间0 点起的秒数
	 *
	 * @return currentSeconds
	 */
	public static long getCurrentSecond() {
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int seconds = c.get(Calendar.SECOND);
		long currentSeconds = hour * 3600 + minute * 60 + seconds;
		return currentSeconds;
	}

	/**
	 * 获得当前时间到日期的字符串 String 类型 格式 ＹＹＹＹ－ＭＭ－ＤＤ
	 *
	 * @return
	 */
	public static String getCurrentDateString() {
		Calendar c = Calendar.getInstance();
		return truncDateToString(c.getTime());
	}

	/**
	 * 获得当前时间到日期的字符串 String 类型 格式 ＹＹＹＹ－ＭＭ
	 *
	 * @return
	 */
	public static String getCurrentMonthString() {
		Calendar c = Calendar.getInstance();
		return truncMonthToString(c.getTime());
	}

	/**
	 * 获得当前日期 Date 类型
	 *
	 * @return
	 */
	public static Date getCurrentBeginDate() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 00);
		c.set(Calendar.MINUTE, 00);
		c.set(Calendar.SECOND, 00);
		c.set(Calendar.MILLISECOND, 00);
		return c.getTime();
	}

	/**
	 * 获得当前日期 Date 类型
	 *
	 * @return
	 */
	public static Date getCurrentEndDate() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();
	}

	/**
	 * 获得当前日期 Date 类型
	 *
	 * @return
	 */
	public static Date beginDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 00);
		c.set(Calendar.MINUTE, 00);
		c.set(Calendar.SECOND, 00);
		c.set(Calendar.MILLISECOND, 00);
		return c.getTime();
	}

	/**
	 * 获得当前日期 endDate 类型
	 *
	 * @return
	 */
	public static Date endDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();
	}

	/**
	 * 获得 i 天前日期 Date 类型
	 *
	 * @param i
	 *            从今天开始向前倒退 i 天
	 * @return
	 */
	public static Date getProDate(int i) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - i);
		c.set(Calendar.HOUR_OF_DAY, 00);
		c.set(Calendar.MINUTE, 00);
		c.set(Calendar.SECOND, 00);
		return c.getTime();
	}

	/**
	 * 获得 i 月前日期String类型
	 *
	 * @param i
	 *            从今天开始向前倒退 i 月
	 * @return 返回格式：YYYY-MM
	 */
	public static String getProMonth(int i) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, c.get(Calendar.MONTH) - i);
		c.set(Calendar.HOUR_OF_DAY, 00);
		c.set(Calendar.MINUTE, 00);
		c.set(Calendar.SECOND, 00);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String dateString = format.format(c.getTime());
		return dateString;
	}

	/**
	 * 获得前一天 String 类型 格式 ＹＹＹＹ－ＭＭ－ＤＤ
	 *
	 * @return
	 */
	public static String getproDateString() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - 1);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = format.format(c.getTime());
		return dateString;
	}

	/**
	 * 获得当前到秒 String 类型 格式 yyyy－ＭＭ－dd ＨＨ：mm：ss
	 *
	 * @return
	 */
	public static String getCurrentDateInMinString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = format.format(getNow());
		return dateString;
	}

	/**
	 * 把date类型的日期截成ＹＹＹＹ－ＭＭ－ＤＤ格式的字符串
	 *
	 * @param date
	 * @return
	 */
	public static String truncDateToString(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = format.format(date);
		return dateString;
	}

	/**
	 * 把date类型的日期截成ＹＹＹＹ－ＭＭ格式的字符串
	 *
	 * @param date
	 * @return
	 */
	public static String truncMonthToString(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String dateString = format.format(date);
		return dateString;
	}

	/**
	 * 时间格式转换
	 *
	 * @param date
	 *            Date
	 * @param pattern
	 *            formatPattern eg:yyyy-MM-dd HH:mm:ss
	 * @return String String
	 */
	public static String formatDateToString(java.util.Date date, String pattern) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 当前时间格式转换
	 *
	 * @param pattern
	 * @return
	 */
	public static String formatCurrentDateToString(String pattern) {
		return formatDateToString(getNow(), pattern);
	}

	/**
	 * 时间格式转换
	 *
	 * @param strDate
	 *            strDate
	 * @param pattern
	 *            format
	 * @return Date Date
	 */
	public static java.util.Date parseStringToDate(String strDate, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static java.util.Date BCDToDate(long BCD) throws IllegalArgumentException {
		int year = 0;
		int month = 0;
		int day = 0;
		int hour = 0;
		int minute = 0;
		int second = 0;

		year = Integer.parseInt(Long.toString(((BCD & 0x00ff000000000000L) >> 48)), 10);
		year *= 100;
		year += Integer.parseInt(Long.toString(((BCD & 0x0000ff0000000000L) >> 40)), 10);

		month = Integer.parseInt(Long.toString(((BCD & 0x000000ff00000000L) >> 32)), 10) - 1;

		day = Integer.parseInt(Long.toString(((BCD & 0x00000000ff000000L) >> 24)), 10);

		hour = Integer.parseInt(Long.toString(((BCD & 0x0000000000ff0000L) >> 16)), 10);

		minute = Integer.parseInt(Long.toString(((BCD & 0x000000000000ff00L) >> 8)), 10);

		second = Integer.parseInt(Long.toString((BCD & 0x00000000000000ffL)), 10);

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, hour, minute, second);

		java.util.Date date = calendar.getTime();

		return date;

	}

	public static int dayToBCD(int year, int month, int dayOfMonth) throws IllegalArgumentException {
		int BCD = 0;
		// int第二个字节放2007的前两位，07
		int value = year;
		int bcd = 0;
		int param = 1000;
		for (int i = 0; i < 4; i++) {
			int remain = value % param;
			value = value / param;
			bcd = bcd << 4;
			bcd = bcd | value;
			value = remain;
			param = param / 10;
		}

		BCD = bcd << 16;

		// int第三个字节放月份
		BCD = BCD | ((month / 10 << 4) | (month % 10)) << 8;
		// int第四个字节放日期
		BCD = BCD | (dayOfMonth / 10 << 4) | (dayOfMonth % 10);

		return BCD;
	}

	// 传进来的date，返回只要时、分、秒，第一个字节补零
	public static int timeToBCD(java.util.Date date) throws IllegalArgumentException {

		int hour = 0;
		int minute = 0;
		int second = 0;
		int retInt = 0;
		String tempStr = null;

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
		tempStr = format.format(date);

		hour = Integer.parseInt((tempStr.substring(11, 13)), 10) << 16;

		minute = Integer.parseInt((tempStr.substring(14, 16)), 10) << 8;

		second = Integer.parseInt((tempStr.substring(17, 19)), 10);

		retInt = hour + minute + second;

		return retInt;

	}

	public static long dateToBCD(java.util.Date date) {
		long year = 0;
		long month = 0;
		long day = 0;
		long hour = 0;
		long minute = 0;
		long second = 0;
		long retlong = 0;
		String tempStr = null;

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
		tempStr = format.format(date);
		year = Long.parseLong((tempStr.substring(0, 2)), 10) << 48;
		year += Long.parseLong((tempStr.substring(2, 4)), 10) << 40;
		month = Long.parseLong((tempStr.substring(5, 7)), 10) << 32;
		day = Long.parseLong((tempStr.substring(8, 10)), 10) << 24;
		hour = Long.parseLong((tempStr.substring(11, 13)), 10) << 16;
		minute = Long.parseLong((tempStr.substring(14, 16)), 10) << 8;
		second = Long.parseLong((tempStr.substring(17, 19)), 10);
		retlong = year + month + day + hour + minute + second;

		return retlong;

	}

	public static String getCurrentWeekDay() {
		Calendar calendar = Calendar.getInstance(Locale.CHINESE);

		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;

		String weekDayString = "";

		if (dayOfWeek == 0) {
			weekDayString = "日";
		} else if (dayOfWeek == 1) {
			weekDayString = "一";
		} else if (dayOfWeek == 2) {
			weekDayString = "二";
		} else if (dayOfWeek == 3) {
			weekDayString = "三";
		} else if (dayOfWeek == 4) {
			weekDayString = "四";
		} else if (dayOfWeek == 5) {
			weekDayString = "五";
		} else if (dayOfWeek == 6) {
			weekDayString = "六";
		} else if (dayOfWeek == 1) {
			weekDayString = "一";
		}

		return "星期" + weekDayString;
	}

	/**
	 * 获取当前时间距1970年1月1日的天数
	 *
	 * @return 距1970年1月1日的天数
	 */
	public static int getBusinessDay() {
		Long currentTime = new Date().getTime();
		int day = (int) (currentTime / (1000 * 60 * 60 * 24));
		return day;
	}

	/**
	 * 将毫秒值转换成秒（必须先将毫秒除以1000后再强制转换为秒，否则int溢出）
	 *
	 * @param milliseconds
	 * @return 毫秒转换成秒数
	 */
	public static int getSeconds(long milliseconds) {
		return (int) (milliseconds / 1000);
	}

	/**
	 * 获取当前事件 HH:mm:ss
	 *
	 * @return
	 */
	public static String getTime() {
		Calendar currentCalendar = Calendar.getInstance();
		int hour = currentCalendar.get(Calendar.HOUR_OF_DAY);
		int minute = currentCalendar.get(Calendar.MINUTE);
		int second = currentCalendar.get(Calendar.SECOND);
		return hour + ":" + minute + ":" + second;
	}

	/**
	 * 根据指定日期和指定的时间差获得时间
	 *
	 * @param asignedDate
	 *            指定时间
	 * @param diff
	 *            指定时间差，如果要获得的时间早于指定时间则diff<0
	 */
	public static Date getDateDiff(Date asignedDate, int diff) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(asignedDate);
		calendar.add(Calendar.DATE, diff);
		return calendar.getTime();
	}

	/**
	 * 获取n天的同一时刻
	 */
	public static Date getBackDay(int n) {
		Calendar d = Calendar.getInstance();
		d.setTime(new Date());
		d.add(Calendar.DATE, -n);
		return d.getTime();
	}

	public static int getAge(String id) throws Exception {
		int age = -1;
		int length = id.length();
		String birthday = "";
		if (length == 15) {
			birthday = id.substring(6, 8);
			birthday = "19" + birthday;
		} else if (length == 18)
			birthday = id.substring(6, 10);
		else
			throw new Exception("错误的身份证号");
		int currentYear = Calendar.getInstance().get(1);
		age = currentYear - (new Integer(birthday)).intValue();
		return age;
	}

	public static java.sql.Date getDateByAge(int age) {
		Calendar calendar = Calendar.getInstance(Locale.CHINESE);
		// long current = calendar.getTimeInMillis();
		calendar.set(calendar.get(1) - age, calendar.get(2), calendar.get(5));
		return new java.sql.Date(calendar.getTimeInMillis());
	}

	public static String getChineseDate(java.util.Date d) {
		if (d == null) {
			return new String();
		} else {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", new DateFormatSymbols());
			String dtrDate = df.format(d);
			return dtrDate.substring(0, 4) + "年" + Integer.parseInt(dtrDate.substring(4, 6)) + "月"
					+ Integer.parseInt(dtrDate.substring(6, 8)) + "日";
		}
	}

	public static String formatDate(java.util.Date d, String format) {
		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd";
		}
		if (d == null) {
			return "";
		} else {
			SimpleDateFormat df = new SimpleDateFormat(format);
			return df.format(d);
		}
	}

	public static String getCurrentDate_String() {
		Calendar cal = Calendar.getInstance();
		String currentDate = null;
		// java.util.Date d = cal.getTime();
		String currentYear = (new Integer(cal.get(1))).toString();
		String currentMonth = null;
		String currentDay = null;
		if (cal.get(2) < 9)
			currentMonth = "0" + (new Integer(cal.get(2) + 1)).toString();
		else
			currentMonth = (new Integer(cal.get(2) + 1)).toString();
		if (cal.get(5) < 10)
			currentDay = "0" + (new Integer(cal.get(5))).toString();
		else
			currentDay = (new Integer(cal.get(5))).toString();
		currentDate = currentYear + currentMonth + currentDay;
		return currentDate;
	}

	public static String getCurrentDate_String(String strFormat) {
		Calendar cal = Calendar.getInstance();
		// String currentDate = null;
		java.util.Date d = cal.getTime();
		return dateToString(d, strFormat);
	}

	public static int calBetweenTwoMonth(String dealMonth, String alterMonth) {
		int length = 0;
		if (dealMonth.length() != 6 || alterMonth.length() != 6) {
			length = -1;
		} else {
			int dealInt = Integer.parseInt(dealMonth);
			int alterInt = Integer.parseInt(alterMonth);
			if (dealInt < alterInt) {
				length = -2;
			} else {
				int dealYearInt = Integer.parseInt(dealMonth.substring(0, 4));
				int dealMonthInt = Integer.parseInt(dealMonth.substring(4, 6));
				int alterYearInt = Integer.parseInt(alterMonth.substring(0, 4));
				int alterMonthInt = Integer.parseInt(alterMonth.substring(4, 6));
				length = (dealYearInt - alterYearInt) * 12 + (dealMonthInt - alterMonthInt);
			}
		}
		return length;
	}

	public static int convertDateToYear(java.util.Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy", new DateFormatSymbols());
		return Integer.parseInt(df.format(date));
	}

	public static String convertDateToYearMonth(java.util.Date d) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM", new DateFormatSymbols());
		return df.format(d);
	}

	public static String convertDateToYearMonthDay(java.util.Date d) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", new DateFormatSymbols());
		return df.format(d);
	}

	public static int daysBetweenDates(java.util.Date newDate, java.util.Date oldDate) {
		int days = 0;
		Calendar calo = Calendar.getInstance();
		Calendar caln = Calendar.getInstance();
		calo.setTime(oldDate);
		caln.setTime(newDate);
		int oday = calo.get(6);
		int nyear = caln.get(1);
		for (int oyear = calo.get(1); nyear > oyear;) {
			calo.set(2, 11);
			calo.set(5, 31);
			days += calo.get(6);
			oyear++;
			calo.set(1, oyear);
		}

		int nday = caln.get(6);
		days = (days + nday) - oday;
		return days;
	}

	public static java.util.Date getDateBetween(java.util.Date date, int intBetween) {
		Calendar calo = Calendar.getInstance();
		calo.setTime(date);
		calo.add(5, intBetween);
		return calo.getTime();
	}

	public static String getDateBetween_String(java.util.Date date, int intBetween, String strFromat) {
		java.util.Date dateOld = getDateBetween(date, intBetween);
		return dateToString(dateOld, strFromat);
	}

	public static String increaseYearMonth(String yearMonth) {
		int year = (new Integer(yearMonth.substring(0, 4))).intValue();
		int month = (new Integer(yearMonth.substring(4, 6))).intValue();
		if (++month <= 12 && month >= 10)
			return yearMonth.substring(0, 4) + (new Integer(month)).toString();
		if (month < 10)
			return yearMonth.substring(0, 4) + "0" + (new Integer(month)).toString();
		else
			return (new Integer(year + 1)).toString() + "0" + (new Integer(month - 12)).toString();
	}

	public static String increaseYearMonth(String yearMonth, int addMonth) {
		int year = (new Integer(yearMonth.substring(0, 4))).intValue();
		int month = (new Integer(yearMonth.substring(4, 6))).intValue();
		month += addMonth;
		year += month / 12;
		month %= 12;
		if (month <= 12 && month >= 10)
			return year + (new Integer(month)).toString();
		else
			return year + "0" + (new Integer(month)).toString();
	}

	public static String descreaseYearMonth(String yearMonth) {
		int year = (new Integer(yearMonth.substring(0, 4))).intValue();
		int month = (new Integer(yearMonth.substring(4, 6))).intValue();
		if (--month >= 10)
			return yearMonth.substring(0, 4) + (new Integer(month)).toString();
		if (month > 0 && month < 10)
			return yearMonth.substring(0, 4) + "0" + (new Integer(month)).toString();
		else
			return (new Integer(year - 1)).toString() + (new Integer(month + 12)).toString();
	}

	public static String getCurrentTime_String() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		return format.format(date);
	}

	public static java.util.Date getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		java.util.Date d = cal.getTime();
		return d;
	}

	public static String getCurrentYearMonth() {
		Calendar cal = Calendar.getInstance();
		String currentYear = (new Integer(cal.get(1))).toString();
		String currentMonth = null;
		if (cal.get(2) < 9)
			currentMonth = "0" + (new Integer(cal.get(2) + 1)).toString();
		else
			currentMonth = (new Integer(cal.get(2) + 1)).toString();
		return currentYear + currentMonth;
	}

	public static int getCurrentYear() {
		Calendar cal = Calendar.getInstance();
		int currentYear = cal.get(1);
		return currentYear;
	}

	public static int getCurrentMonth() {
		Calendar cal = Calendar.getInstance();

		int currentMonth = cal.get(2) + 1;

		return currentMonth;
	}

	public static int getCurrentDay() {
		Calendar cal = Calendar.getInstance();
		int currentDay = cal.get(5);
		return currentDay;
	}

	public static java.util.Date stringToDate(String strDate, String oracleFormat) {
		if (strDate == null)
			return null;
		Hashtable h = new Hashtable();
		String javaFormat = new String();
		String s = oracleFormat.toLowerCase();
		if (s.indexOf("yyyy") != -1)
			h.put(new Integer(s.indexOf("yyyy")), "yyyy");
		else if (s.indexOf("yy") != -1)
			h.put(new Integer(s.indexOf("yy")), "yy");
		if (s.indexOf("mm") != -1)
			h.put(new Integer(s.indexOf("mm")), "MM");
		if (s.indexOf("dd") != -1)
			h.put(new Integer(s.indexOf("dd")), "dd");
		if (s.indexOf("hh24") != -1)
			h.put(new Integer(s.indexOf("hh24")), "HH");
		if (s.indexOf("mi") != -1)
			h.put(new Integer(s.indexOf("mi")), "mm");
		if (s.indexOf("ss") != -1)
			h.put(new Integer(s.indexOf("ss")), "ss");
		for (int intStart = 0; s.indexOf("-", intStart) != -1; intStart++) {
			intStart = s.indexOf("-", intStart);
			h.put(new Integer(intStart), "-");
		}

		for (int intStart = 0; s.indexOf("/", intStart) != -1; intStart++) {
			intStart = s.indexOf("/", intStart);
			h.put(new Integer(intStart), "/");
		}

		for (int intStart = 0; s.indexOf(" ", intStart) != -1; intStart++) {
			intStart = s.indexOf(" ", intStart);
			h.put(new Integer(intStart), " ");
		}

		for (int intStart = 0; s.indexOf(":", intStart) != -1; intStart++) {
			intStart = s.indexOf(":", intStart);
			h.put(new Integer(intStart), ":");
		}

		if (s.indexOf("年") != -1)
			h.put(new Integer(s.indexOf("年")), "年");
		if (s.indexOf("月") != -1)
			h.put(new Integer(s.indexOf("月")), "月");
		if (s.indexOf("日") != -1)
			h.put(new Integer(s.indexOf("日")), "日");
		if (s.indexOf("时") != -1)
			h.put(new Integer(s.indexOf("时")), "时");
		if (s.indexOf("分") != -1)
			h.put(new Integer(s.indexOf("分")), "分");
		if (s.indexOf("秒") != -1)
			h.put(new Integer(s.indexOf("秒")), "秒");
		int i = 0;
		while (h.size() != 0) {
			Enumeration e = h.keys();
			int n = 0;
			while (e.hasMoreElements()) {
				i = ((Integer) e.nextElement()).intValue();
				if (i >= n)
					n = i;
			}
			String temp = (String) h.get(new Integer(n));
			h.remove(new Integer(n));
			javaFormat = temp + javaFormat;
		}
		SimpleDateFormat df = new SimpleDateFormat(javaFormat);
		java.util.Date myDate = new java.util.Date();
		try {
			myDate = df.parse(strDate);
		} catch (Exception e) {
			return null;
		}
		return myDate;
	}

	public static java.sql.Date stringToSqlDate(String strDate, String oracleFormat) {
		if (strDate == null)
			return null;
		Hashtable h = new Hashtable();
		String javaFormat = new String();
		String s = oracleFormat.toLowerCase();
		if (s.indexOf("yyyy") != -1)
			h.put(new Integer(s.indexOf("yyyy")), "yyyy");
		else if (s.indexOf("yy") != -1)
			h.put(new Integer(s.indexOf("yy")), "yy");
		if (s.indexOf("mm") != -1)
			h.put(new Integer(s.indexOf("mm")), "MM");
		if (s.indexOf("dd") != -1)
			h.put(new Integer(s.indexOf("dd")), "dd");
		if (s.indexOf("hh24") != -1)
			h.put(new Integer(s.indexOf("hh24")), "HH");
		if (s.indexOf("mi") != -1)
			h.put(new Integer(s.indexOf("mi")), "mm");
		if (s.indexOf("ss") != -1)
			h.put(new Integer(s.indexOf("ss")), "ss");
		for (int intStart = 0; s.indexOf("-", intStart) != -1; intStart++) {
			intStart = s.indexOf("-", intStart);
			h.put(new Integer(intStart), "-");
		}

		for (int intStart = 0; s.indexOf("/", intStart) != -1; intStart++) {
			intStart = s.indexOf("/", intStart);
			h.put(new Integer(intStart), "/");
		}

		for (int intStart = 0; s.indexOf(" ", intStart) != -1; intStart++) {
			intStart = s.indexOf(" ", intStart);
			h.put(new Integer(intStart), " ");
		}

		for (int intStart = 0; s.indexOf(":", intStart) != -1; intStart++) {
			intStart = s.indexOf(":", intStart);
			h.put(new Integer(intStart), ":");
		}

		if (s.indexOf("年") != -1)
			h.put(new Integer(s.indexOf("年")), "年");
		if (s.indexOf("月") != -1)
			h.put(new Integer(s.indexOf("月")), "月");
		if (s.indexOf("日") != -1)
			h.put(new Integer(s.indexOf("日")), "日");
		if (s.indexOf("时") != -1)
			h.put(new Integer(s.indexOf("时")), "时");
		if (s.indexOf("分") != -1)
			h.put(new Integer(s.indexOf("分")), "分");
		if (s.indexOf("秒") != -1)
			h.put(new Integer(s.indexOf("秒")), "秒");
		int i = 0;
		while (h.size() != 0) {
			Enumeration e = h.keys();
			int n = 0;
			while (e.hasMoreElements()) {
				i = ((Integer) e.nextElement()).intValue();
				if (i >= n)
					n = i;
			}
			String temp = (String) h.get(new Integer(n));
			h.remove(new Integer(n));
			javaFormat = temp + javaFormat;
		}
		SimpleDateFormat df = new SimpleDateFormat(javaFormat);
		java.util.Date myDate = new java.util.Date();
		try {
			myDate = df.parse(strDate);
		} catch (Exception e) {
			return null;
		}
		return new java.sql.Date(myDate.getTime());
	}

	public static String dateToString(java.util.Date d, String format) {
		if (d == null)
			return "";
		Hashtable h = new Hashtable();
		String javaFormat = new String();
		String s = format.toLowerCase();
		if (s.indexOf("yyyy") != -1)
			h.put(new Integer(s.indexOf("yyyy")), "yyyy");
		else if (s.indexOf("yy") != -1)
			h.put(new Integer(s.indexOf("yy")), "yy");
		if (s.indexOf("mm") != -1)
			h.put(new Integer(s.indexOf("mm")), "MM");
		if (s.indexOf("dd") != -1)
			h.put(new Integer(s.indexOf("dd")), "dd");
		if (s.indexOf("hh24") != -1)
			h.put(new Integer(s.indexOf("hh24")), "HH");
		if (s.indexOf("mi") != -1)
			h.put(new Integer(s.indexOf("mi")), "mm");
		if (s.indexOf("ss") != -1)
			h.put(new Integer(s.indexOf("ss")), "ss");
		for (int intStart = 0; s.indexOf("-", intStart) != -1; intStart++) {
			intStart = s.indexOf("-", intStart);
			h.put(new Integer(intStart), "-");
		}

		for (int intStart = 0; s.indexOf("/", intStart) != -1; intStart++) {
			intStart = s.indexOf("/", intStart);
			h.put(new Integer(intStart), "/");
		}

		for (int intStart = 0; s.indexOf(" ", intStart) != -1; intStart++) {
			intStart = s.indexOf(" ", intStart);
			h.put(new Integer(intStart), " ");
		}

		for (int intStart = 0; s.indexOf(":", intStart) != -1; intStart++) {
			intStart = s.indexOf(":", intStart);
			h.put(new Integer(intStart), ":");
		}

		if (s.indexOf("年") != -1)
			h.put(new Integer(s.indexOf("年")), "年");
		if (s.indexOf("月") != -1)
			h.put(new Integer(s.indexOf("月")), "月");
		if (s.indexOf("日") != -1)
			h.put(new Integer(s.indexOf("日")), "日");
		if (s.indexOf("时") != -1)
			h.put(new Integer(s.indexOf("时")), "时");
		if (s.indexOf("分") != -1)
			h.put(new Integer(s.indexOf("分")), "分");
		if (s.indexOf("秒") != -1)
			h.put(new Integer(s.indexOf("秒")), "秒");
		int i = 0;
		while (h.size() != 0) {
			Enumeration e = h.keys();
			int n = 0;
			while (e.hasMoreElements()) {
				i = ((Integer) e.nextElement()).intValue();
				if (i >= n)
					n = i;
			}
			String temp = (String) h.get(new Integer(n));
			h.remove(new Integer(n));
			javaFormat = temp + javaFormat;
		}
		SimpleDateFormat df = new SimpleDateFormat(javaFormat, new DateFormatSymbols());
		return df.format(d);
	}

	public static String sqlDateToString(java.sql.Date d, String format) {
		if (d == null)
			return "";
		Hashtable h = new Hashtable();
		String javaFormat = new String();
		String s = format.toLowerCase();
		if (s.indexOf("yyyy") != -1)
			h.put(new Integer(s.indexOf("yyyy")), "yyyy");
		else if (s.indexOf("yy") != -1)
			h.put(new Integer(s.indexOf("yy")), "yy");
		if (s.indexOf("mm") != -1)
			h.put(new Integer(s.indexOf("mm")), "MM");
		if (s.indexOf("dd") != -1)
			h.put(new Integer(s.indexOf("dd")), "dd");
		if (s.indexOf("hh24") != -1)
			h.put(new Integer(s.indexOf("hh24")), "HH");
		if (s.indexOf("mi") != -1)
			h.put(new Integer(s.indexOf("mi")), "mm");
		if (s.indexOf("ss") != -1)
			h.put(new Integer(s.indexOf("ss")), "ss");
		for (int intStart = 0; s.indexOf("-", intStart) != -1; intStart++) {
			intStart = s.indexOf("-", intStart);
			h.put(new Integer(intStart), "-");
		}

		for (int intStart = 0; s.indexOf("/", intStart) != -1; intStart++) {
			intStart = s.indexOf("/", intStart);
			h.put(new Integer(intStart), "/");
		}

		for (int intStart = 0; s.indexOf(" ", intStart) != -1; intStart++) {
			intStart = s.indexOf(" ", intStart);
			h.put(new Integer(intStart), " ");
		}

		for (int intStart = 0; s.indexOf(":", intStart) != -1; intStart++) {
			intStart = s.indexOf(":", intStart);
			h.put(new Integer(intStart), ":");
		}

		if (s.indexOf("年") != -1)
			h.put(new Integer(s.indexOf("年")), "年");
		if (s.indexOf("月") != -1)
			h.put(new Integer(s.indexOf("月")), "月");
		if (s.indexOf("日") != -1)
			h.put(new Integer(s.indexOf("日")), "日");
		if (s.indexOf("时") != -1)
			h.put(new Integer(s.indexOf("时")), "时");
		if (s.indexOf("分") != -1)
			h.put(new Integer(s.indexOf("分")), "分");
		if (s.indexOf("秒") != -1)
			h.put(new Integer(s.indexOf("秒")), "秒");
		int i = 0;
		while (h.size() != 0) {
			Enumeration e = h.keys();
			int n = 0;
			while (e.hasMoreElements()) {
				i = ((Integer) e.nextElement()).intValue();
				if (i >= n)
					n = i;
			}
			String temp = (String) h.get(new Integer(n));
			h.remove(new Integer(n));
			javaFormat = temp + javaFormat;
		}
		SimpleDateFormat df = new SimpleDateFormat(javaFormat, new DateFormatSymbols());
		return df.format(d);
	}

	/**
	 * 得到当前日期(java.sql.Date类型)，注意：没有时间，只有日期
	 *
	 * @return 当前日期
	 */
	public static java.sql.Date getDate() {
		Calendar oneCalendar = Calendar.getInstance();
		return getDate(oneCalendar.get(Calendar.YEAR), oneCalendar.get(Calendar.MONTH) + 1,
				oneCalendar.get(Calendar.DATE));
	}

	/**
	 * 根据所给年、月、日，得到日期(java.sql.Date类型)，注意：没有时间，只有日期。 年、月、日不合法，会抛IllegalArgumentException(不需要catch)
	 *
	 * @param yyyy
	 *            4位年
	 * @param MM
	 *            月
	 * @param dd
	 *            日
	 * @return 日期
	 */
	public static java.sql.Date getDate(int yyyy, int MM, int dd) {
		if (!verityDate(yyyy, MM, dd))
			throw new IllegalArgumentException("This is illegimate date!");

		Calendar oneCalendar = Calendar.getInstance();
		oneCalendar.clear();
		oneCalendar.set(yyyy, MM - 1, dd);
		return new java.sql.Date(oneCalendar.getTime().getTime());
	}

	/**
	 * 根据所给年、月、日，检验是否为合法日期。
	 *
	 * @param yyyy
	 *            4位年
	 * @param MM
	 *            月
	 * @param dd
	 *            日
	 * @return
	 */
	public static boolean verityDate(int yyyy, int MM, int dd) {
		boolean flag = false;

		if (MM >= 1 && MM <= 12 && dd >= 1 && dd <= 31) {
			if (MM == 4 || MM == 6 || MM == 9 || MM == 11) {
				if (dd <= 30)
					flag = true;
			} else if (MM == 2) {
				if (yyyy % 100 != 0 && yyyy % 4 == 0 || yyyy % 400 == 0) {
					if (dd <= 29)
						flag = true;
				} else if (dd <= 28)
					flag = true;

			} else
				flag = true;

		}
		return flag;
	}

	public static String getDate(java.util.Date d) {
		return dateToString(d, "yyyy-MM-dd");
	}

	public static String getTime(java.util.Date d) {
		return dateToString(d, "HH24:MI:SS");
	}

	public static String getDateTime(java.util.Date d) {
		return dateToString(d, "YYYY-MM-DD HH24:MI:SS");
	}

	public static boolean yearMonthGreatEqual(String s1, String s2) {
		String temp1 = s1.substring(0, 4);
		String temp2 = s2.substring(0, 4);
		String temp3 = s1.substring(4, 6);
		String temp4 = s2.substring(4, 6);
		if (Integer.parseInt(temp1) > Integer.parseInt(temp2))
			return true;
		if (Integer.parseInt(temp1) == Integer.parseInt(temp2))
			return Integer.parseInt(temp3) >= Integer.parseInt(temp4);
		else
			return false;
	}

	public static boolean yearMonthGreater(String s1, String s2) {
		String temp1 = s1.substring(0, 4);
		String temp2 = s2.substring(0, 4);
		String temp3 = s1.substring(4, 6);
		String temp4 = s2.substring(4, 6);
		if (Integer.parseInt(temp1) > Integer.parseInt(temp2))
			return true;
		if (Integer.parseInt(temp1) == Integer.parseInt(temp2))
			return Integer.parseInt(temp3) > Integer.parseInt(temp4);
		else
			return false;
	}

	public static String getOracleFormatDateStr(java.util.Date d) {
		return dateToString(d, "YYYY-MM-DD HH24:MI:SS");
	}

	public static String getLastDay(String term) {
		int getYear = Integer.parseInt(term.substring(0, 4));
		int getMonth = Integer.parseInt(term.substring(4, 6));
		String getLastDay = "";
		if (getMonth == 2) {
			if (getYear % 4 == 0 && getYear % 100 != 0 || getYear % 400 == 0)
				getLastDay = "29";
			else
				getLastDay = "28";
		} else if (getMonth == 4 || getMonth == 6 || getMonth == 9 || getMonth == 11)
			getLastDay = "30";
		else
			getLastDay = "31";
		return String.valueOf(getYear) + "年" + String.valueOf(getMonth) + "月" + getLastDay + "日";
	}

	public static String getMonthBetween(String strDateBegin, String strDateEnd) {
		try {
			String strOut;
			if (strDateBegin.equals("") || strDateEnd.equals("") || strDateBegin.length() != 6
					|| strDateEnd.length() != 6) {
				strOut = "";
			} else {
				int intMonthBegin = Integer.parseInt(strDateBegin.substring(0, 4)) * 12
						+ Integer.parseInt(strDateBegin.substring(4, 6));
				int intMonthEnd = Integer.parseInt(strDateEnd.substring(0, 4)) * 12
						+ Integer.parseInt(strDateEnd.substring(4, 6));
				strOut = String.valueOf(intMonthBegin - intMonthEnd);
			}
			return strOut;
		} catch (Exception e) {
			return "0";
		}
	}

	public static String getStrHaveAcross(String strDate) {
		try {
			return strDate.substring(0, 4) + "-" + strDate.substring(4, 6) + "-" + strDate.substring(6, 8);
		} catch (Exception e) {
			return strDate;
		}
	}

	public static String getFirstDayOfNextMonth() {
		String strToday = getCurrentDate_String();
		return increaseYearMonth(strToday.substring(0, 6)) + "01";
	}

	public static String getYearAndMonth(String yearMonth) {
		if (yearMonth == null)
			return "";
		String ym = yearMonth.trim();
		if (6 != ym.length()) {
			return ym;
		} else {
			String year = ym.substring(0, 4);
			String month = ym.substring(4);
			return year + "年" + month + "月";
		}
	}

	public static String month2YearMonth(String month) {
		String yearMonth = "";
		int smonth = 0;
		int year = 0;
		int rmonth = 0;
		if (month == null || "0".equals(month) || "".equals(month.trim()))
			return "0月";
		smonth = Integer.parseInt(month);
		year = smonth / 12;
		rmonth = smonth % 12;
		if (year > 0)
			yearMonth = year + "年";
		if (rmonth > 0)
			yearMonth = yearMonth + rmonth + "个月";
		return yearMonth;
	}

	public static String getYearMonthByMonth(String month) {
		if (month == null)
			return null;
		String ym = month.trim();
		if (6 != ym.length()) {
			return ym;
		} else {
			String year = ym.substring(0, 4);
			// String month1 = ym.substring(4);
			return year + "年" + month + "月";
		}
	}

	public static java.util.Date increaseMonth(java.util.Date date, int intBetween) {
		Calendar calo = Calendar.getInstance();
		calo.setTime(date);
		calo.add(2, intBetween);
		return calo.getTime();
	}

	public static java.util.Date increaseYear(java.util.Date date, int intBetween) {
		Calendar calo = Calendar.getInstance();
		calo.setTime(date);
		calo.add(1, intBetween);
		return calo.getTime();
	}

	/**
	 * 按照设置的时间间隔（例如5分），将输入的时间转换为从0：00开始的编号
	 *
	 * @param date
	 *            日期
	 * @param timePeriod
	 *            时间间隔
	 * @return 按照设置的时间间隔，将输入的时间转换为从0：00开始的编号
	 */
	public static int convertTimeToIndex(Date date, int timePeriod) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minitue = calendar.get(Calendar.MINUTE);
		if (timePeriod <= 0) {
			timePeriod = TIME_PERIOD_IN_MINUTES;
		}
		return (hour * 60 + minitue) / timePeriod + 1;
	}

	/**
	 * 根据中国标准时间（CST）获取世界标准时间（UTC）
	 *
	 * @return 世界标准时间毫秒数
	 */
	public static long getUTCTimeBasingCSTTime() {
		long utcTime = 0;

		// // 1、取得本地时间：
		// java.util.Calendar cal = java.util.Calendar.getInstance();
		//
		// // 2、取得时间偏移量：
		// int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
		//
		// // 3、取得夏令时差：
		// int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
		//
		// // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
		// cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));

		// utcTime = cal.getTimeInMillis();

		Calendar curCal = Calendar.getInstance();
		TimeZone currentTimeZone = curCal.getTimeZone();// 保存原来的时区

		System.err.println("本地时区：" + currentTimeZone.getID());

		TimeZone utcTimeZone = TimeZone.getTimeZone("GMT");

		Calendar utcCal = (Calendar) curCal.clone(); // 使用GMT时间重新构造日历对象
		curCal.setTimeZone(utcTimeZone);// 将时区转换到GMT（注意此句如果定义在上一行前面，则无法正常获取到UTC时间）

		utcCal.set(curCal.get(Calendar.YEAR), curCal.get(Calendar.MONTH), curCal.get(Calendar.DATE),
				curCal.get(Calendar.HOUR_OF_DAY), curCal.get(Calendar.MINUTE), curCal.get(Calendar.SECOND));
		utcCal.set(Calendar.MILLISECOND, curCal.get(Calendar.MILLISECOND));

		// System.err.println(utcCal.getTime());

		utcTime = utcCal.getTime().getTime();

		curCal.setTimeZone(currentTimeZone);// 恢复到原来时区

		return utcTime;
	}

	/**
	 * 根据世界标准时间（UTC）计算中国标准时间（CST）
	 *
	 * @return 中国标准时间毫秒数
	 */
	public static long getCSTTimeBasingUTCTime(long utcTime) {
		long cstTime = 0;

		// // 1、取得本地时间：
		// Calendar cal = Calendar.getInstance();
		//
		// // 2、取得时间偏移量：
		// int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
		//
		// // 3、取得夏令时差：
		// int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
		//
		// // 4、将本地时间设置为utcTime：
		// cal.setTimeInMillis(utcTime);
		//
		// // 5、给utcTime添加差量，即可以将UTC时间转化为本地时间：
		// cal.add(java.util.Calendar.MILLISECOND, +(zoneOffset + dstOffset));
		//
		// cstTime = cal.getTimeInMillis();

		Calendar utcCal = Calendar.getInstance();

		TimeZone currentTimeZone = utcCal.getTimeZone();// 保存当前时区
		// System.err.println(currentTimeZone.getID());
		TimeZone utcTimeZone = TimeZone.getTimeZone("GMT");
		utcCal.setTimeZone(utcTimeZone);

		Calendar curCal = (Calendar) utcCal.clone(); // 使用GMT时间重新构造日历对象
		utcCal.setTimeZone(currentTimeZone);// 将时区转换到GMT下（注意此句如果定义在上一行前面，则无法正常获取到UTC时间）
		utcCal.setTimeInMillis(utcTime);

		curCal.set(utcCal.get(Calendar.YEAR), utcCal.get(Calendar.MONTH), utcCal.get(Calendar.DATE),
				utcCal.get(Calendar.HOUR_OF_DAY), utcCal.get(Calendar.MINUTE), utcCal.get(Calendar.SECOND));
		curCal.set(Calendar.MILLISECOND, utcCal.get(Calendar.MILLISECOND));

		System.err.println("同步时区：" + curCal.getTimeZone().getID());

		// System.err.println(utcCal.getTime());

		cstTime = curCal.getTime().getTime();

		utcCal.setTimeZone(currentTimeZone);// 恢复到原来时区

		return cstTime;
	}

	/**
	 * @param range
	 *            : 表示距当年前后的范围;<br>
	 *            如当前年为2011，range等于2，则返回（2009，2010，2011，2012，2013）;<br>
	 *            若range小于等于0，返回null。
	 * @return
	 */
	public static String[] getYearRange(int range) {
		String[] array = null;

		int arrayLen = 0;
		if (range > 0) {
			arrayLen = range * 2 + 1;
			array = new String[arrayLen];
		}

		int currentYear = DateTimeUtil.convertDateToYear(new Date());
		int baseYear = currentYear - range;

		if (range > 0) {
			for (int i = 0; i < range * 2 + 1; i++) {
				array[i] = String.valueOf(baseYear + i);
			}
		}

		return array;
	}

	/**
	 * 获取一年包含的月份
	 *
	 * @return
	 */
	public static String[] getMonthRange() {
		return DateTimeUtil.MONTH_12;
	}

	/**
	 * 取得指定月份包含的天数
	 *
	 * @param month
	 *            ：月份
	 * @return
	 */
	public static String[] getDayRange(int year, int month) {
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return DateTimeUtil.DAY_31;
		case 4:
		case 6:
		case 9:
		case 11:
			return DateTimeUtil.DAY_30;
		case 2:
			if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
				return DateTimeUtil.DAY_29;
			} else {
				return DateTimeUtil.DAY_28;
			}

		default:
			return null;
		}
	}

}
