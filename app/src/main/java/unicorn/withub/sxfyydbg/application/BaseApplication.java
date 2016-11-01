package unicorn.withub.sxfyydbg.application;

import android.app.Application;

public class BaseApplication extends Application{
	public static String getVersion(){
		return "1.0";
	}
	
	public static String getUUID(){
		return "";
	}
	
	public static String getPhoneType(){
		return "android";
	}

}
