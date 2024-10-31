module com.example.ejerciciom {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ejerciciom to javafx.fxml;
    exports com.example.ejerciciom;
}