package JavaFX;

import cyBooks.*;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;

public class CreateBorrow extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Create Borrow");

        GridPane grid = new GridPane();
        Label userIdLabel = new Label("User ID:");
        TextField userIdField = new TextField();
        Label isbnLabel = new Label("Book ISBN:");
        TextField isbnField = new TextField();

        Button saveButton = new Button("Save");
        Button returnButton = new Button("Return");

        saveButton.setOnAction(e -> {
            // Create borrow record in the database
            String userId = userIdField.getText();
            String isbn = isbnField.getText();
            // Additional validation here

            try {
                Book book = new DataParser().getBook(isbn);
                User user = new DataParser().getUser(userId);
                if (book != null && user != null) {
                    Borrows borrow = new Borrows(book, user);
                    InsertIntoDataBase db = new InsertIntoDataBase();
                    db.addBorrow(borrow);
                    System.out.println("Borrow created successfully");
                } else {
                    System.out.println("Book or User not found");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        returnButton.setOnAction(e -> new BorrowSearch().start(primaryStage));

        grid.add(userIdLabel, 0, 0);
        grid.add(userIdField, 1, 0);
        grid.add(isbnLabel, 0, 1);
        grid.add(isbnField, 1, 1);
        grid.add(saveButton, 0, 2);
        grid.add(returnButton, 1, 2);

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
