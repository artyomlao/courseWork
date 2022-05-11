package model;

import controller.MainController;
import javafx.fxml.FXMLLoader;
import view.RegistrationViewController;

import java.io.IOException;

public class ResponseChecker {
    public void checkResponseType(String message){
        if(message.equals("Login")){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/lepesha/registrationView.fxml"));
            try {
                fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            RegistrationViewController registrationViewController = fxmlLoader.getController();
            registrationViewController.setErrorText("пиздец");
        }
        else if(message.equals("Number")){
            System.out.println("такой номер уже существует, братик");
        }
    }
}
