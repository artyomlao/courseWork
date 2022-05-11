package dao;

import entity.UserInfo;

import java.util.List;

public interface UserInfoInt {
    void add(UserInfo userInfo);
    List<UserInfo> getAll();
}
