package com.s.t.m.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.time.DateFormatUtils;



/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author Bai
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}
	
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }
	
	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}
	
	/**
	 * 字符串时间转LONG
	 * @param sdate
	 * @return
	 */
	public static long string2long(String sdate){
		if(sdate.length() < 11){
			sdate = sdate + " 00:00:00";
		}
		SimpleDateFormat sdf= new SimpleDateFormat(DEFAULT_PATTERN);
		Date dt2 = null;
		try {
			dt2 = sdf.parse(sdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//继续转换得到秒数的long型
		long lTime = dt2.getTime() / 1000;
		return lTime;
	}
	
	/**
	 * LONG时间转字符串
	 * @param ldate
	 * @return
	 */
	public static String long2string(long ldate){
		SimpleDateFormat sdf= new SimpleDateFormat(DEFAULT_PATTERN);
		//前面的ldate是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
		java.util.Date dt = new Date(ldate * 1000);  
		String sDateTime = sdf.format(dt);  //得到精确到秒的表示
		if(sDateTime.endsWith("00:00:00")){
			sDateTime = sDateTime.substring(0,10);
		}
		return sDateTime;
	}
	/**
	 * 在不方便使用joda-time时，使用本类降低Date处理的复杂度与性能消耗, 封装Common Lang及移植Jodd的最常用日期方法
	 * @author Bai
	 *
	 */
	public static final class DateTimeUtils{
		public static final long MILLIS_PER_SECOND = 1000; // Number of milliseconds in a standard second.

		public static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND; // Number of milliseconds in a standard minute.

		public static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE; // Number of milliseconds in a standard hour.

		public static final long MILLIS_PER_DAY = 24 * MILLIS_PER_HOUR; // Number of milliseconds in a standard day.

		private static final int[] MONTH_LENGTH = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		//////// 日期比较 ///////////
		/**
		 * 是否同一天.
		 * 
		 * @see DateUtils#isSameDay(Date, Date)
		 */
		public static boolean isSameDay(Date date1, Date date2) {
			return DateUtils.isSameDay(date1, date2);
		}

		/**
		 * 是否同一时刻.
		 */
		public static boolean isSameTime(Date date1, Date date2) {
			// date.getMillisOf() 比date.getTime()快
			return date1.compareTo(date2) == 0;
		}

		/**
		 * 判断日期是否在范围内，包含相等的日期
		 */
		public static boolean isBetween(Date date, Date start, Date end) {
			if (date == null || start == null || end == null || start.after(end)) {
				throw new IllegalArgumentException("some date parameters is null or dateBein after dateEnd");
			}
			return !date.before(start) && !date.after(end);
		}

		//////////// 往前往后滚动时间//////////////

		/**
		 * 加一月
		 */
		public static Date addMonths(Date date, int amount) {
			return DateUtils.addMonths(date, amount);
		}

		/**
		 * 减一月
		 */
		public static Date subMonths(Date date, int amount) {
			return DateUtils.addMonths(date, -amount);
		}

		/**
		 * 加一周
		 */
		public static Date addWeeks(Date date, int amount) {
			return DateUtils.addWeeks(date, amount);
		}

		/**
		 * 减一周
		 */
		public static Date subWeeks(Date date, int amount) {
			return DateUtils.addWeeks(date, -amount);
		}

		/**
		 * 加一天
		 */
		public static Date addDays(Date date, final int amount) {
			return DateUtils.addDays(date, amount);
		}

		/**
		 * 减一天
		 */
		public static Date subDays(Date date, int amount) {
			return DateUtils.addDays(date, -amount);
		}

		/**
		 * 加一小时
		 */
		public static Date addHours(Date date, int amount) {
			return DateUtils.addHours(date, amount);
		}

		/**
		 * 减一小时
		 */
		public static Date subHours(Date date, int amount) {
			return DateUtils.addHours(date, -amount);
		}

		/**
		 * 加一分钟
		 */
		public static Date addMinutes(Date date, int amount) {
			return DateUtils.addMinutes(date, amount);
		}

		/**
		 * 减一分钟
		 */
		public static Date subMinutes(Date date, int amount) {
			return DateUtils.addMinutes(date, -amount);
		}

		/**
		 * 终于到了，续一秒.
		 */
		public static Date addSeconds(Date date, int amount) {
			return DateUtils.addSeconds(date, amount);
		}

		/**
		 * 减一秒.
		 */
		public static Date subSeconds(Date date, int amount) {
			return DateUtils.addSeconds(date, -amount);
		}

		//////////// 直接设置时间//////////////

		/**
		 * 设置年份, 公元纪年.
		 */
		public static Date setYears(Date date, int amount) {
			return DateUtils.setYears(date, amount);
		}

		/**
		 * 设置月份, 0-11.
		 */
		public static Date setMonths(Date date, int amount) {
			return DateUtils.setMonths(date, amount);
		}

		/**
		 * 设置日期, 1-31.
		 */
		public static Date setDays(Date date, int amount) {
			return DateUtils.setDays(date, amount);
		}

		/**
		 * 设置小时, 0-23.
		 */
		public static Date setHours(Date date, int amount) {
			return DateUtils.setHours(date, amount);
		}

		/**
		 * 设置分钟, 0-59.
		 */
		public static Date setMinutes(Date date, int amount) {
			return DateUtils.setMinutes(date, amount);
		}

		/**
		 * 设置秒, 0-59.
		 */
		public static Date setSeconds(Date date, int amount) {
			return DateUtils.setSeconds(date, amount);
		}

		/**
		 * 设置毫秒.
		 */
		public static Date setMilliseconds(Date date, int amount) {
			return DateUtils.setMilliseconds(date, amount);
		}

		///// 获取日期的位置//////
		/**
		 * 获得日期是一周的第几天. 已改为中国习惯，1 是Monday，而不是Sundays.
		 */
		public static int getDayOfWeek(Date date) {
			int result = get(date, Calendar.DAY_OF_WEEK);
			return result == 1 ? 7 : result - 1;
		}

		/**
		 * 获得日期是一年的第几天，返回值从1开始
		 */
		public static int getDayOfYear(Date date) {
			return get(date, Calendar.DAY_OF_YEAR);
		}

		/**
		 * 获得日期是一月的第几周，返回值从1开始.
		 * 
		 * 开始的一周，只要有一天在那个月里都算. 已改为中国习惯，1 是Monday，而不是Sunday
		 */
		public static int getWeekOfMonth(Date date) {
			return getWithMondayFirst(date, Calendar.WEEK_OF_MONTH);
		}

		/**
		 * 获得日期是一年的第几周，返回值从1开始.
		 * 
		 * 开始的一周，只要有一天在那一年里都算.已改为中国习惯，1 是Monday，而不是Sunday
		 */
		public static int getWeekOfYear(Date date) {
			return getWithMondayFirst(date, Calendar.WEEK_OF_YEAR);
		}

		private static int get(final Date date, int field) {
			Validate.notNull(date, "The date must not be null");
			Calendar cal = Calendar.getInstance();
			cal.setFirstDayOfWeek(Calendar.MONDAY);
			cal.setTime(date);

			return cal.get(field);
		}

		private static int getWithMondayFirst(final Date date, int field) {
			Validate.notNull(date, "The date must not be null");
			Calendar cal = Calendar.getInstance();
			cal.setFirstDayOfWeek(Calendar.MONDAY);
			cal.setTime(date);
			return cal.get(field);
		}

		///// 获得往前往后的日期//////

		/**
		 * 2016-11-10 07:33:23, 则返回2016-1-1 00:00:00
		 */
		public static Date beginOfYear(Date date) {
			return DateUtils.truncate(date, Calendar.YEAR);
		}

		/**
		 * 2016-11-10 07:33:23, 则返回2016-12-31 23:59:59.999
		 */
		public static Date endOfYear(Date date) {
			return new Date(nextYear(date).getTime() - 1);
		}

		/**
		 * 2016-11-10 07:33:23, 则返回2017-1-1 00:00:00
		 */
		public static Date nextYear(Date date) {
			return DateUtils.ceiling(date, Calendar.YEAR);
		}

		/**
		 * 2016-11-10 07:33:23, 则返回2016-11-1 00:00:00
		 */
		public static Date beginOfMonth(Date date) {
			return DateUtils.truncate(date, Calendar.MONTH);
		}

		/**
		 * 2016-11-10 07:33:23, 则返回2016-11-30 23:59:59.999
		 */
		public static Date endOfMonth(Date date) {
			return new Date(nextMonth(date).getTime() - 1);
		}

		/**
		 * 2016-11-10 07:33:23, 则返回2016-12-1 00:00:00
		 */
		public static Date nextMonth(Date date) {
			return DateUtils.ceiling(date, Calendar.MONTH);
		}

		/**
		 * 2017-1-20 07:33:23, 则返回2017-1-16 00:00:00
		 */
		public static Date beginOfWeek(Date date) {
			return DateUtils.truncate(DateTimeUtils.subDays(date, DateTimeUtils.getDayOfWeek(date) - 1), Calendar.DATE);
		}

		/**
		 * 2017-1-20 07:33:23, 则返回2017-1-22 23:59:59.999
		 */
		public static Date endOfWeek(Date date) {
			return new Date(nextWeek(date).getTime() - 1);
		}

		/**
		 * 2017-1-23 07:33:23, 则返回2017-1-22 00:00:00
		 */
		public static Date nextWeek(Date date) {
			return DateUtils.truncate(DateTimeUtils.addDays(date, 8 - DateTimeUtils.getDayOfWeek(date)), Calendar.DATE);
		}

		/**
		 * 2016-11-10 07:33:23, 则返回2016-11-10 00:00:00
		 */
		public static Date beginOfDate(Date date) {
			return DateUtils.truncate(date, Calendar.DATE);
		}

		/**
		 * 2017-1-23 07:33:23, 则返回2017-1-23 23:59:59.999
		 */
		public static Date endOfDate(Date date) {
			return new Date(nextDate(date).getTime() - 1);
		}

		/**
		 * 2016-11-10 07:33:23, 则返回2016-11-11 00:00:00
		 */
		public static Date nextDate(Date date) {
			return DateUtils.ceiling(date, Calendar.DATE);
		}

		/**
		 * 2016-12-10 07:33:23, 则返回2016-12-10 07:00:00
		 */
		public static Date beginOfHour(Date date) {
			return DateUtils.truncate(date, Calendar.HOUR_OF_DAY);
		}

		/**
		 * 2017-1-23 07:33:23, 则返回2017-1-23 07:59:59.999
		 */
		public static Date endOfHour(Date date) {
			return new Date(nextHour(date).getTime() - 1);
		}

		/**
		 * 2016-12-10 07:33:23, 则返回2016-12-10 08:00:00
		 */
		public static Date nextHour(Date date) {
			return DateUtils.ceiling(date, Calendar.HOUR_OF_DAY);
		}

		/**
		 * 2016-12-10 07:33:23, 则返回2016-12-10 07:33:00
		 */
		public static Date beginOfMinute(Date date) {
			return DateUtils.truncate(date, Calendar.MINUTE);
		}

		/**
		 * 2017-1-23 07:33:23, 则返回2017-1-23 07:33:59.999
		 */
		public static Date endOfMinute(Date date) {
			return new Date(nextMinute(date).getTime() - 1);
		}

		/**
		 * 2016-12-10 07:33:23, 则返回2016-12-10 07:34:00
		 */
		public static Date nextMinute(Date date) {
			return DateUtils.ceiling(date, Calendar.MINUTE);
		}

		////// 闰年及每月天数///////
		/**
		 * 是否闰年.
		 */
		public static boolean isLeapYear(Date date) {
			return isLeapYear(get(date, Calendar.YEAR));
		}

		/**
		 * 是否闰年，移植Jodd Core的TimeUtil
		 * 
		 * 参数是公元计数, 如2016
		 */
		public static boolean isLeapYear(int y) {
			boolean result = false;

			if (((y % 4) == 0) && // must be divisible by 4...
					((y < 1582) || // and either before reform year...
							((y % 100) != 0) || // or not a century...
							((y % 400) == 0))) { // or a multiple of 400...
				result = true; // for leap year.
			}
			return result;
		}

		/**
		 * 获取某个月有多少天, 考虑闰年等因数, 移植Jodd Core的TimeUtil
		 */
		public static int getMonthLength(Date date) {
			int year = get(date, Calendar.YEAR);
			int month = get(date, Calendar.MONTH);
			return getMonthLength(year, month);
		}

		/**
		 * 获取某个月有多少天, 考虑闰年等因数, 移植Jodd Core的TimeUtil
		 */
		public static int getMonthLength(int year, int month) {

			if ((month < 1) || (month > 12)) {
				throw new IllegalArgumentException("Invalid month: " + month);
			}
			if (month == 2) {
				return isLeapYear(year) ? 29 : 28;
			}

			return MONTH_LENGTH[month];
		}
	}

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
//		System.out.println(formatDate(parseDate("2010/3/6")));
//		System.out.println(getDate("yyyy年MM月dd日 E"));
//		long time = new Date().getTime()-parseDate("2012-11-19").getTime();
//		System.out.println(time/(24*60*60*1000));
	}
	
	
	
}
