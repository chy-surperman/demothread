package com.data.util;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {

	/**
	 * 根据字符串时间生成Date
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(String time) throws ParseException{

		 return getDateFormat(DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()).parse(time.replace(".0", ""));
	}


	/**
	 * 根据字符串时间生成Date
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(String time,DateStyle dateStyle) throws ParseException{

		 return getDateFormat(dateStyle.getValue()).parse(time.replace(".0", ""));
	}
	/**
	 * 根据字符串时间生成Date
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateByString(String time,DateStyle dateStyle){

		 try {
			return getDateFormat(dateStyle.getValue()).parse(time.replace(".0", ""));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	}
	/**
     * 获取SimpleDateFormat
     * @param parttern 日期格式
     * @return SimpleDateFormat对象
     * @throws RuntimeException 异常：非法日期格式
     */
    private static SimpleDateFormat getDateFormat(String parttern) throws RuntimeException {
        return new SimpleDateFormat(parttern);
    }

	/**
	 * 获取当前时间
	 * @return 当前时间
	 */
	public static Date getDate(){
		return new Date();
	}

	public static Date getDate(DateStyle dateStyle) {
		try {
			return getDate(DateUtil.getDateByDs(new Date(), dateStyle), dateStyle);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取当前时间unix时间戳
	 * @return
	 */
	public static Integer getUnixTimestamp(){
		return new Long(System.currentTimeMillis() / 1000).intValue();
	}

	/**
	 * 获取一个token失效时间
	 * @return
	 */
	public static int getTokenFailTimestamp(int day){
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+day);
		return new Long(calendar.getTimeInMillis() / 1000).intValue();
	}

	/**
	 * 返回 YYYY-MM-DD HH:MM:SS 当前格式的日期
	 * @return
	 */
	public static String getDateSimple(){
		return getDateFormat(DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()).format(getDate());
	}

	/**
	 * 根据时期格式ds返回当前格式的日期
	 * @return
	 */
	public static String getDateByDs(DateStyle ds){
		return getDateFormat(ds.getValue()).format(getDate());
	}
	/**
	 * 判断before时间是否在 after之前
	 * @param brefor
	 * @param after
	 * @return boolean
	 */
	public static boolean before(Date brefor,Date after){
		return brefor.before(after);
	}


	/**
	 * 判断before时间是否在 after之前
	 * @param brefor
	 * @param after
	 * @return boolean
	 */
	public static boolean before(String brefor,String after,DateStyle ds){
		try {
			Date _brefor = getDateFormat(ds.getValue()).parse(brefor);
			Date _after = getDateFormat(ds.getValue()).parse(after);
			return before(_brefor,_after);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	/**
	 * 根据时间 和 日期格式   返回字符串
	 * @param date  日期
	 * @param ds    日期格式
	 * @return      格式化之后的字符串
	 */
	public static String getDateByDs(Date date,DateStyle ds){
		return getDateFormat(ds.getValue()).format(date);
	}

	public static String getDateByTimestamp(int timestamp){
		System.out.println(timestamp * 1000);
		return DateUtil.getDateByDs(new Date(new Long(timestamp+"000")), DateStyle.YYYY_MM_DD_HH_MM_SS);
	}


	/**
	 * 获取当前日期所在的星期
	 * @return 今天是星期几
	 */
	public static Week getWeek(){
		return getWeek(new Date());
	}


	/**
     * 获取日期的星期。失败返回null。
     * @param date 日期
     * @return 星期
     */
    public static Week getWeek(Date date) {
        Week week = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        switch (weekNumber) {
        case 0:
            week = Week.SUNDAY;
            break;
        case 1:
            week = Week.MONDAY;
            break;
        case 2:
            week = Week.TUESDAY;
            break;
        case 3:
            week = Week.WEDNESDAY;
            break;
        case 4:
            week = Week.THURSDAY;
            break;
        case 5:
            week = Week.FRIDAY;
            break;
        case 6:
            week = Week.SATURDAY;
            break;
        }
        return week;
    }

    /**
     * 当前时间加减分钟计算
     * @param ago代表分钟数 可以为负数，负数代表当前时间之前的时间，正数代表当前时间之后的时间
     * @return 负数代表当前时间之前的时间，正数代表当前时间之后的时间 ,时间格式为yyyy-MM-dd HH:mm:ss
     */
    public static String getAgoTime(int ago){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, ago);

        return getDateFormat(DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()).format(calendar.getTime());

    }
    /**
     * 当前时间加减分钟计算
     * @param ago代表分钟数 可以为负数，负数代表当前时间之前的时间，正数代表当前时间之后的时间
     * @return 负数代表当前时间之前的时间，正数代表当前时间之后的时间 ,时间格式为yyyy-MM-dd HH:mm:ss
     */
    public static Date getAgoTimeByMinute(int ago){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, ago);

        return calendar.getTime();

    }
    /**
     * 当前时间加减分钟计算
     * @param ago代表分钟数 可以为负数，负数代表当前时间之前的时间，正数代表当前时间之后的时间
     * @return 负数代表当前时间之前的时间，正数代表当前时间之后的时间 ,时间格式为yyyy-MM-dd HH:mm:ss
     */
    public static Date getAgoTimeToDate(int ago){
    	Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, ago);
        return calendar.getTime();
    }

    /**
     * 当前日期按年份计算加减
     * @param day
     * @return
     */
    public static String getAgoDay(int day){
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.DAY_OF_YEAR, day);
    	return getDateFormat(DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()).format(calendar.getTime());
    }

    /**
     * 当前日期按年份计算加减
     * @param day
     * @return
     */
    public static Date getAgoDayToDate(int day){
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.DAY_OF_YEAR, day);
    	return calendar.getTime();
    }

    /**
     * 当前日期按年份计算加减
     * @param day
     * @return
     */
    public static String getAgoDay(int day,DateStyle dateStyle){
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.DAY_OF_YEAR, day);
    	return getDateFormat(dateStyle.getValue()).format(calendar.getTime());
    }

    /**
     * 当前日期按年份计算加减
     * @param day
     * @return
     */
    public static int getAgoDayUnixTimestrap(int day){
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.DAY_OF_YEAR, day);
    	return new Long(calendar.getTimeInMillis() / 1000).intValue();
    }

    /**
     * 当前时间加减分钟计算
     * @param ago代表分钟数 可以为负数，负数代表当前时间之前的时间，正数代表当前时间之后的时间
     * @return 负数代表当前时间之前的时间，正数代表当前时间之后的时间 ,时间格式为yyyy-MM-dd HH:mm:ss
     */
    public static String getAgoTimeToFormat(int ago){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, ago);
        return getDateFormat(DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()).format(calendar.getTime());
    }

    /**
     * 当前时间加减分钟计算
     * @param ago代表分钟数 可以为负数，负数代表当前时间之前的时间，正数代表当前时间之后的时间
     * @return 负数代表当前时间之前的时间，正数代表当前时间之后的时间 ,时间格式为yyyy-MM-dd HH:mm:ss
     */
    public static String getAgoTime(int ago,DateStyle dateStyle){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, ago);
        return getDateFormat(dateStyle.getValue()).format(calendar.getTime());
    }

    /**
     * yyyy年mm月dd日
     * @param date
     * @return yyyy年mm月dd日
     */
    public static String getYearMoothDay(Date date){
    	return getDateFormat(DateStyle.YYYY_MM_DD_CN.getValue()).format(date);
    }

    public static String getAgoTimeDay(int day,String date) throws ParseException{
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(DateUtil.getDate(date));
    	calendar.add(Calendar.DATE, day);
    	return getYearMoothDay(calendar.getTime());
    }

    /**
     * 年份加减
     * @param year
     * @return
     */
    public static String getAgoYear(int year){
    	 Calendar calendar = Calendar.getInstance();
         calendar.add(Calendar.YEAR, year);
         return getDateFormat(DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()).format(calendar.getTime());
    }

    /**
     * 年份加减
     * @param year
     * @return
     * @throws ParseException
     */
    public static String getAgoYear(int year,String date) {
         try {
			DateFormat afterDate = new SimpleDateFormat(DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
			 Date after = afterDate.parse(date);
			 Calendar calendar = afterDate.getCalendar();
			 calendar.setTime(after);
			 calendar.add(Calendar.YEAR, year);
			 return getDateFormat(DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()).format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
    }

    /**
     * 年份加减
     * @param year
     * @return
     * @throws ParseException
     */
    public static String getAgoDay(int day,String date) {
         try {
			DateFormat afterDate = new SimpleDateFormat(DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
			 Date after = afterDate.parse(date);
			 Calendar calendar = afterDate.getCalendar();
			 calendar.setTime(after);
			 calendar.add(Calendar.DAY_OF_YEAR, day);
			 return getDateFormat(DateStyle.YYYY_MM_DD_HH_MM_SS.getValue()).format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
    }

    /**
     * 年份加减
     * @param year
     * @return
     * @throws ParseException
     */
    public static String getAgoYear(int year,String date,DateStyle stye) {
         try {
			DateFormat afterDate = new SimpleDateFormat(stye.getValue());
			 Date after = afterDate.parse(date);
			 Calendar calendar = afterDate.getCalendar();
			 calendar.setTime(after);
			 calendar.add(Calendar.YEAR, year);
			 return getDateFormat(stye.getValue()).format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
    }

    /**
     * 两个日期相减 afterTime - beforeTime
     * @param afterTime
     * @param beforeTime
     * @return
     */
    @SuppressWarnings("deprecation")
	public static int dateSubToYear(String afterTime,String beforeTime){
    	DateFormat afterDate = new SimpleDateFormat(DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
    	DateFormat beforDate = new SimpleDateFormat(DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
    	try {
			int after = afterDate.parse(afterTime).getYear();
			int before = beforDate.parse(beforeTime).getYear();
			return after - before;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;
    }


    /**
     * 两个日期相减 afterTime - beforeTime
     * @param afterTime
     * @param beforeTime
     * @return
     */
    @SuppressWarnings("deprecation")
	public static int dateSubToYear(Timestamp afterTime,Timestamp beforeTime){
    	return afterTime.getYear() - beforeTime.getYear();
    }

    /**
     * 判断当前系统时间是否在AfterTime之前
     * @param afterTime
     * @param style
     * @return
     */
    public static boolean beforeCurrentDate(String afterTime,DateStyle style){
    	DateFormat afterDate = new SimpleDateFormat(style.getValue());
    	try {
			Date after = afterDate.parse(afterTime);
			return after.getTime() > System.currentTimeMillis();
		} catch (ParseException e) {
			return beforeCurrentDate(afterTime, DateStyle.YYYY_MM_DD_HH_MM_SS_EN);
		}
    }

    public static long getTimestamp(String dateTime,DateStyle style){
    	if(StringUtils.isEmpty(dateTime)){
    		return System.currentTimeMillis();
    	}else{
    		DateFormat afterDate = new SimpleDateFormat(style.getValue());
        	try {
    			Date after = afterDate.parse(dateTime);
    			return after.getTime();
    		} catch (ParseException e) {
    		}
    	}
    	return System.currentTimeMillis();
    }


	public static int getDaysByYearMonth(int year, int month) {

		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	public static int daysBetween(String smdate, String bdate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	public static int daysBetweenCurrent(String afterDate,DateStyle style){
		SimpleDateFormat sdf = getDateFormat(style.getValue());
		try {
			long bDate = sdf.parse(afterDate).getTime();
			long cDate = Calendar.getInstance().getTimeInMillis();
			int between_days = (int) ((cDate - bDate) / (1000 * 60 * 60 * 24));
			return between_days;
		} catch (ParseException e) {
			daysBetweenCurrent(afterDate, DateStyle.YYYY_MM_DD_HH_MM_SS_EN);
		}
		return 0;
	}

    public static void main(String[] args) throws ParseException {

//    	String time = DateUtil.getDateByDs(new Date(new Long(1515367380) * 1000), DateStyle.HH_MM_SS);
    	String time = "19:29:00";
    	System.out.println(DateUtil.getDate(time, DateStyle.HH_MM_SS));
    	System.out.println(DateUtil.getDate(time, DateStyle.HH_MM_SS).getTime() / 1000);
	}

}
