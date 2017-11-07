package com.databps.bigdaf.core.util;

import java.text.DecimalFormat;

/**
 * 
 * desc:金额转换工具
 * <p>创建人：linquantao 创建日期：2016-1-20 </p>
 * @version V1.0
 */
public class AmountUtil {
	/**
	 * 元转分
	 * 
	 * @param s
	 *            元格式
	 * @return 分格式
	 */
	public static Integer Dollar2Cent(String s) {
		if (s == null || s.equals("")) {
			return 0;
		}
		return  Integer.valueOf(transformNumber(s, "###", 100));
	}
	
	public static Integer Dollar2Cent(Integer s) {
		if (s == null || s.equals("")) {
			return 0;
		}
		return Integer.valueOf(transformNumber(s+"", "###", 100));
	}

	/**
	 * 格式化金额为两位小数
	 * 
	 * @param s
	 * @return
	 */
	public static String formatDouble(double s) {
		DecimalFormat fmt = new DecimalFormat("#0.00");
		return fmt.format(s);
	}

	/**
	 * 分转元
	 * 
	 * @param s
	 *            分格式
	 * @return 元格式
	 */
	public static String Cent2Dollar(String s) {
		return transformNumber(s, "0.00", 0.01);
	}
	
	public static String Cent2Dollar(Integer s) {
		return transformNumber(s+"", "0.00", 0.01);
	}

	private static String transformNumber(String src, String format, double rate) {
		if (src == null || src.equals("")) {
			return "0";
		}
		return new DecimalFormat(format).format(Double.parseDouble(src) * rate);
	}

	public static void main(String[] args) {
	}
}
