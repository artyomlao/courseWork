module client {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;

    opens controller to javafx.graphics;
    opens view to javafx.fxml;
}