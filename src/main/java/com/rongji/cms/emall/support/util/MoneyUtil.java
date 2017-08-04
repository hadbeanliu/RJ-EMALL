package com.rongji.cms.emall.support.util;

import java.math.BigDecimal;

public class MoneyUtil {
	
	private static BigDecimal D100 = new BigDecimal(100);
	
	/**
	 * 将金额从分单位转换为元单位的字符串
	 * <p>
	 * 返回科学计数的金额格式
	 * 
	 * @param money 具体金额
	 * @return 返回格式{@literal 105.01}
	 */
	public static String longToString(long money) {
		return new BigDecimal(money).divide(D100, 2, BigDecimal.ROUND_HALF_UP).toString();
	}

}
