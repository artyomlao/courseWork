package model;

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
                Registration reg = new Registration(jsonObject);
                System.out.println("зарегистрирован чел");
                break;
            }
            case "AUTHORISATION":{
                Authorisation auth = new Authorisation(jsonObject);
            }
        }
    }
}
