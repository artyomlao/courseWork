package view;

import controller.Listener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddDecisionViewController {
    @FXML
    private TextField firstBuy;

    @FXML
    private TextField firstDividends;

    @FXML
    private TextField firstSell;

    @FXML
    private TextField secondBuy;

    @FXML
    private TextField secondDividends;

    @FXML
    private TextField secondSell;

    @FXML
    private TextField thirdBuy;

    @FXML
    private TextField thirdDividends;

    @FXML
    private TextField thirdSell;

    @FXML
    private Label messageLabel;

    @FXML
    private TextField itemName;

    private String login;

    public void setLogin(String login) {
        this.login = login;
    }

    @FXML
    void addDecision(ActionEvent event) {

        if((checkForDouble(firstBuy.getText()) && checkForDouble(firstDividends.getText()) && checkForDouble(firstSell.getText()) &&
                checkForDouble(secondBuy.getText()) && checkForDouble(secondDividends.getText()) && checkForDouble(secondSell.getText()) &&
                checkForDouble(thirdBuy.getText()) && checkForDouble(thirdDividends.getText()) && checkForDouble(thirdSell.getText())
                && checkForText(itemName.getText()))==true) {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("requestType", "MAKE_DECISION");

            jsonObject.put("login", login);

            jsonObject.put("itemName", itemName.getText());

            jsonObject.put("firstBuy", firstBuy.getText());
            jsonObject.put("firstDividends", firstDividends.getText());
            jsonObject.put("firstSell", firstSell.getText());

            jsonObject.put("secondBuy", secondBuy.getText());
            jsonObject.put("secondDividends", secondDividends.getText());
            jsonObject.put("secondSell", secondSell.getText());

            jsonObject.put("thirdBuy", thirdBuy.getText());
            jsonObject.put("thirdDividends", thirdDividends.getText());
            jsonObject.put("thirdSell", thirdSell.getText());

            Listener.send(jsonObject.toJSONString());

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Предупреждение");
            alert.setHeaderText("Неправильный ввод данных!");
            alert.setContentText("Проверьте название вашего товара, а также значения "
                    + "решения должны быть либо целыми либо дробными в формате '3000' или '3000.1' ");

            alert.showAndWait();
        }

    }

    private boolean checkForDouble(String num){
        Pattern pattern = Pattern.compile("\\d{1,}(\\.\\d{1,})?");
        Matcher matcher = pattern.matcher(num);
        System.out.println("checking double " + matcher.matches());
        return matcher.matches();
    }

    private boolean checkForText(String item){
        Pattern pattern = Pattern.compile("\\w{2,}|\\W{2,}");
        Matcher matcher = pattern.matcher(item);
        System.out.println("checking item " + matcher.matches());
        return matcher.matches();
    }

    public void backToPreviousWindow(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}
