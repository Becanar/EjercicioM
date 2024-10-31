module com.example.ejerciciol {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql.rowset;


    exports com.example.ejerciciom.app;
    opens com.example.ejerciciom.model to javafx.base;
    opens com.example.ejerciciom.app to javafx.fxml;
    exports com.example.ejerciciom.controladores;
    opens com.example.ejerciciom.controladores to javafx.fxml;
}