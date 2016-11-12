package com.cshiji.superviki.base.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static String getDayTime(){
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		tempDate.setLenient(false);
		return tempDate.format(new java.util.Date());
	}
	
	public static String getDay(){
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd");
		tempDate.setLenient(false);
		return tempDate.format(new java.util.Date());
	}
	
	public static String formatDate(Date date, String pattern) {
        if (date == null) return "";
        //默认为yyyy-MM-dd
        if (pattern == null) pattern = "yyyy-MM-dd";
        SimpleDateFormat tempDate = new SimpleDateFormat(pattern);
        tempDate.setLenient(false);
        return (tempDate.format(date));
    }
	/**
	 * 日历
	 * @param date
	 * @param pattern
	 */
	public static String calendar(Date date, int pattern) {
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd");
		Calendar canlandar = Calendar.getInstance();  
		canlandar.setTime(date);  
		canlandar.add(canlandar.DATE, pattern);   
		return tempDate.format(canlandar.getTime());
	}
	
	/**
	 * 一般首次还款日
	 * @return
	 */
	public static String getDaysInMonth(){
		Calendar cal = Calendar.getInstance(); 
		int _year = cal.get(Calendar.YEAR);//年 
		int _month = cal.get(Calendar.MONTH)+1;//月  
		int _day = cal.get(Calendar.DATE);//日
		if(_day>=25){
//			_month = _month+2;
//			_day = 05;
			//修改首次还款日如果在25日到月底的改为次月25日
			_month = _month+1;
			_day = 25;
		}else if(_day<5){
			_month = _month+1;
			_day = 05;
		}else if(_day>=5&&_day<15){
			_month = _month+1;
			_day = 15;
		}else if(_day>=15&&_day<25){
			_month = _month+1;
			_day = 25;
		}
		if(_month>12){
			_month = _month - 12;
			_year = _year+1;
		}
		String str = "";
		if(_month<10){
			str = _year+"-0"+_month+"-";
		}else{
			str = _year+"-"+_month+"-";
		}
		if(_day < 10){
			str += "0"+_day;
		}else{
			str += _day;
		}
		return str;
	}
	
	/**
	 * 每月还款日
	 * @return
	 */
	public static String getMthDay(){
		Calendar cal = Calendar.getInstance();  
		int _month = cal.get(Calendar.MONTH)+1;//月  
		int _day = cal.get(Calendar.DATE);//日
		if(_day>=25){
//			_month = _month+2;
//			_day = 05;
			//修改首次还款日如果在25日到月底的改为次月25日
			_month = _month+1;
			_day = 25;
		}else if(_day<5){
			_month = _month+1;
			_day = 05;
		}else if(_day>=5&&_day<15){
			_month = _month+1;
			_day = 15;
		}else if(_day>=15&&_day<25){
			_month = _month+1;
			_day = 25;
		}
		return String.valueOf(_day);
	}
	
	public static String getDaysInMonthYd(){
		Calendar cal = Calendar.getInstance();  
		int _year = cal.get(Calendar.YEAR);//年 
		int _month = cal.get(Calendar.MONTH)+1;//月  
		_month = _month+1;
		//移动固定次月5日
		String _day = "05";
		if(_month>12){
			_month = _month - 12;
			_year = _year+1;
		}
		String month = _month+"";
		if(_month<10){
			month = "0"+_month;
		}
		return _year+"-"+month+"-"+_day;
	}
	
	/**
	 * 获取日期间隔天数
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return
	 */
	public static String getGapDays(Date start,Date end){
		long milliseconds1 = start.getTime();
		long milliseconds2 = end.getTime();
		long diff = milliseconds2 - milliseconds1;
//		long diffSeconds = diff / 1000;
//		long diffMinutes = diff / (60 * 1000);
//		long diffHours = diff / (60 * 60 * 1000);
		long diffDays = diff / (24 * 60 * 60 * 1000);
//		//总毫秒
//		System.out.println("Time in milliseconds: " + diff + " milliseconds.");
//		//秒
//		System.out.println("Time in seconds: " + diffSeconds + " seconds.");
//		//分钟
//		System.out.println("Time in minutes: " + diffMinutes + " minutes.");
//		//小时
//		System.out.println("Time in hours: " + diffHours + " hours.");
//		//天
//		System.out.println("Time in days: " + diffDays + " days.");
		return String.valueOf(diffDays);
	}
	
	/**
	 * 根据当前时间获取下一个还款日
	 * @return
	 */
	public static String getDaysForNextInMonth(String mthDate){
		Calendar cal = Calendar.getInstance(); 
		int _year = cal.get(Calendar.YEAR);//年 
		int _month = cal.get(Calendar.MONTH)+1;//月  
		int _day = cal.get(Calendar.DATE);//日
		if(_day >= Integer.parseInt(mthDate)){
			_month = _month+1;
		}
		
		if(_month>12){
			_month = _month - 12;
			_year = _year+1;
		}
		String str = "";
		if(_month<10){
			str = _year+"-0"+_month+"-";
		}else{
			str = _year+"-"+_month+"-";
		}
		if(Integer.parseInt(mthDate)<10){
			mthDate = "0"+mthDate;
		}
		
		return str + mthDate;
	}
	
	
	/**
	 * 判断字符串是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 字符串轉日期
	 * @param dateStr
	 * @param formatStr
	 * @return
	 */
	public static Date StringToDate(String dateStr, String formatStr) {
		DateFormat dd = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = dd.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 计算两日期之间的天数
	 * @param rq1（YYYY-MM-DD） 起始日期
	 * @param rq2（yyyy-mm-dd）到期日期
	 * @return 天数
	 */
	public static long getQuot(String rq1, String rq2){
		long quot = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = ft.parse(rq1);
			Date date2 = ft.parse(rq2);
			quot = date1.getTime() - date2.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return quot;
	}
	
	/**
	 * 计算两个日期间的天数
	 * 
	 * @param fromDate
	 *            起始日期
	 * @param toDate
	 *            结束日期
	 * @return
	 * @throws ServiceException 
	 */
	public static int dateDiff(String fromDate, String toDate) throws Exception{
		int days = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		try {
			Date from = df.parse(fromDate);
			Date to = df.parse(toDate);
			days = (int) Math.round((from.getTime() - to.getTime())
					/ (24 * 60 * 60 * 1000));
		} catch (ParseException e) {
			throw new Exception(e.getMessage());
		}
		
		return days;
	}
	
	/**
	 * 计算两日期之间的月数
	 * @param qsrq（YYYY-MM-DD） 起始日期
	 * @param bjrq（yyyy-mm-dd）到期日期
	 * @return 天数
	 */
	public static int accountDate(String qsrq,String bjrq)throws Exception{
		//SimpleDateFormat df  = new SimpleDateFormat("yyyy-mm-dd");
		//String d1 = df.parse(qsrq).toString();
		//String d2 = df.parse(bjrq).toString();
		String[] s1 = qsrq.split("-");
		String[] s2 = bjrq.split("-");
		int year1 = Integer.parseInt(s1[0]);
		int year2 = Integer.parseInt(s2[0]);
		if(year2-year1<0){
			throw new Exception("输入的终止日期年份小于起始日期的年份");
		}
		int a = 12*(year2-year1);
	    int month1 = Integer.parseInt(s1[1]);
	    int month2 = Integer.parseInt(s2[1]);
	    int b = month2-month1;
	    int day1 = Integer.parseInt(s1[2]);
	    int day2 = Integer.parseInt(s2[2]);
	    int c = a+b;
	    if(day2-day1>0){
	    	c = a+b+1;
	    }
	    return c;
		
	}

	/**
	 * 计算日期加天数得到日期
	 * @param rq（YYYY-MM-DD） 日期
	 * @param ts 天数
	 * @return 日期
	 */
	public static String dateValues(String rq , int ts) throws Exception{
		Calendar cl = Calendar.getInstance();
		cl.set(Integer.parseInt(rq.substring(0, 4)), (Integer.parseInt(rq.substring(5,7)) - 1), Integer.parseInt(rq.substring(8, 10)));
		cl.add(Calendar.DAY_OF_MONTH, ts);
		return cl.get(Calendar.YEAR) + "-" + (cl.get(Calendar.MONTH) < 9 ? "0" : "") + (cl.get(Calendar.MONTH) + 1) + "-" + (cl.get(Calendar.DAY_OF_MONTH) < 10 ? "0" : "") + cl.get(Calendar.DAY_OF_MONTH);  
	}

	/**
	 * 计算日期加月得到日期
	 * @param rq（YYYY-MM-DD） 日期
	 * @param ts 月
	 * @return 日期
	 */
	public static String dateValues_MONTH(String rq , int ts) throws Exception{
		Calendar cl = Calendar.getInstance();
		cl.set(Integer.parseInt(rq.substring(0, 4)), (Integer.parseInt(rq.substring(5,7)) - 1), Integer.parseInt(rq.substring(8, 10)));
		cl.add(Calendar.MONTH, ts);
		return cl.get(Calendar.YEAR) + "-" + (cl.get(Calendar.MONTH) < 9 ? "0" : "") + (cl.get(Calendar.MONTH) + 1) + "-" + (cl.get(Calendar.DAY_OF_MONTH) < 10 ? "0" : "") + cl.get(Calendar.DAY_OF_MONTH);  
	}
	
	/**
	 * @description: 计算一天的开始时间
	 * @creator: roilat-D
	 * @createDate: 2016年6月24日 
	 */
	public static Date toDayStartDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * @description: 计算一天的结束时间
	 * @creator: roilat-D
	 * @createDate: 2016年6月24日 
	 */
	public static Date toDayEndDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date tmp = null;
		try {
			tmp = sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			return null;
		}
		return new Date(tmp.getTime() + 24 * 60 * 60 * 1000 - 1);
	}
}
