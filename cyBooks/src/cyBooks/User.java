package cyBooks;

import java.sql.Date;
/**
 * Represents a user of the library management system.
 * This class contains user-specific information and manages their borrowing status,
 * number of borrowed books, and their history of late returns.
 */

public class User {
	
	private String id;
	private String surname;
	private String name;
	private Date dateOfBirth;
	private String email; // Is now a String
	private Status status;
	private int currentlyBorrowedBooks;
	private int numberOfLateReturns;
        
	
	public String getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }
    public void setId(String i) {
		this.id=i;	
	}
    public void setName(String name) {
		this.name=name;	
	}
    public void setSurname(String surname) {
		this.surname=surname;	
	}
    public void setEmail(String email) {
		this.email=email;	
	}
	/**
         * Constructs a new User with specified personal and account details.
         *
         * @param id the unique identifier of the user
         * @param surname the surname of the user
         * @param name the name of the user
         * @param dateOfBirth the birth date of the user
         * @param email the email address of the user
         * @param status the current status of the user's library account
         * @param numberOfLateReturns the initial number of late returns by the user
         */
	
	public User(String id, String surname, String name, Date dateOfBirth, String email, int numberOfLateReturns) { 
		this.id = id;
		this.surname = surname;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.email = email; // I could put a constraint, but since usage is under monitoring of librarian, let him do his job for once and make sure the emails are valid
		this.status = Status.openToRun;
		this.currentlyBorrowedBooks = 0 ;
		this.numberOfLateReturns = 0;
	}

	/** Constructor exclusively set to create instances out of retireved data form database
 	*/
	public User(String id, String surname, String name, Date dateOfBirth, String email, Status status, int numberOfLateReturns, int currentlyBorrowedBooks) {
		this.id = id;
		this.surname = surname;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.status = status;
		this.currentlyBorrowedBooks = currentlyBorrowedBooks ;
		this.numberOfLateReturns = numberOfLateReturns;
	}
	
	public User(String id2, String surname2, String name2, java.util.Date dateOfBirth2, String email2, Status status2,
			int currentlyBorrowedBooks2, int numberOfLateReturns2) {
		this.id = id2;
		this.surname = surname2;
		this.name = name2;
		this.dateOfBirth = (Date) dateOfBirth2;
		this.email = email2;
		this.status = status2;
		this.currentlyBorrowedBooks = currentlyBorrowedBooks2 ;
		this.numberOfLateReturns = numberOfLateReturns2;
	}

	/**
         * Retrieves the current number of books the user has borrowed.
         *
         * @return the number of books currently borrowed by the user
         */
	public int getCurrentlyBorrowedBooks() {
		return this.currentlyBorrowedBooks;
	}
        
	/**
         * Gets the current status of the user's library account.
         *
         * @return the status of the user's account 
         */
	public Status getStatus() {
		return this.status;
	}
       
	/**
         * Decreases the count of currently borrowed books by one.
         * Used when a book is returned.
         *
         * @throws BadStockArgumentException if no books are currently borrowed
	 */
	public void decreaseCurrentlyBorrowedBooks() throws BadStockArgumentException {
		if (currentlyBorrowedBooks > 0) {
			currentlyBorrowedBooks --;
		} else {
			throw new BadStockArgumentException("Argument out of range");
		}
		
	}
        /**
         * Increases the count of currently borrowed books by one.
         * Used when a new book is borrowed.
         *
         * @throws BadStockArgumentException if borrowing exceeds the allowed limit
         */
	public void increaseCurrentlyBorrowedBooks() throws BadStockArgumentException {
		if (currentlyBorrowedBooks < Constants.BOOK_LIMIT) {
			currentlyBorrowedBooks ++;
		} else {
			throw new BadStockArgumentException("Argument out of range");
		}
		
	}
        
	/**
        * Gets the number of times the user has returned books late.
        *
        * @return the number of late returns
        */
	public int getNumberOfLateReturns() {
		return this.numberOfLateReturns;
	}
       
	/**
        * Sets the number of late returns for the user.
        *
        * @param numberOfLateReturns the new number of late returns
        */
	public void setNbOfLateReturns(int i) {
		this.numberOfLateReturns = i;
		
	}
        
	/**
         * Sets the status of the user's library account.
         *
         * @param status the new status of the user's account
         */
	public void setStatus(Status status) {
		this.status = status;
		
	}
	/**
         * Redeems or updates the user's account status based on their current borrowing activity.
         * If books are still borrowed, the status is set to currentlyBorrowing; otherwise, it is set to openToRun.
         */
	public void redeemUser() { // "Unblock" an account if necessary
		this.setNbOfLateReturns(0);
		if(this.currentlyBorrowedBooks > 0) {
			this.setStatus(Status.currentlyBorrowing);
		} else {
			this.setStatus(Status.openToRun);
		}
	}
	public void setDateOfBirth(Date newDob) {
		this.dateOfBirth = newDob;
		
	}


}
