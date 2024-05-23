package cyBooks;
/**
 * Represents the status of a user in the library management system.
 * This enumeration defines different states of a user's account, which can affect their ability to borrow books
 * and interact with the library system.
 */
public enum Status {
	currentlyBorrowing,
	blockedAccount,
	openToRun,
}
