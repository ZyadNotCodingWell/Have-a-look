//Classe pour les operations sur la bdd comme : récupérer la liste des emprunts en retard, 
//Afficher une liste des livres les plus empruntés pendant les 30 derniers jours

package cyBooks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOperations {

    public static List<Borrows> getLateBorrows() {
        List<Borrows> lateBorrows = new ArrayList<>();
        String query = "SELECT br.id, br.date_of_operation, br.date_of_return, br.status, " +
                       "b.ISBN, b.title, b.theme, b.author, b.NbOfCopies, b.NbOfAvailableCopies, " +
                       "u.id AS user_id, u.surname, u.name, u.dateOfBirth, u.email, u.status AS user_status, " +
                       "u.currentlyBorrowedBooks, u.numberOfLateReturns " +
                       "FROM Borrows br " +
                       "JOIN Users u ON br.user_id = u.id " +
                       "JOIN Books b ON br.book_isbn = b.ISBN " +
                       "WHERE br.date_of_return < CURDATE() AND br.status = 'Ongoing'";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                try {
                    // Création de l'objet Book avec les paramètres dans l'ordre correct
                    Book book = new Book(
                        resultSet.getInt("NbOfCopies"),
                        resultSet.getString("ISBN"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("theme"),
                        resultSet.getInt("NbOfAvailableCopies")
                    );

                    // Création de l'objet User
                    User user = new User(
                        resultSet.getString("user_id"),
                        resultSet.getString("surname"),
                        resultSet.getString("name"),
                        resultSet.getDate("dateOfBirth"),
                        resultSet.getString("email"),
                        Status.valueOf(resultSet.getString("user_status")),
                        resultSet.getInt("numberOfLateReturns"),
                        resultSet.getInt("currentlyBorrowedBooks")
                    );

                    // Création de l'objet Borrows
                    Borrows borrow = new Borrows(
                        resultSet.getInt("id"),
                        book,
                        user,
                        resultSet.getDate("date_of_operation"),
                        resultSet.getDate("date_of_return"),
                        OpStatus.valueOf(resultSet.getString("status"))
                    );

                    // Ajout de l'emprunt en retard à la liste
                    lateBorrows.add(borrow);
                } catch (BadStockArgumentException e) {
                    e.printStackTrace();
                    // Vous pouvez gérer l'exception de manière appropriée ici, par exemple en ignorant l'entrée incorrecte
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lateBorrows;
    }

    public static List<BookBorrowStats> getMostBorrowedBooksLast30Days() {
        List<BookBorrowStats> mostBorrowedBooks = new ArrayList<>();
        String query = "SELECT b.ISBN, b.title, COUNT(br.book_isbn) AS nombre_emprunts " +
                       "FROM Books b " +
                       "JOIN Borrows br ON b.ISBN = br.book_isbn " +
                       "WHERE br.date_of_operation >= DATE_SUB(CURDATE(), INTERVAL 30 DAY) " +
                       "GROUP BY b.ISBN " +
                       "ORDER BY nombre_emprunts DESC";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String ISBN = resultSet.getString("ISBN");
                String title = resultSet.getString("title");
                int borrowCount = resultSet.getInt("nombre_emprunts");

                BookBorrowStats stats = new BookBorrowStats(ISBN, title, borrowCount);
                mostBorrowedBooks.add(stats);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mostBorrowedBooks;
    }

    public static class BookBorrowStats {
        private String ISBN;
        private String title;
        private int borrowCount;

        public BookBorrowStats(String ISBN, String title, int borrowCount) {
            this.ISBN = ISBN;
            this.title = title;
            this.borrowCount = borrowCount;
        }

        // Getters
        public String getISBN() {
            return ISBN;
        }

        public String getTitle() {
            return title;
        }

        public int getBorrowCount() {
            return borrowCount;
        }

        @Override
        public String toString() {
            return "BookBorrowStats{" +
                    "ISBN='" + ISBN + '\'' +
                    ", title='" + title + '\'' +
                    ", borrowCount=" + borrowCount +
                    '}';
        }
    }
}
