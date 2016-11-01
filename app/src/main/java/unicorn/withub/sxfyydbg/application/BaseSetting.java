package unicorn.withub.sxfyydbg.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.telephony.TelephonyManager;


public class BaseSetting {
    private SharedPreferences temp_settings;
	
	public static BaseSetting getInstance(Context ctx) {
		return new BaseSetting(ctx);
	}

	private BaseSetting(Context context) {
		this.temp_settings = context.getSharedPreferences("temp", 0);
		TelephonyManager local = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
	}
	
	/**
	 * 请求次数 requestTime
	 */
	public int getRequestTime() {
		return this.temp_settings.getInt("requestTime", 0);
	}

	public int createRequestTime() {
		int requestTime = getRequestTime();
		requestTime += 1;
		Editor sharedata = this.temp_settings.edit();
		sharedata.putInt("requestTime", requestTime);
		sharedata.commit();
		return requestTime;
	}

}
