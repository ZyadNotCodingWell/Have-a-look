package cyBooks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class DataParser {

    private DatabaseManager dbManager = new DatabaseManager();
    private Connection connection;

    public DataParser() {
        this.connection = DatabaseConnection.getConnection();
    }

    public Set<Borrows> parseBorrows() throws SQLException, BadStockArgumentException {
        ResultSet rs = dbManager.getBorrows();
        Set<Borrows> borrowsSet = new HashSet<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String bookIsbn = rs.getString("book_isbn");
            String userId = rs.getString("user_id");
            Date dateOfOperation = rs.getDate("date_of_operation");
            Date dateOfReturn = rs.getDate("date_of_return");
            OpStatus status = OpStatus.valueOf(rs.getString("status"));

            Book book = getBook(bookIsbn);
            User user = getUser(userId);

            Borrows borrow = new Borrows(id, book, user, dateOfOperation, dateOfReturn, status);
            borrowsSet.add(borrow);
        }
        return borrowsSet;
    }



    public Book getBook(String isbn) throws SQLException, BadStockArgumentException {
        String query = "SELECT * FROM Books WHERE ISBN = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, isbn);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String title = rs.getString("title");
                    String theme = rs.getString("theme");
                    int nbOfCopies = rs.getInt("nbOfCopies");
                    int nbOfAvailableCopies = rs.getInt("nbOfAvailableCopies");
                    String author = rs.getString("author");

                    Book book = new Book(nbOfCopies, isbn, title, author, theme, nbOfAvailableCopies); // Utilisé le constructeur spécifique à ça

                    return book;
                } else {
                    return null;
                }
            }
        }
    }

    public User getUser(String id) throws SQLException {
        String query = "SELECT * FROM Users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String surname = rs.getString("surname");
                    String name = rs.getString("name");
                    Date dateOfBirth = rs.getDate("dateOfBirth");
                    String email = rs.getString("email");
                    Status status = Status.valueOf(rs.getString("status"));
                    int currentlyBorrowedBooks = rs.getInt("currentlyBorrowedBooks");
                    int numberOfLateReturns = rs.getInt("numberOfLateReturns");

                    return new User(id, surname, name, dateOfBirth, email, status, currentlyBorrowedBooks, numberOfLateReturns); // I should also make a constructor here promise
                } else {
                    return null;
                }
            }
        }
    }
    public Set<Book> getAvailableBooks() throws SQLException, BadStockArgumentException {
        Set<Book> availableBooks = new HashSet<>();
        ResultSet rs = dbManager.getAvailableBooks();
        while (rs.next()) {
            String isbn = rs.getString("ISBN");
            String title = rs.getString("title");
            String theme = rs.getString("theme");
            int nbOfCopies = rs.getInt("nbOfCopies");
            int nbOfAvailableCopies = rs.getInt("NbOfAvailableCopies");
            String authors = rs.getString("author"); 

            Book book = new Book(nbOfCopies, isbn, title, authors, theme, nbOfAvailableCopies);
            availableBooks.add(book);
        }
        return availableBooks;
    }
}
