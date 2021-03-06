package view;

import controller.Listener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import model.UserInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
        if(checkForNumber(number.getText())==false){
            message.setText("Номер некорректный! Введите в формате 375336850000");
            return;
        }
        if(checkPassword(password.getText())==false){
            message.setText("Пароль слишком легкий! Он должен быть размером, более чем 6 символов и содержать буквы или цифры");
            return;
        }
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
        userInfoMap.put("number", number.getText());

        jsonObject.put("userInfo", userInfoMap);
        jsonObject.put("requestType", "REGISTRATION");
        Listener.send(jsonObject.toString());

        String response = Listener.listen();

        JSONObject jsonObjectFromServer = null;

        try {
            jsonObjectFromServer = (JSONObject) new JSONParser().parse(response);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String responseMessage = (String) jsonObjectFromServer.get("message");
        if(responseMessage.equals("LoginExists")) message.setText("Такой логин уже существует!");
        else if(responseMessage.equals("NumberExists")) message.setText("Такой номер уже существует!");
        else if(responseMessage.equals("SuccessfulRegistration")) {
            message.setText("Успешная регистрация!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        backToPreviousWindow(event);
    }

    private boolean checkForNumber(String number){
        Pattern pattern = Pattern.compile("\\d{7,}");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    private boolean checkPassword(String password){
        Pattern pattern = Pattern.compile("^(?=.*(\\w|[а-я]|[А-Я])).{8,20}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public void backToPreviousWindow(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/com/lepesha/fxml/loginView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
