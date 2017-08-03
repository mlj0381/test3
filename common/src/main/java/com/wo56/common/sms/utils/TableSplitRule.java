package com.wo56.common.sms.utils;

import java.util.Calendar;
import java.util.Date;

import com.framework.core.util.DateUtil;

public class TableSplitRule {

	/**
	 * 按年月分表
	 * 
	 * @return
	 */
	public static String yyyyMM() {
		Calendar now = Calendar.getInstance();
		return DateUtil.getYear(now) + DateUtil.getMonth(now);
	}

	/**
	 * 按年月分表
	 * 
	 * @param date
	 * @return
	 */
	public static String yyyyMM(Date date) {
		return DateUtil.getYear(date)
				+ DateUtil.strLen(String.valueOf(DateUtil.getMonth(date)), 2);
	}

}
