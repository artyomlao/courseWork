module client {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires javafx.base;

    opens controller to javafx.graphics;
    opens view to javafx.fxml;
    opens model to javafx.base;
}