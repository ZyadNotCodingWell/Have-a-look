package cyBooks;
/**
 * Represents the operational status of a borrowing transaction in the library management system.
 * This enumeration is used to track the current state of a loan, whether it is ongoing, closed, late yet still not returned
 * or if the item was returned late.
 */
public enum OpStatus {
	Ongoing,
	Closed,
	LateReturned,
	Late,
}
