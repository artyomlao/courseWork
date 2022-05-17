package controller;

import model.AdminInfo;
import model.UserInfo;
import org.json.simple.JSONObject;
import service.AdminInfoService;
import service.UserInfoService;

import java.util.HashMap;
import java.util.Map;

public class Authorisation {
    private String login;
    private String password;

    Authorisation(JSONObject jsonObject){
        login = (String) jsonObject.get("login");
        password = (String) jsonObject.get("password");

        checkingDB();
    }

    private void checkingDB(){
        Validation validation = new Validation();
        JSONObject jsonObject = new JSONObject();
        if(validation.checkAdminTable(login, password)){
            AdminInfo adminInfo = new AdminInfoService().getAdminInfo(login); // получаем объект из БД, чтобы передать на сервер

            jsonObject.put("login", adminInfo.getLogin());
            jsonObject.put("message", "SuccessfulAdminAuthorisation");
            Handler.send(jsonObject.toString());
        }
        else if(validation.checkAuthorisation(login, password)==true) {
            UserInfoService userInfoService = new UserInfoService();
            UserInfo userInfo = userInfoService.getUserInfo(login);

            Map userInfoMap = new HashMap();
            userInfoMap.put("firstName", userInfo.getFirstName());
            userInfoMap.put("login", userInfo.getLogin());
            userInfoMap.put("number", userInfo.getNumber());

            jsonObject.put("message", "SuccessfulAuthorisation");
            jsonObject.put("userInfo", userInfoMap);
            Handler.send(jsonObject.toString());
        }
        else {
            jsonObject.put("message", "IncorrectLoginOrPassword");
            Handler.send(jsonObject.toString());
        }
    }
}
