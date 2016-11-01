package unicorn.withub.sxfyydbg.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import unicorn.withub.sxfyydbg.R;
import unicorn.withub.sxfyydbg.exception.NotFoundNetConnectionException;


public class SystemValidUtil {
	/**
	 * 检测网络是否连接（注：需要在配置文件即AndroidManifest.xml加入权限）
	 * 
	 * @param context
	 * @return String : wa
	 * @return false : 网络连接失败
	 */
	public static String validConnect(Context context) {
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			// 获取网络连接管理的对象
			/*
			 * NetworkInfo[] netinfo = cm.getAllNetworkInfo(); if (netinfo ==
			 * null) { return false; } for (int i = 0; i < netinfo.length; i++)
			 * { if (netinfo[i].isConnected()) { return true; } }
			 */
			NetworkInfo info = connectivity.getActiveNetworkInfo();

			if (info != null) {
				// 判断当前网络是否已经连接
				if (info.getState() == NetworkInfo.State.CONNECTED
						&& info.isAvailable()) {
					int netType = info.getType();

					// String ret = "XYZ_NetState_\n|TypeName:"
					// + info.getTypeName() + "\n|Type:" + info.getType()
					// + "\n|State:" + info.getState() + "\n|ExtraInfo:"
					// + info.getExtraInfo() + "\n|Reason:"
					// + info.getReason() + "\n|SubtypeName:"
					// + info.getSubtypeName() + "\n|Subtype:"
					// + info.getSubtype() + "\n|DetailedState:"
					// + info.getDetailedState();
					//
					if (netType == ConnectivityManager.TYPE_WIFI) {
						return "wifi";
					} else if (netType == ConnectivityManager.TYPE_MOBILE) {
						if (info.getTypeName() != null
								&& info.getTypeName().toLowerCase()
										.equals("mobile")) {
							// cmwap
							// cmnet
							// cnnet 电信3G
							// ctwap
							return info.getExtraInfo();
						}
					}
					return "";
				}
			}
		}

		throw new NotFoundNetConnectionException(
				context.getString(R.string.action_settings));
	}


}
