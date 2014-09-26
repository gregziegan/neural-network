package DataParsing;

public class DataException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor: Creates a new DataException with no message.
	 */
	public DataException() {
		super();
	}
	
	/**
	 * Constructor: Creates a new DataException.
	 * @param message the reason for raising an exception.
	 */
	public DataException(String message) {
		super(message);
	}
	
}
