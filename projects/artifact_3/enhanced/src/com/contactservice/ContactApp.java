package com.contactservice;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ContactApp extends Application {

    private ContactService service = new ContactService();
    private TableView<Contact> table = new TableView<>();
    private ObservableList<Contact> observableContacts = FXCollections.observableArrayList();

    // Form fields
    private TextField tfId = new TextField();
    private TextField tfFirst = new TextField();
    private TextField tfLast = new TextField();
    private TextField tfPhone = new TextField();
    private TextField tfAddress = new TextField();
    
    @Override
    public void start(Stage primaryStage) {
        // Load existing contacts from database
    	try {
            DatabaseHelper.initDatabase();
            loadFromDatabaseToService();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not initialize/load database: " + e.getMessage());
        }
        
        // Table columns (property names must match Contact getters)
        TableColumn<Contact, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getContactID()));
        idCol.setPrefWidth(100);

        TableColumn<Contact, String> firstCol = new TableColumn<>("First Name");
        firstCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFirstName()));
        firstCol.setPrefWidth(150);

        TableColumn<Contact, String> lastCol = new TableColumn<>("Last Name");
        lastCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getLastName()));
        lastCol.setPrefWidth(150);

        TableColumn<Contact, String> phoneCol = new TableColumn<>("Phone Number");
        phoneCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPhoneNum()));
        phoneCol.setPrefWidth(200);

        TableColumn<Contact, String> addrCol = new TableColumn<>("Address");
        addrCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getAddress()));
        addrCol.setPrefWidth(250);

        table.getColumns().addAll(idCol, firstCol, lastCol, phoneCol, addrCol);

        observableContacts.setAll(service.contactList);
        table.setItems(observableContacts);
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // When selecting a row, populate form (ID shown but not editable)
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                tfId.setText(newSel.getContactID());
                tfFirst.setText(newSel.getFirstName());
                tfLast.setText(newSel.getLastName());
                tfPhone.setText(newSel.getPhoneNum());
                tfAddress.setText(newSel.getAddress());
                tfId.setDisable(true); // ID immutable for edits
            } else {
                clearForm();
            }
        });
        // Ensure table size is constrained to column(s) size
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        
        // Form layout
        tfId.setPromptText("Contact ID (auto-generated)");
        tfId.setPrefWidth(180);
        tfId.setDisable(true); // user cannot edit ID
        tfFirst.setPromptText("First Name");
        tfLast.setPromptText("Last Name");
        tfPhone.setPromptText("Phone # (10 digits)");
        tfAddress.setPromptText("Address");
        
        // Buttons for CRUD functionality
        Button btnAdd = new Button("Add");
        btnAdd.setOnAction(e -> onAdd());

        Button btnUpdate = new Button("Update");
        btnUpdate.setOnAction(e -> onUpdate());

        Button btnDelete = new Button("Delete");
        btnDelete.setOnAction(e -> onDelete());

        Button btnRefresh = new Button("Clear Selection");
        btnRefresh.setOnAction(e -> refreshFromDB());
        
        // Button to exit application
        Button btnExit = new Button("Exit");
        btnExit.setOnAction(e -> {
            primaryStage.close();
        });
        // Distinct style from other buttons
        btnExit.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");
        // Spacer to right-align exit button
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // HBoxes for text fields, CRUD, and exit buttons
        HBox textFields = new HBox(8, tfId, tfFirst, tfLast, tfPhone, tfAddress);
        HBox buttons = new HBox(8, btnAdd, btnUpdate, btnDelete, btnRefresh, spacer, btnExit);

        VBox root = new VBox(10, table, textFields, buttons);
        root.setPadding(new Insets(10));

        primaryStage.setScene(new Scene(root, 900, 480));
        primaryStage.setTitle("Contact Manager");
        primaryStage.show();
        
        
    }
    

    private void onAdd() {
        try {
            // Generate a unique ID
            String id = generateUniqueId();
            tfId.setText(id); // show generated id in the disabled field
             
            tfPhone.setTextFormatter(new TextFormatter<String>(change -> {
                String newText = change.getControlNewText();
                // Strip all non-digit characters
                newText = newText.replaceAll("[^0-9]", "");
                change.setText(newText);
                change.setRange(0, change.getControlText().length());
                return change;
            }));
            
            String first = tfFirst.getText().trim();
            String last = tfLast.getText().trim();
            String phone = tfPhone.getText().trim();
            String address = tfAddress.getText().trim();

            // Validate basic input. ID is auto-generated so not checked here.
            if (first.isEmpty() || last.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Validation", "Please fill in all fields.");
                return;
            }

            // Create Contact instance (this may throw IllegalArgumentException if invalid)
            Contact c = new Contact(id, first, last, phone, address);

            // Persist to DB
            DatabaseHelper.insertContact(c);

            // Add to service and UI
            service.contactList.add(c);
            observableContacts.add(c);
            clearForm();

        } catch (IllegalArgumentException ex) {
            showAlert(Alert.AlertType.ERROR, "Invalid input", ex.getMessage());
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Database error", ex.getMessage());
        }
    }

    private void onUpdate() {
        Contact selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No selection", "Select a contact to update.");
            return;
        }

        try {
            // Gather updated values (ID is immutable)
            String first = tfFirst.getText().trim();
            String last = tfLast.getText().trim();
            String phone = tfPhone.getText().trim();
            String address = tfAddress.getText().trim();

            if (first.isEmpty() || last.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Validation", "Please fill in all fields.");
                return;
            }

            // Update in-memory Contact via setters
            selected.setFirstName(first);
            selected.setLastName(last);
            selected.setPhoneNum(phone);
            selected.setAddress(address);

            // Persist update
            DatabaseHelper.updateContact(selected);

            // Refresh UI table and clear selected contact
            table.refresh();

        } catch (IllegalArgumentException ex) {
            showAlert(Alert.AlertType.ERROR, "Invalid input", ex.getMessage());
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Database error", ex.getMessage());
        }
    }

    private void onDelete() {
        Contact selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No selection", "Select a contact to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Deletion");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to delete \"" + selected.getFirstName() + " " + selected.getLastName() + "\"?");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) return;

        try {
            DatabaseHelper.deleteContact(selected.getContactID());
            service.contactList.removeIf(c -> c.getContactID().equals(selected.getContactID()));
            observableContacts.remove(selected);
            clearForm();
        } catch (SQLException ex) {
            showAlert(Alert.AlertType.ERROR, "Database error", ex.getMessage());
        }
    }

    private void loadFromDatabaseToService() throws SQLException {
        List<Contact> fromDb = DatabaseHelper.loadAllContacts();
        service.contactList.clear();
        service.contactList.addAll(fromDb);
    }

    private void refreshFromDB() {
        try {
            loadFromDatabaseToService();
            observableContacts.setAll(service.contactList);
            table.refresh();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database error", "Failed to refresh: " + e.getMessage());
        }
    }

    private void clearForm() {
        tfId.clear();
        tfFirst.clear();
        tfLast.clear();
        tfPhone.clear();
        tfAddress.clear();
        tfId.setDisable(true);
        table.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(content);
        a.showAndWait();
    }

    /**
     * Generate a short unique ID (10  chars) and ensure it doesn't collide
     * with existing IDs in the in-memory list which is kept in sync with DB.
     */
    private String generateUniqueId() {
        String id;
        do {
            id = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        } while (idExists(id));
        return id;
    }

    private boolean idExists(String id) {
        for (Contact c : service.contactList) {
            if (c.getContactID().equals(id)) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }
}