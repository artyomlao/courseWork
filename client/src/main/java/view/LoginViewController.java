package view;

import controller.Listener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;

public class LoginViewController {
    @FXML
    private Hyperlink loginSignUpHyperlink;

    @FXML
    private Button authoriseButton;

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    public void register(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/lepesha/registrationView.fxml"));

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = fxmlLoader.getRoot();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void authorise(ActionEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("login", login);
        jsonObject.put("password", password);
        jsonObject.put("requestType", "AUTHORISATION");
        Listener.send(jsonObject.toJSONString() + "\n");
        System.out.println(jsonObject.toJSONString());
    }
}
