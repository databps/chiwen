package com.databps.bigdaf.core.util;

import java.util.Random;
import org.apache.commons.lang3.StringUtils;


public class AppKeyUtil {
	
	
	/**
	 * 随机生成32位的appkey
	 * @return
	 */
	public static String generate() {

		String ch = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";


		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 32; i++) {
			int random = new Random().nextInt(ch.length() - 1);
			sb.append(StringUtils.substring(ch, random, random + 1));
		}
		return sb.toString();
	}

}
