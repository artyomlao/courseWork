package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Item;

public class ItemViewController {
    @FXML
    private ImageView imgItem;

    @FXML
    private Label nameItemLabel;

    @FXML
    private Label priceItemLabel;

    private Item item;

    private MyListener myListener;

    @FXML
    void click(MouseEvent event) {
        myListener.onClickListener(item);
    }

    public void setData(Item item, MyListener myListener){
        this.item = item;
        this.myListener = myListener;
        nameItemLabel.setText(item.getName());
        priceItemLabel.setText(item.getPrice() + OrderViewController.CURRENCY);
        Image image = new Image(getClass().getResourceAsStream(item.getImage()));
        imgItem.setImage(image);
    }

}
