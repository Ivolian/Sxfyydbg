package unicorn.withub.sxfyydbg.exception;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;




public class ErrorHandler {
	public static String getMsg(Exception e) {

		if (e instanceof AppException) {
			AppException ex = (AppException) e;
			return ex.getMessage();
		} else if (e instanceof IgnoreException) {
			IgnoreException ex = (IgnoreException) e;
			return ex.getMessage();
		} else if (e instanceof SysException) {
			SysException ex = (SysException) e;
			return ex.getMessage();
		} else if (e instanceof TimeoutException) {
			return "网络连接超时，请稍后再试";
		} else if (e instanceof SocketTimeoutException) {
			return "网络连接超时，请稍后再试";
		} else if (e instanceof SocketException) {
			return "网络连接失败，请稍后再试";
		} else if (e instanceof ValidException) {
			return e.getMessage();
		} else if (e instanceof NotFoundNetConnectionException) {
			return e.getMessage();
		} else if (e instanceof UnknownHostException) {
			return "服务器暂时无法访问，请稍后再试或检查网络设置";
		}
		return "系统繁忙，请稍后再试";
	}

	public static void handleHttpResponse(int responseCode) {
		if (responseCode == -1) {
			throw new AppException("http_" + responseCode, "连接失败，请稍后再试");
		} else if (responseCode == 404) {
			throw new AppException("http_" + responseCode, "服务器地址不存在");
		} else if (responseCode == 500) {
			throw new AppException("http_" + responseCode, "服务器响应异常");
		} else {
			throw new AppException("http_" + responseCode, "网络连接失败，请稍后再试");
		}
	}

}
