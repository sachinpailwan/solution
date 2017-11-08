package answer.king.exception;

public class InvalidIItemException extends Exception {

	public final static String INVALID_ITEM="Could not create Item with invalid name :%s and Price: %s";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidIItemException(String arg0) {
		super(arg0);
		
	}
	
	

}
