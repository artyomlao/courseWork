package view;

import controller.Listener;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import model.UserInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.json.simple.*;

import java.util.HashMap;
import java.util.Map;


public class RegistrationViewController {

    @FXML
    private TextField firstName;

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private TextField number;

    @FXML
    private Button loginSignUpButton;

    @FXML
    private Label message;

    public void setErrorText( String text )
    {
        message.setText( text ) ;
    }

    @FXML
    void register(ActionEvent event) {
        UserInfo userInfo = new UserInfo();

        userInfo.setFirstName(firstName.getText());
        userInfo.setLogin(login.getText());
        userInfo.setPassword(password.getText());
        userInfo.setNumber(number.getText());

        JSONObject jsonObject = new JSONObject();

        Map userInfoMap = new HashMap();
        userInfoMap.put("firstName", firstName.getText());
        userInfoMap.put("login", login.getText());
        userInfoMap.put("password", password.getText());
        userInfoMap.put("phoneNumber", number.getText());

        jsonObject.put("userInfo", userInfoMap);
        jsonObject.put("requestType", "REGISTRATION");
        Listener.send(jsonObject.toString() + "\n");
        System.out.println(jsonObject.toString());
    }
}
