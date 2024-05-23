package JavaFX;

import cyBooks.*;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookSearch extends Application {

    private ListView<String> bookListView;
    private ComboBox<String> filterCriteriaComboBox;
    private TextField filterValueField;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Book Search");

        TextField searchField = new TextField();
        Button searchButton = new Button("Search");
        filterCriteriaComboBox = new ComboBox<>();
        filterValueField = new TextField();
        Button filterButton = new Button("Filter");
        Button last30DaysButton = new Button("Last 30 Days");
        Button returnButton = new Button("Return");
        bookListView = new ListView<>();

        filterCriteriaComboBox.getItems().addAll("Genre", "Publication Date", "Author", "Title");

        searchButton.setOnAction(e -> {
            // Perform search based on book ISBN
            String isbn = searchField.getText();
            fetchAndDisplayBooks(isbn, null, null);
        });

        filterButton.setOnAction(e -> {
            // Perform search based on filter criteria and value
            String criteria = filterCriteriaComboBox.getValue();
            String value = filterValueField.getText();
            fetchAndDisplayBooks(null, criteria, value);
        });

        last30DaysButton.setOnAction(e -> {
            // Show most borrowed books in the last 30 days
            new Last30DaysView().start(primaryStage);
        });

        returnButton.setOnAction(e -> new Home().start(primaryStage));

        // Fetch and display all books initially
        fetchAndDisplayBooks(null, null, null);

        GridPane filterPane = new GridPane();
        filterPane.setHgap(10);
        filterPane.setVgap(10);
        filterPane.add(new Label("Search by ISBN:"), 0, 0);
        filterPane.add(searchField, 1, 0);
        filterPane.add(searchButton, 2, 0);
        filterPane.add(new Label("Filter by:"), 0, 1);
        filterPane.add(filterCriteriaComboBox, 1, 1);
        filterPane.add(filterValueField, 2, 1);
        filterPane.add(filterButton, 3, 1);

        VBox vbox = new VBox(filterPane, bookListView, last30DaysButton, returnButton);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(vbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void fetchAndDisplayBooks(String isbn, String criteria, String value) {
        String query = "SELECT ISBN, title, author FROM Books";
        boolean hasCondition = false;

        if (isbn != null && !isbn.isEmpty()) {
            query += " WHERE ISBN = ?";
            hasCondition = true;
        } else if (criteria != null && value != null && !value.isEmpty()) {
            if (hasCondition) {
                query += " AND ";
            } else {
                query += " WHERE ";
            }

            switch (criteria) {
                case "Genre":
                    query += "theme = ?";
                    break;
                case "Publication Date":
                    query += "publication_date = ?";
                    break;
                case "Author":
                    query += "author = ?";
                    break;
                case "Title":
                    query += "title = ?";
                    break;
            }
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int paramIndex = 1;

            if (isbn != null && !isbn.isEmpty()) {
                preparedStatement.setString(paramIndex++, isbn);
            } else if (criteria != null && value != null && !value.isEmpty()) {
                preparedStatement.setString(paramIndex, value);
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            bookListView.getItems().clear();

            while (resultSet.next()) {
                String book = resultSet.getString("title") + " - " + resultSet.getString("author") + " - " + resultSet.getString("ISBN");
                bookListView.getItems().add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            bookListView.getItems().add("Failed to fetch books.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
