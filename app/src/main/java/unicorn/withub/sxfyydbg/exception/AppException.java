package unicorn.withub.sxfyydbg.exception;

public class AppException extends BaseException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AppException(String message) {
		super("", message);
	}

	public AppException(String code, String message) {
		super(code, message);
	}
	public AppException(String code, String message, Exception ex) {
		super(code, message,ex);
	}
	@Override
	public Throwable fillInStackTrace() {
		// TODO Auto-generated method stub
		return this;
	}

}
