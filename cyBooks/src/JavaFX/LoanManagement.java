package com.example.demo2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import cyBooks.Borrows;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * LoanManagement is a JavaFX application that manages loan transactions in a library system.
 * It displays the current loans in a TableView and allows the user to extend the loan period or return books.
 */
public class LoanManagement extends Application {

    /**
     * The main entry point for the JavaFX application.
     *
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Loan Management");

        // TableView to display loans
        TableView<Borrows> table = new TableView<>(); // Using Borrows as the type for TableView

        // Columns for TableView
        TableColumn<Borrows, String> userIdCol = new TableColumn<>("User ID");
        TableColumn<Borrows, String> bookIdCol = new TableColumn<>("Book ID");
        TableColumn<Borrows, String> borrowDateCol = new TableColumn<>("Borrow Date");
        TableColumn<Borrows, String> returnDateCol = new TableColumn<>("Return Date");

        // Adding columns to TableView
        table.getColumns().addAll(userIdCol, bookIdCol, borrowDateCol, returnDateCol);

        // Buttons for actions
        Button extendButton = new Button("Extend");
        Button returnButton = new Button("Return");

        // Setup buttons with EventHandler for keyboard and mouse interaction
        extendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Borrows selectedBorrow = table.getSelectionModel().getSelectedItem();
                if (selectedBorrow != null) {
                    selectedBorrow.extendPeriod();
                }
            }
        });

        returnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Borrows selectedBorrow = table.getSelectionModel().getSelectedItem();
                if (selectedBorrow != null) {
                    try {
                        selectedBorrow.returnBook();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // VBox to organize elements vertically
        VBox root = new VBox();
        root.getChildren().addAll(table, extendButton, returnButton);

        // Creating the main scene with VBox as root element
        Scene scene = new Scene(root, 600, 400);

        // Setting the scene on the primary stage
        primaryStage.setScene(scene);

        // Displaying the primary stage
        primaryStage.show();
    }

    /**
     * The main method which launches the JavaFX application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
