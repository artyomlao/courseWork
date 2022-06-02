package view;

import controller.Listener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.UserInfo;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Map;

public class AdminMenuViewController {
    @FXML
    private Label bestItemLabel;

    private String login;

    public void setLogin(String login) {
        this.login = login;
    }

    public void addDecision(ActionEvent event) {
        FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/com/lepesha/fxml/addDecisionView.fxml"));
        Parent root = null;

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddDecisionViewController addDecisionViewController = fxmlLoader.getController();

        addDecisionViewController.setLogin(login);

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void solutionView(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/lepesha/fxml/showDecisionView.fxml"));
        Parent root = null;

        try{
            root = fxmlLoader.load();
        } catch(IOException e){
            e.printStackTrace();
        }

        ShowDecisionViewController showDecisionViewController = fxmlLoader.getController();

        showDecisionViewController.setThisFXMLLoader(fxmlLoader);

        showDecisionViewController.setData();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void userView(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/lepesha/fxml/userInfoView.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("requestType", "GET_USERINFO");

        Listener.send(jsonObject.toJSONString());

        String response = Listener.listen();

        JSONObject jsonObjectFromServer=null;

        UserInfoViewController userInfoViewController = fxmlLoader.getController();

        try {
            jsonObjectFromServer = (JSONObject) new JSONParser().parse(response);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < Integer.parseInt((String) jsonObjectFromServer.get("size")); i++) {
            Map userMap = (Map) jsonObjectFromServer.get("user" + i);

            UserInfo userInfo = new UserInfo();

            userInfo.setLogin((String) userMap.get("login"));
            userInfo.setFirstName((String) userMap.get("firstName"));
            userInfo.setNumber((String) userMap.get("number"));

            userInfoViewController.addUserInfo(userInfo);
        }

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void logOut(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/lepesha/fxml/loginView.fxml"));

        Parent root=null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void bestItem(ActionEvent event) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("requestType", "GET_BEST_ITEM");

        Listener.send(jsonObject.toJSONString());

        String response = Listener.listen();

        JSONObject jsonObjectFromServer = null;
        try {
            jsonObjectFromServer = (JSONObject) new JSONParser().parse(response);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(jsonObjectFromServer.get("requestMessage").equals("noGoodItems")) {
            bestItemLabel.setText("Измерения всех товаров неточны! Проверьте корректность введённых данных");
        } else{
            String name = (String) jsonObjectFromServer.get("name");
            String avgEfficiency = (String) jsonObjectFromServer.get("avgEfficiency");
            String dispersion = (String) jsonObjectFromServer.get("dispersion");

            bestItemLabel.setText("Название: " + name + ", Средняя эффективность: " + avgEfficiency + ", Дисперсия: " + dispersion);
        }
    }
}
