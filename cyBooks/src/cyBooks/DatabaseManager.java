package cyBooks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {

    private Connection connect() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    public ResultSet getBorrows() throws SQLException {
        Connection conn = connect();
        String sql = "SELECT * FROM Borrows";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        return pstmt.executeQuery();
    }

    public ResultSet getBookByISBN(String isbn) throws SQLException {
        Connection conn = connect();
        String sql = "SELECT * FROM Books WHERE ISBN = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, isbn);
        return pstmt.executeQuery();
    }

    public ResultSet getUserById(String userId) throws SQLException {
        Connection conn = connect();
        String sql = "SELECT * FROM Users WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, userId);
        return pstmt.executeQuery();
    }

    public ResultSet getAvailableBooks() throws SQLException {
        Connection conn = connect();
        String sql = "SELECT * FROM Books WHERE NbOfAvailableCopies > 0";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        return pstmt.executeQuery();
    }
}
