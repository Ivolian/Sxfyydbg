package unicorn.withub.sxfyydbg.exception;

public class NotFoundNetConnectionException extends BaseException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotFoundNetConnectionException(String message) {
		super("", message);
	}

	@Override
	public Throwable fillInStackTrace() {
		// TODO Auto-generated method stub
		return this;
	}

}
