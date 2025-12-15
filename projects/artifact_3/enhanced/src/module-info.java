// Module descriptor for ContactService
module ContactService {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports com.contactservice;
    
    opens com.contactservice to javafx.fxml;
}