package unicorn.withub.sxfyydbg.http;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import unicorn.withub.sxfyydbg.data.BaseConstant;
import unicorn.withub.sxfyydbg.exception.ErrorHandler;
import unicorn.withub.sxfyydbg.exception.NotFoundNetConnectionException;
import unicorn.withub.sxfyydbg.exception.SysException;


public class HttpClients {
	private Context context;

	public HttpClients(Context context) {
		this.context = context;
	}

	public String postXml(String urlStr, String xml)
			throws UnsupportedEncodingException, IOException {
		String xmlDoc = "";
		byte[] xmlbyte = xml.toString().getBytes("utf-8");
		HttpURLConnection conn = null;
		try {
			conn = ConnectionManage.getURLConnection(urlStr, context);
			// 设置连接服务器超时时间
			conn.setConnectTimeout(ConnectionManage.CONNECT_TIMEOUT);
			// 设置从服务器读取数据超时时间
			conn.setReadTimeout(60 * 1000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			// conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", BaseConstant.CHARSET);
			conn.setRequestProperty("Content-Length",
					String.valueOf(xmlbyte.length));
			conn.setRequestProperty("Content-Type", "text/xml; charset="
					+ BaseConstant.CHARSET);
			conn.getOutputStream().write(xmlbyte);
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStreamReader isr = new InputStreamReader(
						conn.getInputStream(),  BaseConstant.CHARSET);
				BufferedReader bufferedReader = new BufferedReader(isr);
				// InputStreamReader 转换成带缓存的bufferedReader
				String s;
				while ((s = bufferedReader.readLine()) != null) {
					xmlDoc += s;
				}
				bufferedReader.close();
				isr.close();
			} else {
				ErrorHandler.handleHttpResponse(conn.getResponseCode());
			}
		} catch (UnsupportedEncodingException e) {
			// TODO: handle exception
			throw new SysException("字符集错误");
		} catch (IOException e) {
			// TODO: handle exception
			throw new SysException("连接超时,请稍候在试!");
		} catch (NotFoundNetConnectionException e) {
			throw e;
		} catch (Exception e) {
			throw new SysException("连接超时,请稍候在试!");
		} finally {
			try {
				conn.disconnect();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return xmlDoc;
	}

	public String get(String urlStr) throws UnsupportedEncodingException,
			IOException {
		HttpURLConnection conn = null;
		String returns = "";
		try {

			conn = ConnectionManage.getURLConnection(urlStr, context);
			conn.setConnectTimeout(60 * 1000);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("get");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "gbk");
			conn.setRequestProperty("Content-Type", "text/html; charset=gbk");
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStreamReader isr = new InputStreamReader(
						conn.getInputStream(), BaseConstant.CHARSET);
				BufferedReader bufferedReader = new BufferedReader(isr);
				// InputStreamReader 转换成带缓存的bufferedReader
				String s;
				while ((s = bufferedReader.readLine()) != null) {
					returns += s;
				}
				bufferedReader.close();
				isr.close();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO: handle exception
			throw e;
		} catch (IOException e) {
			// TODO: handle exception
			throw e;
		} finally {
			try {
				conn.disconnect();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		return returns;
	}

	/**
	 * 通过图片url返回图片Bitmap
	 * 
	 * @param url
	 * @return
	 */
	public byte[] getByte(String url) throws UnsupportedEncodingException,
			IOException {
		// Bitmap bitmap = null;
		byte[] imgData = null;
		HttpURLConnection conn = null;
		InputStream is = null;
		try {
			conn = ConnectionManage.getURLConnection(url, context);
			conn.setDoInput(true);
			conn.connect();
			is = conn.getInputStream();
			// 获取长度
			int length = (int) conn.getContentLength();
			if (length != -1) {
				imgData = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, imgData, destPos, readLen);
					destPos += readLen;
				}

			}

			// bitmap = BitmapFactory.decodeStream(is);

		} finally {
			try {
				is.close();
				conn.disconnect();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return imgData;
	}
}

