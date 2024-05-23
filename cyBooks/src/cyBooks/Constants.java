// Constants.java
package cyBooks;
/**
 * Holds the constant values used throughout the library management system.
 * This class defines limits and thresholds such as the maximum number of books 
 * a user can borrow at one time and the maximum number of late returns allowed 
 * before a user's account is blocked.
 */

public class Constants {
    /**
     * The maximum number of books a user is allowed to borrow at one time.
     * This limit is used to ensure that no single user monopolizes library resources.
     */
    public static final int BOOK_LIMIT = 5;

    /**
     * The maximum number of times a user can return books late before their account 
     * is blocked. This limit is used to encourage timely returns of borrowed materials.
     */
    public static final int LATE_RETURN_LIMIT = 4;
}
