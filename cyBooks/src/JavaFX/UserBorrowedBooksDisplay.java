package com.example.demo2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import cyBooks.User;
import cyBooks.UserDatabase;

public class UserBorrowedBooksDisplay extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Window title
        primaryStage.setTitle("User Borrowed Books Display");

        // Creating a VBox to organize elements vertically
        VBox vbox = new VBox();
        vbox.setSpacing(10);

        // Creating a label to display the number of borrowed books
        Label borrowedBooksLabel = new Label("Current Borrowed Books Count: ");

        // Retrieving the user (replace 1 with the desired user ID)
        User user = UserDatabase.getUserById("1");

        // Displaying the current number of borrowed books by the user
        int borrowedBooksCount = user.getCurrentlyBorrowedBooks(); // Adjusted method name to likely correct version
        Label borrowedBooksCountLabel = new Label(String.valueOf(borrowedBooksCount));

        // Adding elements to the VBox
        vbox.getChildren().addAll(borrowedBooksLabel, borrowedBooksCountLabel);

        // Creating the main scene with VBox as the root element
        Scene scene = new Scene(vbox, 300, 200);

        // Setting the scene on the primary stage
        primaryStage.setScene(scene);

        // Displaying the primary stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

