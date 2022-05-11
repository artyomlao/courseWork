package model;

import controller.Handler;
import entity.UserInfo;
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

    public Registration(JSONObject jsonObject){
        Map userInfoMap = (Map) jsonObject.get("userInfo");

        firstName = (String) userInfoMap.get("firstName");
        password = (String) userInfoMap.get("password");
        number = (String) userInfoMap.get("phoneNumber");
        login = (String) userInfoMap.get("login");

        userInfo = new UserInfo();
        userInfo.setLogin(login);
        userInfo.setPassword(password);
        userInfo.setFirstName(firstName);
        userInfo.setNumber(number);

        checkingDB();
    }

    private void checkingDB(){
        userInfoService = new UserInfoService();
        Validation validation = new Validation(userInfo);

        if(validation.checkLogin()==true){
            Handler.send("Login");
        }
        else if(validation.checkNumber()==true){
            Handler.send("Number");
        }
        else add();
    }

    private void add(){
        userInfoService.add(userInfo);
    }
}
