package cyBooks;
/**
 * Exception thrown when invalid credentials are provided for authentication.
 * This exception is used to indicate that the login credentials provided by the librarian are incorrect.
 */
public class UnvalidCredentialsException extends Exception {
	/**
	 * Version identifier for serialization.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Constructs a new exception with the specified message.
	 * @param e the detail message explaining the cause of the exception
	 */
	public UnvalidCredentialsException(String e) {
		super(e);
	}
}
