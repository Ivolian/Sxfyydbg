package unicorn.withub.sxfyydbg.exception;

public class IgnoreException extends BaseException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public IgnoreException(String location,String message) {
		super(location, message);
	}

	@Override
	public Throwable fillInStackTrace() {
		// TODO Auto-generated method stub
		return this;
	}
	public String getLocalizedMessage(){
		return "location="+getLocation()+"mag="+getMessage();
	}

}
