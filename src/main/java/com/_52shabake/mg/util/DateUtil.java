package com._52shabake.mg.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	/**
	 * 得到两个时间之间的秒数
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static long getBetweenSecond(Date d1, Date d2) {
		return Math.abs((d1.getTime() - d2.getTime()) / 1000);
	}


	/**
	 * 把传入的时间设置为起始时间,把时分秒设置为0
	 */
	public static Date getBeginDate(Date current) {
		Calendar c = Calendar.getInstance();
		c.setTime(current);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
				c.get(Calendar.DATE), 0, 0, 0);
		return c.getTime();
	}

	/**
	 * 把传入的时间设置为结束时间: 1):把时分秒设置为0 2):把天加1. 3):把秒减1.
	 */
	public static Date getEndDate(Date current) {
		Calendar c = Calendar.getInstance();
		c.setTime(current);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
				c.get(Calendar.DATE), 0, 0, 0);
		c.add(Calendar.DATE, 1);
		c.add(Calendar.SECOND, -1);
		return c.getTime();
	}

	public static void main(String[] args) {
		System.out.println(getBeginDate(new Date()).toLocaleString());
		System.out.println(getEndDate(new Date()).toLocaleString());
	}
}
