package controller;

import model.Decision;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RequestChecker {
    private String json;
    private JSONObject jsonObject;

    public RequestChecker(String json){
        Object obj = null;
        this.json = json;

        try {
            obj = new JSONParser().parse(json);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        jsonObject = (JSONObject) obj;
        checkRequestType();
    }

    public void checkRequestType(){
        String requestType = (String) jsonObject.get("requestType");

        System.out.println(requestType);

        switch(requestType) {
            case "REGISTRATION": {
                System.out.println("Клиент отправил запрос на регистрацию");
                Registration reg = new Registration(jsonObject);
                break;
            }
            case "AUTHORISATION":{
                System.out.println("Клиент отправил запрос на авторизацию");
                Authorisation auth = new Authorisation(jsonObject);
                break;
            }
            case "MAKE_ORDER":{
                System.out.println("Клиент завершил заказ");
                Order order = new Order(jsonObject);
                break;
            }
            case "GET_ORDER": {
                System.out.println("Клиент запрашивает данные о своём заказе");
                Order order = new Order(jsonObject);
                break;
            }
            case "MAKE_DECISION":{
                System.out.println("Администратор создаёт решение");
                DecisionController decisionController = new DecisionController(jsonObject);
                break;
            }
            case "GET_DECISION":{
                System.out.println("Администратор запрашивает решения");
                DecisionController decisionController = new DecisionController();
                break;
            }
            case "GET_USERINFO":{
                System.out.println("Администратор запрашивает данные пользователей");
                UserController userController = new UserController();
                break;
            }
            case "GET_BEST_ITEM": {
                System.out.println("Администратор хочет узнать наилучший товар");
                DecisionBestItem decisionBestItem = new DecisionBestItem();
                decisionBestItem.computingBestItem();
                decisionBestItem.sendBestItem();
                break;
            }
        }
    }
}
