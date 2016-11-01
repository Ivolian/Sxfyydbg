package unicorn.withub.sxfyydbg.exception;

public class SysException extends BaseException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SysException(String message) {
		super("", message);
	}

	public SysException(String code, String message) {
		super(code, message);
	}

	@Override
	public Throwable fillInStackTrace() {
		// TODO Auto-generated method stub
		return this;
	}

}
