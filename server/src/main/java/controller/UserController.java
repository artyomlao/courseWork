package controller;

import model.UserInfo;
import org.json.simple.JSONObject;
import service.UserInfoService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserController {
    public UserController(){
        List<UserInfo> list;
        list = new UserInfoService().getAll();

        JSONObject jsonObject = new JSONObject();

        for (int i = 0; i < list.size(); i++) {
            Map userMap = new HashMap();

            userMap.put("login" , list.get(i).getLogin());
            userMap.put("number", list.get(i).getNumber());
            userMap.put("firstName", list.get(i).getFirstName());

            jsonObject.put("user" + i, userMap);
        }
        jsonObject.put("size", String.valueOf(list.size()));
        System.out.println(jsonObject.toJSONString());

        Handler.send(jsonObject.toJSONString());
    }
}
