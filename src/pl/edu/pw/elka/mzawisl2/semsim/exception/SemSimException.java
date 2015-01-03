package pl.edu.pw.elka.mzawisl2.semsim.exception;

public class SemSimException extends Exception {

	private static final long serialVersionUID = -4479323825871360572L;

	public SemSimException() {
		super();
	}

	public SemSimException(String message) {
		super(message);
	}

	public SemSimException(String message, Throwable cause) {
		super(message, cause);
	}

	public SemSimException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SemSimException(Throwable cause) {
		super(cause);
	}

}
