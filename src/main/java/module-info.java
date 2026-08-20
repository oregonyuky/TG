module com.example.tg {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tg to javafx.fxml;
    opens com.example.tg.controller to javafx.fxml;
    exports com.example.tg;
}