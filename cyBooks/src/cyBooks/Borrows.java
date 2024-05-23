package cyBooks;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Represents a borrowing transaction in the library management system.
 * This class manages the borrow details including the book, the user,
 * the date of the borrowing operation, and the expected date of return.
 */
public class Borrows {

    private int id; // Unique identifier for the borrow transaction
    private Book book;
    private User user;
    private Date DateOfOperation;
    private Date DateOfReturn;
    private OpStatus status;

    /**
     * Constructs a Borrows instance and registers a new borrowing operation.
     * Initializes the transaction, sets the borrowing and return dates, and updates
     * the status and availability of the borrowed book and user.
     *
     * @param book The book being borrowed.
     * @param user The user borrowing the book.
     * @throws BadStockArgumentException if the book is not available for borrowing
     * or the user has reached the borrowing limit or has a blocked account.
     */
    public Borrows(Book book, User user) throws BadStockArgumentException {
    	this.id = (book.getISBN() + "-" + user.getId() + "-" + Calendar.getInstance().getTime().hashCode()).hashCode(); // Correction to make id an int
        if (book.isAvailable() && user.getCurrentlyBorrowedBooks() < Constants.BOOK_LIMIT && user.getStatus() != Status.blockedAccount) {
            this.book = book;
            this.user = user;

            this.book.decreaseNbOfAvailableCopies();
            // updating book
            String updateBorrows = "UPDATE Books SET number_of_available_copies = ? WHERE isbn = ?";
            try (Connection conn = DatabaseConnection.getConnection(); // Obtenez la connexion
                 PreparedStatement pstmt = conn.prepareStatement(updateBorrows)) {
                pstmt.setInt(1, this.book.getNbOfAvailableCopies());
                pstmt.setString(2, this.book.getISBN());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            this.user.increaseCurrentlyBorrowedBooks();
            // updating user
            String updateUser = "UPDATE Users SET currentlyBorrowedBooks = ? WHERE id = ?";
            try (Connection conn = DatabaseConnection.getConnection(); // Obtenez la connexion
                 PreparedStatement pstmtUser = conn.prepareStatement(updateUser)) {
                pstmtUser.setInt(1, user.getCurrentlyBorrowedBooks());
                pstmtUser.setString(2, user.getId());
                pstmtUser.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            this.DateOfOperation = Calendar.getInstance().getTime(); // Current date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateOfOperation);
            calendar.add(Calendar.WEEK_OF_YEAR, 3);
            this.DateOfReturn = calendar.getTime();
            this.status = OpStatus.Ongoing;

        } else {
            throw new BadStockArgumentException("Book not available or user limit reached/blocked.");
        }
    }

    /**
     * Constructs a Borrows instance EXCLUSIVELY to parse rows from database table, please do not use for initialization
     */
    public Borrows(int id, Book book, User user, Date DateOfOperation, Date DateOfReturn, OpStatus status) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.DateOfOperation = DateOfOperation;
        this.DateOfReturn = DateOfReturn;
        this.status = status;
    }

    /**
     * Handles the return of the borrowed book.
     * Updates the number of available copies of the book, adjusts the user's currently borrowed books count,
     * and updates the operation status based on the previous status.
     * @throws Exception 
     */
    public void returnBook() throws Exception {
        this.book.increaseNbOfAvailableCopies();
        // updating books
        String updateBorrows = "UPDATE Books SET number_of_available_copies = ? WHERE isbn = ?";
        try (Connection conn = DatabaseConnection.getConnection(); // Obtenez la connexion
             PreparedStatement pstmt = conn.prepareStatement(updateBorrows)) {
            pstmt.setInt(1, this.book.getNbOfAvailableCopies());
            pstmt.setString(2, this.book.getISBN());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.user.decreaseCurrentlyBorrowedBooks();
        // updating users
        String updateUser = "UPDATE Users SET currentlyBorrowedBooks = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); // Obtenez la connexion
             PreparedStatement pstmtUser = conn.prepareStatement(updateUser)) {
            pstmtUser.setInt(1, user.getCurrentlyBorrowedBooks());
            pstmtUser.setString(2, user.getId());
            pstmtUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        switch (this.status) {
            case Late:
                this.setStatus(OpStatus.LateReturned);
                break;
            case Ongoing:
                this.setStatus(OpStatus.Closed);
                break;
            default:
                throw new Exception("Status Argument does not allow for a return");
        }
        // updating borrow in the database
        String updateBorrow = "UPDATE Borrow SET operation_status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); // Obtenez la connexion
             PreparedStatement pstmt = conn.prepareStatement(updateBorrow)) {
            pstmt.setString(1, this.status.name()); // Convertit l'énumération en String
            pstmt.setString(2, String.valueOf(this.id)); // Convertit l'id int en String
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	/**
         * Extends the return period for the borrowed book by three weeks from the current return date.
	 * Needs to implement condition on status, has to be ongoing before attempting
        */
	
    public void extendPeriod() {
        if (this.getStatus() == OpStatus.Ongoing) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateOfReturn);
            calendar.add(Calendar.WEEK_OF_YEAR, 3);
            this.DateOfReturn = calendar.getTime();
        } else {
            throw new IllegalArgumentException("Status Argument does not allow for period extension");
        }
    }

	/** 
 	* Update status of a book to stay up to date to any operations that were not terminated in time
  	* Only does the check if a borrow status is set to ongoing,
   	* late, and closed deals don't need to get updates.
    	* If a borrow is successfully updated, we increase the count of the number of late returns,
     	* If a user exceeds the limit of late returns, his account status is set to blocked
 	*/

    public void updateStatus() {
        if (this.getStatus() == OpStatus.Ongoing) {
            Date currentDate = Calendar.getInstance().getTime();
            int comparison = currentDate.compareTo(DateOfReturn);
            if (comparison > 0) {
                this.status = OpStatus.Late;
                this.user.setNbOfLateReturns(this.user.getNumberOfLateReturns() + 1);
                if (this.user.getNumberOfLateReturns() > Constants.LATE_RETURN_LIMIT) {
                    this.user.setStatus(Status.blockedAccount);
                }
            }
        }
    }
		
	/**
 	* Method to get how late an operation is to it's previously set deadline
 	*/

   
    public String DeadlineTimeDiff() {
        if (this.status == OpStatus.Late) {
            Date currentDate = new Date();
            long differenceInMillis = currentDate.getTime() - this.DateOfReturn.getTime();
            long diffInDays = TimeUnit.MILLISECONDS.toDays(differenceInMillis);
            return String.format("%d days", diffInDays);
        } else {
            return "This operation is either not late, or has already been terminated";
        }
    }



	
	public String getBorrowDuration() {
        // Calculate the difference in milliseconds
        long differenceInMillis = this.DateOfReturn.getTime() - this.DateOfOperation.getTime();

        // Calculate the difference in days
        long diffInDays = TimeUnit.MILLISECONDS.toDays(differenceInMillis);

        // Format the time difference into a readable string
        String timeDifference = String.format("%d days", diffInDays);

        return timeDifference;
    }



	public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateOfReturn() {
        return this.DateOfReturn;
    }

    public void setDateOfReturn(Date dateOfReturn) {
        this.DateOfReturn = dateOfReturn;
    }

    public OpStatus getStatus() {
        return this.status;
    }

    public void setStatus(OpStatus status) {
        this.status = status;
    }

    public Book getBook() {
        return this.book;
    }

    public User getUser() {
        return this.user;
    }

    public Date getDateOfOperation() {
        return this.DateOfOperation;
    }
	
	
}
