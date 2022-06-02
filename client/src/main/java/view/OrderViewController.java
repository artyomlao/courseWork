package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Item;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OrderViewController implements Initializable {

    public static final String CURRENCY = "р.";

    @FXML
    private VBox chosenItem;

    @FXML
    private ImageView imgItem;

    @FXML
    private Label nameItemLabel;

    @FXML
    private Label priceItemLabel;

    @FXML
    private ComboBox<Integer> quantity = new ComboBox<>();

    @FXML
    private Button searchButtonPressed;

    @FXML
    private TextField textFieldSearch;

    @FXML
    private GridPane grid;

    @FXML
    private Label messageLabel;

    private String authorisedLogin;
    private String authorisedNumber;
    private String authorisedFirstName;
    private String welcomeLabel;



    private List<Item> itemList = new ArrayList<>();

    private MyListener myListener;

    private OrderTableViewController orderTableViewController;
    private FXMLLoader orderTableViewLoader;
    private Parent orderTableViewRoot;
    private Stage stage;// this field are for scene with TableView


    public void setUserMenuWindowInfo(String authorisedLogin, String authorisedNumber,
                                      String authorisedFirstName, String welcomeLabel){
        this.authorisedLogin = authorisedLogin;
        this.authorisedNumber = authorisedNumber;
        this.authorisedFirstName =authorisedFirstName;
        this.welcomeLabel = welcomeLabel;
        orderTableViewController.setUserMenuWindowInfo(authorisedLogin, authorisedNumber, authorisedFirstName, welcomeLabel);
    }

    private List<Item> getItemList(){
        List<Item> itemList = new ArrayList<>();
        Item item;

        item = new Item();
        item.setName("Пицца");
        item.setPrice(7.99);
        item.setColor("#e1da07");
        item.setImage("/com/lepesha/img/pizza.png");
        itemList.add(item);

        item = new Item();
        item.setName("Бургер");
        item.setPrice(4.99);
        item.setColor("#ffaeae");
        item.setImage("/com/lepesha/img/burger.png");
        itemList.add(item);

        item = new Item();
        item.setName("Кофе");
        item.setPrice(2.99);
        item.setColor("#b9b984");
        item.setImage("/com/lepesha/img/coffee.png");
        itemList.add(item);

        item = new Item();
        item.setName("Круассан");
        item.setPrice(3.99);
        item.setColor("#e8bb85");
        item.setImage("/com/lepesha/img/croissant.png");
        itemList.add(item);

        item = new Item();
        item.setName("Картошка");
        item.setPrice(4.59);
        item.setColor("#f8f831");
        item.setImage("/com/lepesha/img/potato.png");
        itemList.add(item);

        item = new Item();
        item.setName("Ролл");
        item.setPrice(5.99);
        item.setColor("#75ee4c");
        item.setImage("/com/lepesha/img/roll.png");
        itemList.add(item);

        item = new Item();
        item.setName("Стейк");
        item.setPrice(6.59);
        item.setColor("#636aee");
        item.setImage("/com/lepesha/img/steak.png");
        itemList.add(item);

        item = new Item();
        item.setName("Веган Сет");
        item.setPrice(8.59);
        item.setColor("#2dd074");
        item.setImage("/com/lepesha/img/vegan.png");
        itemList.add(item);

        return itemList;
    }

    private void setChosenItem(Item item){
        nameItemLabel.setText(item.getName());
        priceItemLabel.setText(item.getPrice() + CURRENCY);
        Image image = new Image(getClass().getResourceAsStream(item.getImage()));
        imgItem.setImage(image);
        chosenItem.setStyle("-fx-background-color:" + item.getColor() + ";\n" + "-fx-background-radius: 30;");
    }

    @FXML
    void buttonAddToCart(ActionEvent event) {
        String stringPrice = priceItemLabel.getText();
        String cutString = stringPrice.substring(0, stringPrice.indexOf("р"));//обрезка строки, чтобы удалить курс валют

        if(orderTableViewController.addData(nameItemLabel.getText(), Double.parseDouble(cutString), quantity.getValue())==true){
            messageLabel.setText("Успешное добавление!");
        }
        else {
            messageLabel.setText("Такой товар уже есть в корзине!");
        }
        Runnable runnable = () -> {
            messageLabel.setVisible(true);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            messageLabel.setVisible(false);
        };


        new Thread(runnable).start();
    }

    @FXML
    void clickMyProfile(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/lepesha/fxml/userMenuView.fxml"));

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UserMenuViewController userMenuViewController= fxmlLoader.getController();
        Parent root = fxmlLoader.getRoot();

        userMenuViewController.setFields
                (authorisedLogin, authorisedNumber, authorisedFirstName,
                        authorisedFirstName);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderTableViewLoader = new FXMLLoader(getClass().getResource("/com/lepesha/fxml/orderTableView.fxml"));
        try {
            orderTableViewRoot = orderTableViewLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        orderTableViewController = orderTableViewLoader.getController();

        stage =new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(new Scene(orderTableViewRoot));
        stage.setTitle("TableView");

        quantity.getItems().addAll(1,2,3,4,5);
        quantity.getSelectionModel().selectFirst();

        itemList.addAll(getItemList());
        setChosenItem(itemList.get(0));
        myListener = (item)-> {
            setChosenItem(item);
        };
        int column=0;
        int row=1;

        try{
        for (int i = 0; i < itemList.size(); i++) {
            FXMLLoader fxmlLoader= new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/lepesha/fxml/itemView.fxml"));

            AnchorPane anchorPane = fxmlLoader.load();

            ItemViewController itemViewController = fxmlLoader.getController();
            itemViewController.setData(itemList.get(i), myListener);

            if(column==3){
                column=0;
                row++;
            }
            grid.add(anchorPane, column++, row);

            grid.setMinWidth(Region.USE_COMPUTED_SIZE);
            grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
            grid.setMaxWidth(Region.USE_PREF_SIZE);

            grid.setMinHeight(Region.USE_COMPUTED_SIZE);
            grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
            grid.setMaxHeight(Region.USE_PREF_SIZE);

            GridPane.setMargin(anchorPane, new Insets(10));
        }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void clickCart(MouseEvent mouseEvent) {
        stage.show();
    }
}
