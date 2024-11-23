module com.example.lab6fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens com.example.lab6fx to javafx.fxml;
    exports com.example.lab6fx;
}
