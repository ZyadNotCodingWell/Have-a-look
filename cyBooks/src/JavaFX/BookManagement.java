package com.example.demo2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import cyBooks.Book; 

public class BookManagement extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Book Management");

        // TableView to display books
        TableView<Book> table = new TableView<>();

        // Columns for TableView
        TableColumn<Book, String> titleCol = new TableColumn<>("Title");
        TableColumn<Book, String> authorCol = new TableColumn<>("Author");
        TableColumn<Book, String> themeCol = new TableColumn<>("Theme"); // Adding theme column
        TableColumn<Book, Integer> copiesCol = new TableColumn<>("Number of Copies"); // Adding copies column

        // Adding columns to TableView
        table.getColumns().addAll(titleCol, authorCol, themeCol, copiesCol);

        // Buttons for actions
        Button addButton = new Button("Add");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");

         // Using UIHelper to setup buttons
        UIHelper.setupButton(addButton, "Add", this::addBook);
        UIHelper.setupButton(updateButton, "Update", this::updateBook);
        UIHelper.setupButton(deleteButton, "Delete", this::deleteBook);

        // VBox to organize elements vertically
        VBox root = new VBox();
        root.getChildren().addAll(table, addButton, updateButton, deleteButton);

        // Creating the main scene with VBox as root element
        Scene scene = new Scene(root, 600, 400);

        // Setting the scene on the primary stage
        primaryStage.setScene(scene);

        // Displaying the primary stage
        primaryStage.show();
    }


	private void addBook() {
	}

	private void updateBook() {
	}

	private void deleteBook() {
	}
	
	public static void main(String[] args) {
        launch(args);
    }
}
