package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;
import model.UserInfo;

import java.net.URL;
import java.util.ResourceBundle;

public class UserInfoViewController implements Initializable {
    @FXML
    private TableView<UserInfo> table;

    @FXML
    private TableColumn<UserInfo, String> firstName;

    @FXML
    private TableColumn<UserInfo, String> number;

    @FXML
    private TableColumn<UserInfo, String> login;

    ObservableList<UserInfo> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        firstName.setCellValueFactory(new PropertyValueFactory<UserInfo, String>("firstName"));
        number.setCellValueFactory(new PropertyValueFactory<UserInfo, String>("number"));
        login.setCellValueFactory(new PropertyValueFactory<UserInfo, String>("login"));

        table.setItems(list);
    }

    public void addUserInfo(UserInfo userInfo){
        list.add(userInfo);
    }
}
