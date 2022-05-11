package model;

import controller.Handler;
import entity.UserInfo;
import net.bytebuddy.jar.asm.Handle;
import org.json.simple.JSONObject;
import service.UserInfoService;

import java.util.List;

public class Authorisation {
    private String login;
    private String password;
    private UserInfoService userInfoService;

    Authorisation(JSONObject jsonObject){
        login = (String) jsonObject.get("login");
        password = (String) jsonObject.get("password");

        checkingDB();
    }

    private void checkingDB(){
        userInfoService = new UserInfoService();
        Validation validation = new Validation();
        if(validation.checkAuthorisation(login, password)==true) {
            Handler.send("Успешная авторизация");
        }
        else Handler.send("Неправильный логин и пароль");
    }

}
