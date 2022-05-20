package controller;

import model.UserInfo;
import org.json.simple.JSONObject;
import service.UserInfoService;

import java.util.Map;

public class Registration {
    private final String firstName;
    private final String password;
    private final String number;
    private final String login;

    private UserInfoService userInfoService;
    private UserInfo userInfo;

    public Registration(JSONObject jsonObject) {
        Map userInfoMap = (Map) jsonObject.get("userInfo");

        firstName = (String) userInfoMap.get("firstName");
        password = (String) userInfoMap.get("password");
        number = (String) userInfoMap.get("number");
        login = (String) userInfoMap.get("login");

        userInfo = new UserInfo();
        userInfo.setLogin(login);
        userInfo.setPassword(password);
        userInfo.setFirstName(firstName);
        userInfo.setNumber(number);

        checkingDB();
    }

    private void checkingDB() {
        userInfoService = new UserInfoService();
        Validation validation = new Validation(userInfo);

        if((validation.checkLoginInUserTable() || validation.checkLoginInAdminTable())==true) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "LoginExists");
            Handler.send(jsonObject.toString());
        }
        else if(validation.checkNumber()==true) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "NumberExists");
            Handler.send(jsonObject.toString());
        }
        else {
            add();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "SuccessfulRegistration");
            Handler.send(jsonObject.toString());
        }
    }

    private void add(){
        userInfoService.add(userInfo);
    }
}
