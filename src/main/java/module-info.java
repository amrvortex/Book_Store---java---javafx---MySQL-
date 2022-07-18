module Controller {
        requires javafx.controls;
        requires javafx.fxml;
        requires java.sql;
    requires mysql.connector.java;
    requires jasperreports;

    opens Controller to javafx.fxml;
        opens View to javafx.fxml;
        exports Controller;
        exports View;
}