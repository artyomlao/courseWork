package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DecisionEfficiency;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilteringViewController {
    @FXML
    private TextField firstAvgEfficiency;

    @FXML
    private TextField secondAvgEfficiency;

    private FXMLLoader showDecisionViewFXMLLoader;

    private List<DecisionEfficiency> allDecisionsList = new ArrayList<>();

    private ObservableList<DecisionEfficiency> filteringList = FXCollections.observableArrayList();

    public FXMLLoader getShowDecisionViewFXMLLoader() {
        return showDecisionViewFXMLLoader;
    }

    public void setShowDecisionViewFXMLLoader(FXMLLoader showDecisionViewFXMLLoader) {
        this.showDecisionViewFXMLLoader = showDecisionViewFXMLLoader;
    }

    public void setList(ObservableList allDecisionsList){
        this.allDecisionsList = allDecisionsList;
    }

    public void filtering(ActionEvent event) {

        if((checkForDouble(firstAvgEfficiency.getText()) && checkForDouble(secondAvgEfficiency.getText())==true)) {
            double firstEff = Double.parseDouble(firstAvgEfficiency.getText());
            double secondEff = Double.parseDouble(secondAvgEfficiency.getText());

            if(firstEff>secondEff){
                double buf = firstEff;
                firstEff = secondEff;
                secondEff = buf;
            }

            for (int i = 0; i < allDecisionsList.size(); i++) {
                if(firstEff<=allDecisionsList.get(i).getAvgEfficiency() && secondEff>=allDecisionsList.get(i).getAvgEfficiency()){
                    filteringList.add(allDecisionsList.get(i));
                }
            }

            ShowDecisionViewController showDecisionViewController = showDecisionViewFXMLLoader.getController();
            showDecisionViewController.setIntermediateList(filteringList);

            showDecisionViewController.setIntermediateDataSeries(filteringList);

            showDecisionViewController.setIntermediateListTableItems();

            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    private boolean checkForDouble(String num){
        Pattern pattern = Pattern.compile("\\d{1,}(\\.\\d{1,})?");
        Matcher matcher = pattern.matcher(num);
        System.out.println("checking double " + matcher.matches());
        return matcher.matches();
    }
}
