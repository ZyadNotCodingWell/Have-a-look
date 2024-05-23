package cyBooks;
/**
 * Exception thrown when the arguments related to the stock of a book are invalid.
 * This exception is used to indicate specific problems with stock management operations
 * in the library management system.
 */
public class BadStockArgumentException extends Exception {
	/**
	 * Version identifier for serialization.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Constructs a new exception with the specified message.
	 * @param message the detail message explaining the cause of the exception
	 */
	public BadStockArgumentException(String message) {
		super(message);
		printStackTrace();
	}
}
