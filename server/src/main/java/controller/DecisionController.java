package controller;

import model.AdminInfo;
import model.Decision;
import model.DecisionEfficiency;
import org.json.simple.JSONObject;
import service.AdminInfoService;
import service.DecisionEfficiencyService;
import service.DecisionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DecisionController {
    private JSONObject jsonObject;

    public DecisionController(){
        sendDecisionsToClient();
    }

    private void sendDecisionsToClient() {
        JSONObject jsonObject = new JSONObject();
        List<DecisionEfficiency> list = getDecisions();

        jsonObject.put("size", String.valueOf(list.size()));

        for (int i = 0; i < list.size(); i++) {
            Map decisionEfficiency = new HashMap();

            decisionEfficiency.put("itemName", String.valueOf(list.get(i).getDecision().getName()));

            decisionEfficiency.put("firstEfficiency", String.valueOf(list.get(i).getFirstEfficiency()));
            decisionEfficiency.put("secondEfficiency", String.valueOf(list.get(i).getSecondEfficiency()));
            decisionEfficiency.put("thirdEfficiency", String.valueOf(list.get(i).getThirdEfficiency()));

            decisionEfficiency.put("avgEfficiency", String.valueOf(list.get(i).getAvgEfficiency()));
            decisionEfficiency.put("dispersion", String.valueOf(list.get(i).getDispersion()));

            jsonObject.put("decision" + i, decisionEfficiency);
            //тут остановился
        }
        System.out.println(jsonObject.toJSONString());

        Handler.send(jsonObject.toJSONString());
    }

    private List<DecisionEfficiency> getDecisions(){
        return new DecisionEfficiencyService().getAll();
    }

    public DecisionController(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        addDecision();
    }

    private void addDecision(){
        Decision decision = new Decision();
        DecisionEfficiency decisionEfficiency =new DecisionEfficiency();

        AdminInfo adminInfo = new AdminInfoService().getAdminInfo((String) jsonObject.get("login"));

        decision.setAdminInfo(adminInfo);

        decision.setFirstBuy(Double.parseDouble((String) jsonObject.get("firstBuy")));
        decision.setFirstDividends(Double.parseDouble((String) jsonObject.get("firstDividends")));
        decision.setFirstSell(Double.parseDouble((String) jsonObject.get("firstSell")));

        decision.setSecondBuy(Double.parseDouble((String) jsonObject.get("secondBuy")));
        decision.setSecondDividends(Double.parseDouble((String) jsonObject.get("secondDividends")));
        decision.setSecondSell(Double.parseDouble((String) jsonObject.get("secondSell")));

        decision.setThirdBuy(Double.parseDouble((String) jsonObject.get("thirdBuy")));
        decision.setThirdDividends(Double.parseDouble((String) jsonObject.get("thirdDividends")));
        decision.setThirdSell(Double.parseDouble((String) jsonObject.get("thirdSell")));

        decision.setName((String) jsonObject.get("itemName"));

        decisionEfficiency.setDecision(decision);

        decisionEfficiency.setFirstEfficiency(calculateEfficiency(decision.getFirstBuy(), decision.getFirstDividends(),
                decision.getFirstSell()));
        decisionEfficiency.setSecondEfficiency(calculateEfficiency(decision.getSecondBuy(), decision.getSecondDividends(),
                decision.getSecondSell()));
        decisionEfficiency.setThirdEfficiency(calculateEfficiency(decision.getThirdBuy(), decision.getThirdDividends(),
                decision.getThirdSell()));

        decisionEfficiency.setAvgEfficiency(calculateAvgEfficiency(decisionEfficiency.getFirstEfficiency(),
                decisionEfficiency.getSecondEfficiency(),
                decisionEfficiency.getThirdEfficiency()));

        decisionEfficiency.setDispersion(calculateDispersion(decisionEfficiency.getFirstEfficiency(),
                decisionEfficiency.getSecondEfficiency(),
                decisionEfficiency.getThirdEfficiency(),
                decisionEfficiency.getAvgEfficiency()));

        new DecisionService().add(decision);
        new DecisionEfficiencyService().add(decisionEfficiency);
    }

    private double calculateEfficiency(double buy, double dividends, double sell){
        double efficiency;
        efficiency = (sell+dividends)/buy;
        return efficiency;
    }
    private double calculateAvgEfficiency(double firstEfficiency, double secondEfficiency, double thirdEfficiency){
        double avgEfficiency;
        avgEfficiency = (firstEfficiency + secondEfficiency + thirdEfficiency)/3;
        return avgEfficiency;
    }

    private double calculateDispersion(double firstEfficiency, double secondEfficiency,
                                     double thirdEfficiency, double avgEfficiency){
        double firstDelta = firstEfficiency-avgEfficiency;
        double secondDelta = secondEfficiency - avgEfficiency;
        double thirdDelta = thirdEfficiency - avgEfficiency;

        double dispersion = ((firstDelta*firstDelta)+(secondDelta*secondDelta)+(thirdDelta*thirdDelta))/2;
        return dispersion;
    }

}
