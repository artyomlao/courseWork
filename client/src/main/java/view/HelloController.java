package view;

import controller.Listener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label welcomeText;



    @FXML
    protected void onHelloButtonClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/lepesha/loginView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        Listener listener = new Listener();
        listener.start();
        stage.show();
        //welcomeText.setText("Welcome to JavaFX Application!");
    }
}