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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DecisionEfficiency;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

public class ShowDecisionViewController implements Initializable {
    @FXML
    private TableColumn<DecisionEfficiency, Double> avgEfficiency;

    @FXML
    private TableColumn<DecisionEfficiency, Double> dispersion;

    @FXML
    private TableColumn<DecisionEfficiency, Double> firstEfficiency;

    @FXML
    private TableColumn<DecisionEfficiency, String> itemName;

    @FXML
    private TableColumn<DecisionEfficiency, Double> secondEfficiency;

    @FXML
    private TableColumn<DecisionEfficiency, Double> thirdEfficiency;

    @FXML
    private TextField textFieldSearch;

    @FXML
    private BarChart<String, Number> chart;

    @FXML
    public TableView<DecisionEfficiency> table;

    private BarChart<String, Number> barChart;

    private FXMLLoader thatFXMLLoader;

    private static XYChart.Series<String, Number> dataSeries;

    private XYChart.Series<String, Number> intermediateDataSeries;

    private ObservableList<DecisionEfficiency> allDecisionsList = FXCollections.observableArrayList();

    private ObservableList<DecisionEfficiency> intermediateList = FXCollections.observableArrayList();

    public void setIntermediateListTableItems(){
        table.setItems(intermediateList);
    }

    public void setIntermediateList(ObservableList<DecisionEfficiency> intermediateList) {
        this.intermediateList = intermediateList;
    }

    public void setThisFXMLLoader(FXMLLoader thatFXMLLoader) {
        this.thatFXMLLoader = thatFXMLLoader;
    }

    public void setIntermediateDataSeries(ObservableList<DecisionEfficiency> observableList){
        intermediateDataSeries = new XYChart.Series<>();
        for (int i = 0; i < observableList.size(); i++) {
            intermediateDataSeries.getData().add(new XYChart.Data<String, Number>(observableList.get(i).getItemName(),
                    observableList.get(i).getAvgEfficiency()));
        }
        intermediateDataSeries.setName("Средняя удельная эффективность");
        setChartDataSeries(intermediateDataSeries);
    }

    public void setChartDataSeries(XYChart.Series<String, Number> dataSeries){
        chart.getData().clear();
        dataSeries.setName("Средняя удельная эффективность");
        chart.getData().add(dataSeries);
    }

    public void setData(){
        chart.getData().clear();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("requestType", "GET_DECISION");

        Listener.send(jsonObject.toJSONString());

        String response = Listener.listen();

        JSONObject jsonObjectFromServer = null;
        try {
            jsonObjectFromServer = (JSONObject) new JSONParser().parse(response);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int size = Integer.parseInt((String) jsonObjectFromServer.get("size"));

        dataSeries = new XYChart.Series<String, Number>();
        for (int i = 0; i < size; i++) {
            Map map = (Map) jsonObjectFromServer.get("decision" + i);

            DecisionEfficiency decisionEfficiency = new DecisionEfficiency();

            String itemName = (String) map.get("itemName");
            double avgEfficiency = Double.parseDouble((String) map.get("avgEfficiency"));

            dataSeries.getData().add(new XYChart.Data<String, Number>(itemName, avgEfficiency));

            decisionEfficiency.setItemName(itemName);

            decisionEfficiency.setFirstEfficiency(Double.parseDouble((String) map.get("firstEfficiency")));
            decisionEfficiency.setSecondEfficiency(Double.parseDouble((String) map.get("secondEfficiency")));
            decisionEfficiency.setThirdEfficiency(Double.parseDouble((String) map.get("thirdEfficiency")));

            decisionEfficiency.setAvgEfficiency(Double.parseDouble((String) map.get("avgEfficiency")));
            decisionEfficiency.setDispersion(Double.parseDouble((String) map.get("dispersion")));

            allDecisionsList.add(decisionEfficiency);
        }
        setChartDataSeries(dataSeries);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Товар");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Средняя удельная эффективность");

        barChart = new BarChart<String, Number>(xAxis, yAxis);


        itemName.setCellValueFactory(new PropertyValueFactory<DecisionEfficiency, String >("itemName"));
        avgEfficiency.setCellValueFactory(new PropertyValueFactory<DecisionEfficiency, Double>("avgEfficiency"));
        firstEfficiency.setCellValueFactory(new PropertyValueFactory<DecisionEfficiency, Double>("firstEfficiency"));
        dispersion.setCellValueFactory(new PropertyValueFactory<DecisionEfficiency, Double>("dispersion"));
        secondEfficiency.setCellValueFactory(new PropertyValueFactory<DecisionEfficiency, Double>("secondEfficiency"));
        thirdEfficiency.setCellValueFactory(new PropertyValueFactory<DecisionEfficiency, Double>("thirdEfficiency"));

        table.setItems(allDecisionsList);
    }


    public void filtering(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/lepesha/fxml/filteringView.fxml"));
        Parent root = null;

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FilteringViewController filteringViewController = fxmlLoader.getController();

        filteringViewController.setList(allDecisionsList);

        filteringViewController.setShowDecisionViewFXMLLoader(thatFXMLLoader);

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void clearIntermediateList(){
        Iterator<DecisionEfficiency> itr = intermediateList.iterator();
        while(itr.hasNext()){
            itr.next();
            itr.remove();
        }
    }

    public void searchButtonPressed(ActionEvent event) {
        if(textFieldSearch.getText().equals("")){
            setIntermediateDataSeries(allDecisionsList);
            table.setItems(allDecisionsList);
        } else{
            clearIntermediateList();
            for (int i = 0; i < allDecisionsList.size(); i++) {
                if(allDecisionsList.get(i).getItemName().indexOf(textFieldSearch.getText())!=-1){
                    intermediateList.add(allDecisionsList.get(i));
                }
                setIntermediateDataSeries(intermediateList);
            }
            table.setItems(intermediateList);
        }
    }

    public void back(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/lepesha/fxml/adminMenuView.fxml"));

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
}
