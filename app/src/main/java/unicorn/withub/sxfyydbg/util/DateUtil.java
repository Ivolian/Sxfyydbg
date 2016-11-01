package unicorn.withub.sxfyydbg.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;




@SuppressLint("NewApi")
public class DateUtil {
	public static final int DATE_KS=1;
	public static final int DATE_JS=2;
	/**构建选择日期的对话框*/
	public static DatePickerDialog getDialog(Context context,DatePickerDialog.OnDateSetListener onDateSetListener){
		Calendar calendar = Calendar.getInstance();
		DatePickerDialog dialog=new DatePickerDialog(context,onDateSetListener,
        		calendar.get(Calendar.YEAR),
        		calendar.get(Calendar.MONTH),
        		calendar.get(Calendar.DAY_OF_MONTH)); 
		dialog.getDatePicker().setCalendarViewShown(false);
		dialog.setTitle("请选择日期");
		return dialog;
	}
	public static TimePickerDialog getTimeDialog(Context context,TimePickerDialog.OnTimeSetListener onTimeSetListener){
		Calendar calendar=Calendar.getInstance();
		TimePickerDialog dialog=new TimePickerDialog(context, onTimeSetListener,
				calendar.get(Calendar.HOUR_OF_DAY), 
				calendar.get(Calendar.MINUTE), 
				true);
		dialog.setTitle("请选择时间");
		return dialog;
	}
    /**比较日期前后顺序*/
	public static boolean isDate(String date_start,String date_end){
		if(date_start!=null&&date_end!=null){
			Date startDate=parseDate1(date_start);
			Date endDate=parseDate1(date_end);
			Log.i("startDate", "startDate="+startDate.toString());
			if(endDate.before(startDate)){
				return false;
			}else {
				return true;
			}
		}else{
			return true;
		}
	}
	/**比较日期前后顺序*/
	public static boolean isDate1(String date_start,String date_end){
		if(date_start!=null&&date_end!=null){
			Date startDate=parseDate1(date_start);
			Date endDate=parseDate1(date_end);
			Log.i("startDate", "startDate="+startDate.toString());
			Log.i("endDate", "endDate="+endDate.toString());
			if(endDate.before(startDate)){
				return false;
			}else {
				return true;
			}
		}else{
			return true;
		}
	}
	/**比较日期前后顺序*/
	public static boolean isThreeDayDate(String date_start,String date_end){
		if(date_start!=null&&date_end!=null){
			Date startDate=parseDate1(date_start);
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(startDate);
			calendar.add(Calendar.DAY_OF_MONTH, 3);
			Date newStart=calendar.getTime();
			
			Date endDate=parseDate1(date_end);
			Log.i("newStart", "newStart="+newStart.toString());
			if(endDate.after(newStart)){
				return false;
			}else {
				return true;
			}
		}else{
			return true;
		}
	}
	/**比较日期前后顺序*/
	public static boolean isTodayDate(Date date_start,String date_end){
		if(date_start!=null&&date_end!=null){
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(date_start);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			Date newStart=calendar.getTime();
			Date endDate=parseDate1(date_end);
			Log.i("startDate", "startDate="+newStart.toString());
			Log.i("endDate", "endDate="+endDate.toString());
			if(endDate.before(newStart)){
				return false;
			}else {
				return true;
			}
		}else{
			return true;
		}
	}
	/**
	 * 将字符串转换成日期类型
	 * 
	 * @param date
	 *            日期字符串 正确日期格式为 yyyy-mm-dd 00:00:00
	 * @return
	 */
	public static Date parseDate(String date) {
		Date ret = null;

		if (date == null) {
			return null;
		}

		DateFormat df = null;

		if (date.indexOf(" ") > 0) { // 日期&时间
			if (date.lastIndexOf(":") == date.indexOf(":")) {
				date = date + ":00";
			}
			df = DateFormat.getDateTimeInstance(2, 2, Locale.CHINA);
		} else { // 日期
			df = DateFormat.getDateInstance(2, Locale.CHINA);
		}

		try {
			ret = df.parse(date);
		} catch (ParseException pe) {
			throw new RuntimeException("录入的日期格式错误，入参：" + date, pe);
		}

		return ret;
	}
	/**
	 * 将字符串转换成日期类型
	 * 
	 * @param date
	 *            日期字符串 正确日期格式为 yyyy-mm-dd 00:00:00
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date parseDate1(String date) {
		Date ret = null;

		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
			ret=sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	/**
	 * 将日期类型转换成字符串类型
	 * 
	 * @param date
	 * @return string
	 */
	public static String toString(Date date) {
		if (date == null) {
			return "";
		}
		String ret = toString(date, true);
		return ret;
	}

