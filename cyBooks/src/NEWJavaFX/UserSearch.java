package JavaFX;

import cyBooks.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class UserSearch extends Application {

    private ListView<String> userListView;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("User Search");

        TextField searchField = new TextField();
        Button searchButton = new Button("Search");
        Button addUserButton = new Button("Add User");
        Button returnButton = new Button("Return");
        userListView = new ListView<>();

        searchButton.setOnAction(e -> {
            // Perform search based on user ID
            String userId = searchField.getText();
            fetchAndDisplayUsers(userId);
        });


        addUserButton.setOnAction(e -> new UserRegistration().start(primaryStage));

        returnButton.setOnAction(e -> new Home().start(primaryStage));

        // Fetch and display all users initially
        fetchAndDisplayUsers(null);

        VBox vbox = new VBox(new HBox(new Label("Search by ID:"), searchField, searchButton), userListView, addUserButton, returnButton);
        Scene scene = new Scene(vbox, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void fetchAndDisplayUsers(String userId) {
        String query = "SELECT id, surname, name FROM Users";
        if (userId != null && !userId.isEmpty()) {
            query += " WHERE id = ?";
        }
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            if (userId != null && !userId.isEmpty()) {
                preparedStatement.setString(1, userId);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            userListView.getItems().clear();
            while (resultSet.next()) {
                String user = resultSet.getString("id") + " - " + resultSet.getString("surname") + ", " + resultSet.getString("name");
                userListView.getItems().add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            userListView.getItems().add("Failed to fetch users.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

