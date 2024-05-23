package cyBooks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseUpdater {

    private Connection connect() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    public void updateBorrow(Borrows borrow) throws SQLException {
        Connection conn = connect();

        // Update the Borrows table
        String updateBorrows = "UPDATE Borrows SET status = ?, date_of_return = ? WHERE borrow_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateBorrows)) {
            pstmt.setString(1, borrow.getStatus().name());
            pstmt.setDate(2, new java.sql.Date(borrow.getDateOfReturn().getTime()));
            pstmt.setInt(3, borrow.getId());
            pstmt.executeUpdate();
        }

        // Update the Users table if necessary
        User user = borrow.getUser();
        String updateUser = "UPDATE Users SET status = ?, currentlyBorrowedBooks = ?, numberOfLateReturns = ? WHERE id = ?";
        try (PreparedStatement pstmtUser = conn.prepareStatement(updateUser)) {
            pstmtUser.setString(1, user.getStatus().name());
            pstmtUser.setInt(2, user.getCurrentlyBorrowedBooks());
            pstmtUser.setInt(3, user.getNumberOfLateReturns());
            pstmtUser.setString(4, user.getId());
            pstmtUser.executeUpdate();
        }

        // Update the Books table if necessary
        Book book = borrow.getBook();
        String updateBook = "UPDATE Books SET NbOfAvailableCopies = ? WHERE ISBN = ?";
        try (PreparedStatement pstmtBook = conn.prepareStatement(updateBook)) {
            pstmtBook.setInt(1, book.getNbOfAvailableCopies());
            pstmtBook.setString(2, book.getISBN());
            pstmtBook.executeUpdate();
        }
    }
}