	/**
	 * 将日期类型转换成字符串类型
	 * 
	 * @param date
	 * @param hasTime
	 *            是否显示时、分、秒
	 * @return string
	 */
	public static String toString(Date date, boolean hasTime) {
		if (date == null) {
			return null;
		}

		DateFormat df = null;

		if (hasTime) {
			df = DateFormat.getDateTimeInstance(2, 2, Locale.CHINA);
		} else {
			df = DateFormat.getDateInstance(2, Locale.CHINA);
		}

		String ret = df.format(date);

		return ret;
	}

	/**
	 * 自定义格式日期输出
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            输入格式 例如：yyyy.MM.dd yyyy/MM/dd HH:mm:ss
	 * @return
	 */
	public static String formatDateTime(Date date, String format) {
		if (date == null) {
			return "";
		}
		return (new SimpleDateFormat(format)).format(date);
	}

	
	public static Date toDate(String source,String format){
		if(source==null){
			return null;
		}
		
		try {
			return new SimpleDateFormat(format).parse(source);
		} catch (ParseException e) {
			throw new RuntimeException("录入的日期格式错误，入参：" + source, e);
		}
	}
	public static String formatDateTime(Date date, int iDays, String format) {
		String ret = "";
		if (date == null) {
			return "";
		}
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.DAY_OF_YEAR, iDays);
		ret = formatDateTime(ca.getTime(), format);
		return ret;

	}

	public static int getWeekDay() {
		return getWeekDay(new Date());
	}

	public static int getWeekDay(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		int day = ca.get(Calendar.DAY_OF_WEEK);
		day = day - 1;
		if (day == 0) {
			day = 7;
		}
		return day;
	}

	public static Date getWeekFirstDay() {
		Calendar ca = Calendar.getInstance();
		int day = ca.get(Calendar.DAY_OF_WEEK);
		day = day - 1;
		if (day == 0) {
			day = 7;
		}
		day = -1 * (day - 1);
		ca.add(Calendar.DATE, day);
		return ca.getTime();
	}

	public static String formatDate(String time) {
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			return format.format(format.parse(time));
		} catch (ParseException e) {
			throw new RuntimeException("录入的日期格式错误，入参：" + time, e);
		}
	}

	/**
	 * 判断pTime日期是星期几
	 * 
	 * @return dayForWeek 判断结果
	 */
//	public static String dayForWeek(String time) {
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		Calendar c = Calendar.getInstance();
//		try {
//			c.setTime(format.parse(time));
//		} catch (ParseException e) {
//			throw new RuntimeException("录入的日期格式错误，入参：" + time, e);
//		}
//		int dayForWeek = 0;
//		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
//			dayForWeek = 7;
//		} else {
//			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
//		}
//		return WeekDayEnum.getWeekDay(dayForWeek).toString();
//	}

	public enum WeekDayEnum {
		无, 星期日, 星期一, 星期二, 星期三, 星期四, 星期五, 星期六;
		public static WeekDayEnum getWeekDay(int p) {
			switch (p) {
			case 1:
				return 星期一;
			case 2:
				return 星期二;
			case 3:
				return 星期三;
			case 4:
				return 星期四;
			case 5:
				return 星期五;
			case 6:
				return 星期六;
			case 7:
				return 星期日;
			}
			return 无;
		}
	}

}
