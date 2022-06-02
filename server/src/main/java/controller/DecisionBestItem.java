package controller;

import model.DecisionEfficiency;
import org.json.simple.JSONObject;
import service.DecisionEfficiencyService;

import java.util.List;

public class DecisionBestItem {
    private List<DecisionEfficiency> list;
    private DecisionEfficiency bestItem;

    public DecisionBestItem(){
        list = new DecisionEfficiencyService().getAll();
    }

    public void sendBestItem(){
        JSONObject jsonObject = new JSONObject();

        if(bestItem==null){
            jsonObject.put("requestMessage", "noGoodItems");
        } else{
            jsonObject.put("requestMessage", "success");
            jsonObject.put("avgEfficiency", String.valueOf(bestItem.getAvgEfficiency()));
            jsonObject.put("dispersion", String.valueOf(bestItem.getDispersion()));
            jsonObject.put("name", String.valueOf(bestItem.getDecision().getName()));
        }
        Handler.send(jsonObject.toJSONString());
    }

    public void computingBestItem(){
        DecisionEfficiency decisionEfficiency = null;
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getDispersion()>=0.15) {
                continue;
            } else {
                if(decisionEfficiency==null){
                    decisionEfficiency = list.get(i);
                } else{
                    if(list.get(i).getAvgEfficiency()>=decisionEfficiency.getAvgEfficiency()){
                        decisionEfficiency = list.get(i);
                    }
                }
            }
        }
        bestItem = decisionEfficiency;
    }
}
