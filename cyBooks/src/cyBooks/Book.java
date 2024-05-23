package cyBooks;

import java.util.Random;
/**
 * Represents a book in the library management system.
 */
public class Book {
	
	private String ISBN;
	private String title; 
	private String authors; // is now a String
	private String theme; // is also now a string
	private int NbOfCopies;
	private int NbOfAvailableCopies;
	/**
          * Constructs a new Book instance with detailed book information.
          * 
          * @param ISBN the unique International Standard Book Number for the book
          * @param title the title of the book
          * @param authors a set of Author objects representing the authors of the book
          * @param theme the theme of the book, represented as a Theme enum
          * @param nbOfCopies the total number of physical copies of the book available in the library
	  * @param nbOfAvailableCopies the number of physical copies of the book currently available for borrows
          * @throws BadStockArgumentException if the number of copies specified is negative or number of available copies bigger than number of copies
          */
	
	public Book(String iSBN, String title, String authors, String theme, int nbOfCopies) throws BadStockArgumentException {
		this.ISBN = iSBN;
		this.title = title;
		this.authors = authors;
		this.theme = theme;
		if (NbOfCopies >= 0) { // Condition reset on NbOfCopies
		this.NbOfCopies = nbOfCopies;
		this.NbOfAvailableCopies = nbOfCopies;
		} else { throw new BadStockArgumentException("Unvalid Arguments For Construction Of Entity"); }
	} // Exception thrown when the NbOfCopies is negative or NbOfAvailableCopies doesn't fit in the range

	
	// Constructor when the Number of copies is not present, generate a random number between 1 & 10 (Mainly for test reasons)
	
		public Book(String iSBN, String title, String authors, String theme) {
		this.ISBN = iSBN;
		this.title = title;
		this.authors = authors;
		this.theme = theme;
		Random randomNb = new Random();
		this.NbOfCopies = 1 + randomNb.nextInt(15); // Generates a random number between 1 and 15 
		this.NbOfAvailableCopies = this.NbOfCopies;
		}
       
	// Constructor EXCLUSIVELY MADE FOR DATABASE DATA EXTRACTION INDUCED INSTANCE CONSTRUCTION

	public Book( int nbOfCopies, String ISBN, String title, String authors, String theme, int nbOfAvailableCopies) throws BadStockArgumentException {
		this.ISBN = ISBN;
		this.title = title; 	// AS WE NOTICE, IT STARTS WITH AN INT INSTEAD OF STRING ISBN, CAREFUL BEACUSE THIS DETAIL IS WHAT MAKES THIS CONSTRUCTOR DIFFERENT FROM THE ONE ABOVE !!
		this.authors = authors;
		this.theme = theme;
		if (nbOfCopies < 0 || nbOfAvailableCopies > nbOfCopies) {
			throw new BadStockArgumentException("Invalid number of copies or available copies.");
		}
		this.NbOfCopies = nbOfCopies;
		this.NbOfAvailableCopies = nbOfAvailableCopies;
	}
        
	/**
         * Retrieves the number of available copies of the book.
         * @return the number of available copies in the library
         */
	public int getNbOfAvailableCopies() {
		return this.NbOfAvailableCopies;
	}
	
	/**
         * Retrieves the theme of the book.
         * @return the theme of the book as a String
         */
	public String getTheme() {
		return this.theme;
	}
        
	/**
         * Increases the number of available copies by one.
         * This method is used when a book is returned to the library.
         * @throws BadStockArgumentException if the number of available copies exceeds the total number of copies
         */
	
	public void increaseNbOfAvailableCopies() throws BadStockArgumentException {
		if(this.NbOfAvailableCopies >= this.NbOfCopies ) {
			throw new BadStockArgumentException("the limit of book has been exceeded");
		} else {
		this.NbOfAvailableCopies = this.getNbOfAvailableCopies() + 1 ;
		}
	}
	
	/**
         * Decreases the number of available copies by one.
         * This method is used when a book is borrowed from the library.
         * @throws BadStockArgumentException if there are no available copies to borrow
         */
	
	public void decreaseNbOfAvailableCopies() throws BadStockArgumentException {
		if(0 >= this.NbOfAvailableCopies ) {
			throw new BadStockArgumentException("There is no available Copy on this book");
		} else {
		this.NbOfAvailableCopies = this.getNbOfAvailableCopies() - 1 ;
		}
	}
	
	/**
         * Checks if the book is currently available for borrowing.
         * @return true if there is at least one available copy; false otherwise
         */
	public boolean isAvailable() {
		return (this.NbOfAvailableCopies > 0);
	}
	/**
         * Retrieves the ISBN of the book.
         * @return the ISBN of the book as a String
         */
	public String getISBN() {
		return this.ISBN;
	}
       /**
        * Retrieves the title of the book.
        * @return the title of the book as a String
        */
	public String getTitle() {
		return this.title;
	}
        /**
         * Retrieves the set of authors of the book.
         * @return a Set of Author objects representing the authors
         */
	public String getAuthor() {
		return this.authors;
	}
	/**
         * Retrieves the number of copies.
         * @return an Integer representing the number of copies
         */
	public int getNbOfCopies() {
		return this.NbOfCopies;
	}
        /**
         * Provides a string representation of the book details.
         * @return a string summary of the
	 */
	public String toString() {
		return "Book [ISBN=" + ISBN + ", title=" + title + ", author=" + authors + ", NbOfCopies="
				+ NbOfCopies + ", NbOfAvailableCopies=" + NbOfAvailableCopies + "]";
	}
	
}
