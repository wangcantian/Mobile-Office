package com.mo.mobileoffice.common.tool;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;

@SuppressLint("SimpleDateFormat")
public class StringTool {
	private static final SimpleDateFormat mDataFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat mDataFormat1 = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static final SimpleDateFormat mDataFormat2 = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	private static final SimpleDateFormat mDataFormat3 = new SimpleDateFormat(
			"yyyy-MM");

	/**
	 * 将字符串转成日期类型
	 */
	public static Date StringtoData(String date) {
		try {
			return mDataFormat.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 将日期类型转成字符串（yyyyMMddHHmmss）
	 */
	public static String DataToString3(Date date) {
		return mDataFormat2.format(date);
	}

	/**
	 * 将日期类型转成字符串（yyyy-MM-dd）
	 */
	public static String DataToString2(Date date) {
		return mDataFormat1.format(date);
	}

	/**
	 * 将日期类型转成字符串（yyyy-MM）
	 */
	public static String DataToString4(Date date) {
		return mDataFormat3.format(date);
	}

	/**
	 * 将日期类型转成字符串（yyyy-MM-dd HH:mm:ss）
	 */
	public static String DateToString1(Date date) {
		return mDataFormat.format(date);
	}

	/**
	 * 对开始时间和结束时间进行比较
	 * 
	 * @throws ParseException
	 */
	public static boolean compareTime(String startTime, String endTime)
			throws ParseException {
		SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		long start = mDateFormat.parse(startTime).getTime();
		long end = mDateFormat.parse(endTime).getTime();
		return start < end;
	}

	/**
	 * 对开始时间和结束时间进行比较
	 * 
	 * @throws ParseException
	 */
	public static boolean compareTime1(String startTime, String endTime)
			throws ParseException {
		SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		long start = mDateFormat.parse(startTime).getTime();
		long end = mDateFormat.parse(endTime).getTime();
		return start < end;
	}

	/**
	 * 将数字控制为两位数
	 */
	public static String to2DigitNum(int num) {
		return num < 10 ? "0" + num : num + "";
	}
	
	/**
	 * 将年月日转成String
	 */
	public static String transformDate(int year, int month, int day) {
		StringBuilder builder = new StringBuilder();
		builder.append(year + "-");
		builder.append(month < 10 ? "0" + month : month);
		builder.append(day < 10 ? "-0" + day : "-" + day);
		return builder.toString();
	}

	/**
	 * 将年月转成String
	 */
	@SuppressWarnings("deprecation")
	public static String transformDate1(int value) {
		Date date = new Date();
		int year = date.getYear() + 1900;
		int month = date.getMonth() + 1;
		if (month > value) {
			month = month - value;
		} else {
			year = year - 1;
			month = 12 - (value - month);
		}
		StringBuilder builder = new StringBuilder();
		builder.append(year + "-");
		builder.append(month < 10 ? "0" + month : month);
		return builder.toString();
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}

	/**
	 * 判断字符串是否不为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isEmpty2(String str) {
		if (isEmpty(str))
			return true;

		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 格式化boolean字符串
	 * 
	 * @param booleanStr
	 * @return
	 */
	public static boolean formatBoolean(String booleanStr) {
		if ("true".equalsIgnoreCase(booleanStr)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查邮箱输入格式是否正确
	 */
	public static boolean checkEmailInput(String email) {
		Pattern pattern = Pattern
				.compile("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");
		return pattern.matcher(email).matches();
	}

	/**
	 * 检查手机输入格式是否正确
	 */
	public static boolean checkMobileInput(String mobile) {
		Pattern pattern = Pattern.compile("^[1][358][0-9]{9}$");
		return pattern.matcher(mobile).matches();
	}

	/**
	 * 检查密码位数是否准确
	 */

	public static boolean checkPasswordInput(String password) {
		int value = password.length();
		if (value >= 6 && value <= 10) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 拼接url
	 */
	public static String joinUrlString(String[] key, String[] value) {
		if (key.length == value.length) {
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < key.length; i++) {
				buffer.append("&");
				buffer.append(key[i]);
				buffer.append("=");
				buffer.append(value[i]);
			}
			return buffer.toString();
		}
		return null;
	}

	/**
	 * 将byte转化为mb或kb
	 */
	public static String bytes2kb(long bytes) {
		BigDecimal filesize = new BigDecimal(bytes);
		BigDecimal megabyte = new BigDecimal(1024 * 1024);
		float returnvalue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)
				.floatValue();
		if (returnvalue > 1) {
			return (returnvalue + "MB");
		}
		BigDecimal kilobyte = new BigDecimal(1024);
		returnvalue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP)
				.floatValue();
		return (returnvalue + "KB");
	}
}
