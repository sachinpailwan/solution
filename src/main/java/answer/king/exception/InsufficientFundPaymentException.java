package answer.king.exception;

public class InsufficientFundPaymentException extends Exception {

	public static final String INSUFFICIENT_PAYMENT_ERROR="Could not pay order due to insufficient payment. Required Payment :%s and Received Payment: %s";

	public InsufficientFundPaymentException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
}
