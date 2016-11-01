package unicorn.withub.sxfyydbg.exception;

import java.io.Serializable;

public class BaseException extends RuntimeException implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 289159359507311068L;
	protected String code = ""; // 编码
	protected String message = ""; // 信息
	protected String location = ""; // 位置
	protected Exception ex;

	public BaseException(String code, String message) {
		this.message = message;
		this.code = code;
	}

	public BaseException(String code, String message, String location) {
		this.message = message;
		this.code = code;
		this.location = location;
	}

	public BaseException(String code, String message, String location,
			Exception ex) {
		this.message = message;
		this.code = code;
		this.location = location;
		this.ex = ex;
		if (ex != null) {
			this.initCause(ex.fillInStackTrace());
		}
	}

	public BaseException(String code, String message, Exception ex) {
		this.message = message;
		this.code = code;
		this.ex = ex;
		if (ex != null) {
			this.initCause(ex.fillInStackTrace());
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Exception getEx() {
		return ex;
	}

	public void setEx(Exception ex) {
		this.ex = ex;
		if (ex != null) {
			this.initCause(ex.fillInStackTrace());
		}
	}

}
