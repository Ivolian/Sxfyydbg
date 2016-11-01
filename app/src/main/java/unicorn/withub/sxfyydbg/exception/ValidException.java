package unicorn.withub.sxfyydbg.exception;

public class ValidException extends BaseException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValidException(String message) {
		super("", message);
	}

	@Override
	public Throwable fillInStackTrace() {
		// TODO Auto-generated method stub
		return this;
	}

}
