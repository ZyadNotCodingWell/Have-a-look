package JavaFX;

import cyBooks.*;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Last30DaysView extends Application {

    private ListView<String> bookListView;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Most Borrowed Books in Last 30 Days");

        bookListView = new ListView<>();
        Button returnButton = new Button("Return");

        returnButton.setOnAction(e -> new BookSearch().start(primaryStage));

        // Fetch and display most borrowed books in last 30 days
        fetchMostBorrowedBooksLast30Days();

        VBox vbox = new VBox(bookListView, returnButton);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(vbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void fetchMostBorrowedBooksLast30Days() {
        String query = "SELECT b.ISBN, b.title, b.author, COUNT(br.book_isbn) AS borrow_count " +
                       "FROM Books b " +
                       "JOIN Borrows br ON b.ISBN = br.book_isbn " +
                       "WHERE br.date_of_operation >= DATE_SUB(CURDATE(), INTERVAL 30 DAY) " +
                       "GROUP BY b.ISBN, b.title, b.author " +
                       "ORDER BY borrow_count DESC";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            bookListView.getItems().clear();

            while (resultSet.next()) {
                String book = resultSet.getString("title") + " - " + resultSet.getString("author") + " - " + resultSet.getString("ISBN") +
                              " (Borrowed " + resultSet.getInt("borrow_count") + " times)";
                bookListView.getItems().add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            bookListView.getItems().add("Failed to fetch most borrowed books.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
