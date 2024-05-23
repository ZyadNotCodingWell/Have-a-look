package JavaFX;

import cyBooks.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BorrowSearch extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Borrow Search");

        TextField userIdField = new TextField();
        TextField isbnField = new TextField();
        Button searchButton = new Button("Search");
        Button createBorrowButton = new Button("Create Borrow");
        Button returnButton = new Button("Return");

        searchButton.setOnAction(e -> {
            // Perform search based on user ID and/or book ISBN
            String userId = userIdField.getText();
            String isbn = isbnField.getText();
            // Search logic here
        });

        createBorrowButton.setOnAction(e -> new CreateBorrow().start(primaryStage));

        returnButton.setOnAction(e -> new Home().start(primaryStage));

        VBox vbox = new VBox(new HBox(new Label("Search by User ID:"), userIdField), new HBox(new Label("Search by ISBN:"), isbnField, searchButton), createBorrowButton, returnButton);
        Scene scene = new Scene(vbox, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

