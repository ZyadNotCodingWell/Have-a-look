package cyBooks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertIntoDataBase {
    public void addBook(Book book) throws SQLException {
        String query = "INSERT INTO Books (ISBN, title, author, theme, NbOfCopies, NbOfAvailableCopies) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
             
            preparedStatement.setString(1, book.getISBN());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setString(4, book.getTheme());
            preparedStatement.setInt(5, book.getNbOfCopies());
            preparedStatement.setInt(6, book.getNbOfAvailableCopies());
            
            preparedStatement.executeUpdate();
        }
    }
    
    public void addUser(User user) throws SQLException {
        String query = "INSERT INTO Users (id, surname, name, dateOfBirth, email, status, currentlyBorrowedBooks, numberOfLateReturns) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
             
            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setDate(4, user.getDateOfBirth());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getStatus().toString());
            preparedStatement.setInt(7, user.getCurrentlyBorrowedBooks());
            preparedStatement.setInt(8, user.getNumberOfLateReturns());
            
            preparedStatement.executeUpdate();
        }
    }

    public void addBorrow(Borrows borrow) throws SQLException {
        String query = "INSERT INTO Borrows (id, book_isbn, user_id, date_of_operation, date_of_return, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, borrow.getId());
            preparedStatement.setString(2, borrow.getBook().getISBN());
            preparedStatement.setString(3, borrow.getUser().getId());
            preparedStatement.setDate(4, new java.sql.Date(borrow.getDateOfOperation().getTime()));
            preparedStatement.setDate(5, new java.sql.Date(borrow.getDateOfReturn().getTime()));
            preparedStatement.setString(6, borrow.getStatus().toString());

            preparedStatement.executeUpdate();
        }
    }
}
