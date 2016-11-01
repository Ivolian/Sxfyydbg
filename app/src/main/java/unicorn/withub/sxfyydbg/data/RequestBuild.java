package unicorn.withub.sxfyydbg.data;

import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;



public abstract class RequestBuild {
	private String md5Key;

	/**
	 * @param bundle
	 *            主报文以报文段为key，Parameter以parameter_报文段为key
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public abstract String buildXml(Bundle bundle, Context context)
			throws UnsupportedEncodingException, NoSuchAlgorithmException;

	public abstract String getServerUrl();

	// 取得SharedPreferences里面的值
	public String getMd5Key(Context context) {
		return this.md5Key;
	}

	/**
	 * 返回 Corpartner-id 手机唯一的设备ID
	 * 
	 * @param context
	 * @return
	 */
	protected String getPhoneInfo(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		/*
		 * 唯一的设备ID： GSM手机的 IMEI 和 CDMA手机的 MEID. Return null if device ID is not
		 * available.
		 */
		String deviceID = tm.getDeviceId();
		Log.i("", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+deviceID);
		return deviceID;
		// return Constant.stationid;
	}

	/**
	 * @功能 获取clientType sim imsi phone填入报文
	 * 
	 * @param context
	 * @return
	 */
	protected StringBuffer getFixedSegment(Context context) {
		StringBuffer buf = new StringBuffer();
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		/*
		 * 手机号： GSM手机的 MSISDN. Return null if it is
		 * 
		 * unavailable.
		 */
		String phone = tm.getLine1Number();
		/*
		 * SIM卡的序列号： 需要权限：READ_PHONE_STATE
		 */
		String sim = tm.getSimSerialNumber();
		/*
		 * 唯一的用户ID： 例如：IMSI(国际移动用户识别码) for a
		 * 
		 * GSM phone. 需要权限：READ_PHONE_STATE
		 */
		String IMSI = tm.getSubscriberId();
		/*
		 * <clientType></clientType> <sim></sim> <imsi></imsi> <phone></phone>
		 */
		buf.append("<clientType>").append("android_caipiao") // clientType 传入 android
				// 目标是android统一建一个客户端如果测试必须在版本号下面建客户端的再建版本号
				// .append(android.os.Build.VERSION.RELEASE)
				.append("</clientType>").append("<sim>").append(sim).append(
						"</sim>").append("<imsi>").append(IMSI).append(
						"</imsi>").append("<phone>").append(phone).append(
						"</phone>");
		return buf;
	}

	/**
	 * 返回四个字段：[clientType ][ sim ][ imsi ][ phone ]
	 * 
	 * @param context
	 * @return
	 */
	protected String getFixedString(Context context) {
		StringBuffer buf = new StringBuffer();
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		/*
		 * 手机号： GSM手机的 MSISDN. Return null if it is
		 * 
		 * unavailable.
		 */
		String phone = tm.getLine1Number();
		/*
		 * SIM卡的序列号： 需要权限：READ_PHONE_STATE
		 */
		String sim = tm.getSimSerialNumber();
		/*
		 * 唯一的用户ID： 例如：IMSI(国际移动用户识别码) for a
		 * 
		 * GSM phone. 需要权限：READ_PHONE_STATE
		 */
		String IMSI = tm.getSubscriberId();
		/*
		 * [clientType ][ sim ][ imsi ][ phone ]
		 */
		buf.append("android_caipiao")// clientType 传入 android
				// 目标是android统一建一个客户端如果测试必须在版本号下面建客户端的再建版本号
				// .append(android.os.Build.VERSION.RELEASE)
				.append(sim).append(IMSI).append(phone);
		return buf.toString();
	}

	public String formatPwd(String string) {
		// 
		if (string == null || string.length() == 0)
			return "";
		int l = string.length();
		return (l <= 9 ? ("0" + l) : (l)) + string;
	}

	protected String getRequestFlow() {
		return String.valueOf(System.currentTimeMillis());
	}

	public static RequestBuild getInstance(String className) {
		if (className.equals("BusiGateRequestBuild")) {
			return new NormalRequestBuild();
		} 
		return null;
	}
	
	/**
	 * CaiPiaoRequestBuild
	 */
	public static final String REQUESTbUILD_CLASS_CAIPIAOREQUESTBUILD = "CaiPiaoRequestBuild";
}



