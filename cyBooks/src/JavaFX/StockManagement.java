package com.example.demo2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import cyBooks.Book;

public class StockManagement extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Stock Management");

        // TableView to display stock items
        TableView<Book> table = new TableView<>();

        // Columns for TableView
        TableColumn<Book, String> bookIdCol = new TableColumn<>("Book ID");
        TableColumn<Book, String> statusCol = new TableColumn<>("Status");
        TableColumn<Book, String> borrowerCol = new TableColumn<>("Borrower");

        // Adding columns to TableView
        table.getColumns().addAll(bookIdCol, statusCol, borrowerCol);

        // Buttons for actions
        Button borrowButton = new Button("Borrow");
        Button returnButton = new Button("Return");

        // Setup buttons using UIHelper for keyboard and mouse interaction
        UIHelper.setupButton(borrowButton, "Borrow", this::borrowBook);
        UIHelper.setupButton(returnButton, "Return", this::returnBook);

        // VBox to organize elements vertically
        VBox root = new VBox();
        root.getChildren().addAll(table, borrowButton, returnButton);

        // Creating the main scene with VBox as root element
        Scene scene = new Scene(root, 600, 400);

        // Setting the scene on the primary stage
        primaryStage.setScene(scene);

        // Displaying the primary stage
        primaryStage.show();
    }

    // Méthode principale pour lancer l'application
    public static void main(String[] args) {
        launch(args);
    }

    private void borrowBook() {
        // Logique pour emprunter un livre
    }

    private void returnBook() {
        // Logique pour retourner un livre
    }
}
