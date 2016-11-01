package unicorn.withub.sxfyydbg.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import unicorn.withub.sxfyydbg.activity.LoginActivity;

public class StringUtil {
	public static String edittype="";
	public static String editcontent="";
	public static String nullOrEmptyToStr(String str){
		if(str == null || str.trim().equals("")){
			return "";
		}
		
		String tmp = str.toLowerCase();
		if(tmp.trim().equals("null")){
			return "";
		}
		
		return str.trim();
	}
	/**注销清空任务站，跳到登录页面*/
	@SuppressLint("InlinedApi")
	public static void tagetToLogin(Context context){
		Intent intent=new Intent(context, LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	public static String DoubleStrSubStr(String source,int length){
		String tmp = nullOrEmptyToStr(source);
		
		if(!tmp.equals("") && tmp.indexOf(".") != -1){
			return tmp.substring(0, tmp.indexOf(".") + length);
		}
		
		return tmp;
	}
	/**判断字符串长度*/
	public static boolean outLength(int strlength,int n){
		if(n<=strlength){
			return true;
		}else{
			return false;
		}
	}
	/**邮箱正则表达式*/
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;

	}
	/**手机号码正则表达式*/
	public static boolean checkMobileNumber(String mobileNumber) {
		boolean flag = false;
		try {
			Pattern regex = Pattern
					.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
			Matcher matcher = regex.matcher(mobileNumber);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	/**日期格式正则表达式*/
	public static boolean checkDate(String date){
		boolean flag = false;
		try {
			Pattern regex = Pattern
					.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\-\\s]?((((0?" +"[13578])|(1[02]))[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))" +"|(((0?[469])|(11))[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|" +"(0?2[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12" +"35679])|([13579][01345789]))[\\-\\-\\s]?((((0?[13578])|(1[02]))" +"[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))" +"[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\-\\s]?((0?[" +"1-9])|(1[0-9])|(2[0-8]))))))" );
			Matcher matcher = regex.matcher(date);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	/**时间格式正则表达式*/
	public static boolean checkTime(String time){
		boolean flag = false;
		try {
			Pattern regex = Pattern
					.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
			Matcher matcher = regex.matcher(time);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}


}
