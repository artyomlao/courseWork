package dao;

import model.UserInfo;

import java.util.List;

public interface UserInfoInt {
    void add(UserInfo userInfo);

    List<UserInfo> getAll();

    UserInfo getUserInfo(String login);
}
