module com.lepesha.client {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.lepesha.client to javafx.fxml;

    exports com.lepesha.client.controller;
    opens com.lepesha.client.controller to javafx.fxml;
    exports com.lepesha.client.view;
    opens com.lepesha.client.view to javafx.fxml;
}