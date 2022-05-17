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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Map;

public class LoginViewController {
    @FXML
    private Hyperlink loginSignUpHyperlink;

    @FXML
    private Button authoriseButton;

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label errorMessage;

    private String authorisedLogin;
    private String authorisedNumber;
    private String authorisedFirstName;


    public void register(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/lepesha/fxml/registrationView.fxml"));

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = fxmlLoader.getRoot();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    public void authorise(ActionEvent event) throws IOException {
        String login = loginField.getText();
        String password = passwordField.getText();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("login", login);
        jsonObject.put("password", password);
        jsonObject.put("requestType", "AUTHORISATION");
        Listener.send(jsonObject.toJSONString());

        String response = Listener.listen();

        JSONObject jsonObjectFromServer = null;
        try {
            jsonObjectFromServer = (JSONObject) new JSONParser().parse(response);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String message = (String) jsonObjectFromServer.get("message");

        if (message.equals("IncorrectLoginOrPassword")) errorMessage.setText("Неверный логин или пароль");
        else if(message.equals("SuccessfulAdminAuthorisation")){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/lepesha/fxml/adminMenuView.fxml"));
            Parent root = null;

            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
        else if (message.equals("SuccessfulAuthorisation")) {
            Map userInfoMap = (Map) jsonObjectFromServer.get("userInfo");

            authorisedLogin = (String) userInfoMap.get("login");
            authorisedFirstName = (String) userInfoMap.get("firstName");
            authorisedNumber = (String) userInfoMap.get("number");

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/lepesha/fxml/userMenuView.fxml"));
            Parent root = null;

            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            UserMenuViewController userMenuViewController = fxmlLoader.getController();

            userMenuViewController.setFields
                    (authorisedLogin,
                            authorisedNumber,
                            authorisedFirstName,
                            authorisedFirstName);

            Scene scene = new Scene(root, 600, 400);
            stage.setScene(scene);
            stage.show();
        }

        System.out.println(jsonObject.toJSONString());
    }
}
