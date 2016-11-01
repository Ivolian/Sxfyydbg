package unicorn.withub.sxfyydbg.http;

import android.content.Context;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;

import unicorn.withub.sxfyydbg.util.SystemValidUtil;


public class ConnectionManage {
	// Android下实现wap和net自适应
			public static int CONNECT_TIMEOUT = 20 * 1000;// 连接超时时间

			public static HttpURLConnection getURLConnection_new(String url, Context context)
					throws IOException {
				HttpURLConnection conn = null;
				String proxyHost = android.net.Proxy.getDefaultHost();
				String netType = "";
				if (context != null)
					netType = SystemValidUtil.validConnect(context);
				URL _url = new URL(url);
				if (proxyHost != null && netType.equals("cmwap")) {
					String str = "http://" + proxyHost + _url.getFile();
					String str2 = _url.getHost() + ":" + _url.getPort();
					// if (true)
					// throw new AppException("", str);
					URL target = new URL(str);
					conn = (HttpURLConnection) target.openConnection();
					conn.setRequestProperty("X-Online-Host", str2);
				} else {
					conn = (HttpURLConnection) _url.openConnection();
				}
				conn.setConnectTimeout(ConnectionManage.CONNECT_TIMEOUT);
				return conn;
			}

			public static HttpURLConnection getURLConnection(String url,
					Context context) throws IOException {
				HttpURLConnection conn = null;
				String netType = "";
				if (context != null)
					netType = SystemValidUtil.validConnect(context);
				String proxyHost = android.net.Proxy.getDefaultHost();// cmwap接入时有代理,wifi也有设置代理的
				if(netType.equals("wifi")){
					conn= (HttpURLConnection) new URL(url).openConnection();
				} else if (proxyHost != null){
					java.net.Proxy p = new java.net.Proxy(java.net.Proxy.Type.HTTP,
							new InetSocketAddress(proxyHost, 80));
					conn= (HttpURLConnection) new URL(url).openConnection(p);
				} else {
					conn= (HttpURLConnection) new URL(url).openConnection();
				}
				//if (proxyHost != null && netType.equals("cmwap")) {
				//if (proxyHost != null && netType.indexOf("wap")>=0) {
					// android.net.Proxy.getDefaultPort()
				//	java.net.Proxy p = new java.net.Proxy(java.net.Proxy.Type.HTTP,
				//			new InetSocketAddress(proxyHost, 80));
				//	conn= (HttpURLConnection) new URL(url).openConnection(p);
				//} else {
				//	conn= (HttpURLConnection) new URL(url).openConnection();
				//}
				conn.setConnectTimeout(ConnectionManage.CONNECT_TIMEOUT);
				return conn;
			}
		}




