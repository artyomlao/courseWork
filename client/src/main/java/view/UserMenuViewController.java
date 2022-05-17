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
import model.Item;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarInputStream;

public class UserMenuViewController {
    @FXML
    private Label firstName;

    @FXML
    private Label login;

    @FXML
    private Label number;

    @FXML
    private Label welcomeLabel;

    public void setFields(String loginLabel, String numberLabel, String firstNameLabel, String welcomeLabel){
        this.login.setText(loginLabel);
        this.number.setText(numberLabel);
        this.firstName.setText(firstNameLabel);
        this.welcomeLabel.setText(welcomeLabel);
    }

    @FXML
    void addOffer(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/lepesha/fxml/orderView.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            OrderViewController orderViewController = fxmlLoader.getController();
            orderViewController.setUserMenuWindowInfo(login.getText(), number.getText(), firstName.getText(), welcomeLabel.getText());

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showOffers(ActionEvent event) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("requestType", "GET_ORDER");
        jsonObject.put("login", login.getText());

        Listener.send(jsonObject.toJSONString());

        String response = Listener.listen();
        JSONObject jsonObjectFromServer = null;
        try {
            jsonObjectFromServer = (JSONObject) new JSONParser().parse(response);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        FXMLLoader fxmlOrders = new FXMLLoader(getClass().getResource("/com/lepesha/fxml/allOrdersView.fxml"));
        try {
            fxmlOrders.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = fxmlOrders.getRoot();

        AllOrdersViewController allOrdersViewController = fxmlOrders.getController();

        for (int i = 0; i < Integer.parseInt((String) jsonObjectFromServer.get("size")); i++) {
            Map orderedItem = (Map) jsonObjectFromServer.get("orderedItem" + i);

            Item item = new Item();

            item.setOrderId(Integer.parseInt((String) orderedItem.get("orderId")));
            item.setQuantity(Integer.parseInt((String) orderedItem.get("quantity")));
            item.setPrice(Double.parseDouble((String) orderedItem.get("price")));
            item.setName((String) orderedItem.get("name"));

            allOrdersViewController.addDataToList(item);
        }

        Stage stage =new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(new Scene(root));
        stage.setTitle("TableView");
        stage.show();
    }

}
