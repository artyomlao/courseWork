module com.lepesha.client {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.lepesha to javafx.fxml;

    exports com.lepesha.controller;
    opens com.lepesha.controller to javafx.fxml;
    exports com.lepesha.view;
    opens com.lepesha.view to javafx.fxml;
}