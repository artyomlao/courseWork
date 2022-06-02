package dao;

import model.AdminInfo;

import java.util.List;

public interface AdminInfoInt {
    List<AdminInfo> getAll();
    AdminInfo getAdminInfo(String login);
}
