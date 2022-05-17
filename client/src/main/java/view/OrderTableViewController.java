package view;

import controller.Listener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Item;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

public class OrderTableViewController implements Initializable {

    @FXML
    private TableColumn<Item, String> name;

    @FXML
    private TableColumn<Item, Double> price;

    @FXML
    private TableColumn<Item, Integer> quantity;

    @FXML
    private TableView<Item> table;

    @FXML
    private HBox buttonHBox;

    private String authorisedLogin;
    private String authorisedNumber;
    private String authorisedFirstName;
    private String welcomeLabel;

    ObservableList<Item> list = FXCollections.observableArrayList();

    public boolean addData(String name, double price, int quantity){//true - добавлены данные, false - не добавлены
        Iterator<Item> itr = list.iterator();
        while(itr.hasNext()){
            if(itr.next().getName().equals(name)) return false;
        }
        list.add(new Item(name,price,quantity));
        return true;
    }

    public void setUserMenuWindowInfo(String authorisedLogin, String authorisedNumber,
                                      String authorisedFirstName, String welcomeLabel){
        this.authorisedLogin = authorisedLogin;
        this.authorisedNumber = authorisedNumber;
        this.authorisedFirstName =authorisedFirstName;
        this.welcomeLabel = welcomeLabel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setCellValueFactory(new PropertyValueFactory<Item, String >("name"));
        price.setCellValueFactory(new PropertyValueFactory<Item, Double>("price"));
        quantity.setCellValueFactory(new PropertyValueFactory<Item, Integer>("quantity"));

        table.setItems(list);
    }

    public void deleteRow(ActionEvent event) {
        table.getItems().removeAll(table.getSelectionModel().getSelectedItem());
    }

    public void saveOrder(ActionEvent event) {
        JSONObject jsonObject = new JSONObject();

        for (int i = 0; i < list.size(); i++) {
            Map itemMap = new HashMap();
            itemMap.put("name", list.get(i).getName());
            itemMap.put("price", list.get(i).getPrice().toString());
            itemMap.put("quantity", list.get(i).getQuantity().toString());
            jsonObject.put("item" + i, itemMap);
        }
        jsonObject.put("size", list.size());
        jsonObject.put("login", authorisedLogin);
        jsonObject.put("requestType", "MAKE_ORDER");

        String json = jsonObject.toJSONString();
        System.out.println(json);
        Listener.send(json);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/lepesha/fxml/userMenuView.fxml"));

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UserMenuViewController userMenuViewController= fxmlLoader.getController();
        Parent root = fxmlLoader.getRoot();

        userMenuViewController.setFields
                        (authorisedLogin,
                        authorisedNumber,
                        authorisedFirstName,
                        welcomeLabel);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }
}
