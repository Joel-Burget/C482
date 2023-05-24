module com.example.c482 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens kinkead.firstscreen to javafx.fxml;
    exports kinkead.firstscreen;
}