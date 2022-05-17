package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;

import java.net.URL;
import java.util.ResourceBundle;

public class AllOrdersViewController implements Initializable {
    @FXML
    private TableColumn<Item, Integer> orderId;

    @FXML
    private TableColumn<Item, String> name;

    @FXML
    private TableColumn<Item, Double> price;

    @FXML
    private TableColumn<Item, Integer> quantity;

    @FXML
    private TableView<Item> table;

    private ObservableList<Item> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderId.setCellValueFactory(new PropertyValueFactory<Item, Integer>("orderId"));
        name.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        price.setCellValueFactory(new PropertyValueFactory<Item, Double>("price"));
        quantity.setCellValueFactory(new PropertyValueFactory<Item, Integer>("quantity"));

        table.setItems(list);
    }

    public void addDataToList(Item item){
        list.add(item);
    }
}
