package controller;

import model.AdminInfo;
import model.UserInfo;
import service.AdminInfoService;
import service.UserInfoService;

import java.util.Iterator;
import java.util.List;

public class Validation {
    private List<UserInfo> users;
    private List<AdminInfo> admins;
    private UserInfo userInfo;

    Validation(){
        users = new UserInfoService().getAll();
        admins = new AdminInfoService().getAll();
    }

    Validation(UserInfo userInfo){
        this.userInfo =userInfo;
        users = new UserInfoService().getAll();
        admins = new AdminInfoService().getAll();
    }

    //метод возвращает true при наличии данного логина в БД
    public boolean checkLoginInUserTable(){
        Iterator<UserInfo> itr = users.iterator();

        while(itr.hasNext()){
            if(itr.next().getLogin().equals(userInfo.getLogin())){
                return true;
            }
        }
        return false;
    }

    public boolean checkLoginInAdminTable(){
        Iterator<AdminInfo> itr = admins.iterator();

        while(itr.hasNext()){
            if(itr.next().getLogin().equals(userInfo.getLogin())){
                return true;
            }
        }
        return false;
    }

    public boolean checkAuthorisation(String login, String password){
        Iterator<UserInfo> itr = users.iterator();

        while(itr.hasNext()){
            UserInfo userInfoItr = itr.next();
            if(userInfoItr.getLogin().equals(login)){
                if(userInfoItr.getPassword().equals(password)) return true;
            }
        }
        return false;
    }
    //метод возвращает true при наличии данного номера в БД
    public boolean checkNumber(){
        Iterator<UserInfo> itr = users.iterator();

        while(itr.hasNext()){
            if(itr.next().getLogin().equals(userInfo.getNumber())){
                return true;
            }
        }
        return false;
    }

    public boolean checkAdminTable(String login, String password) {
        Iterator<AdminInfo> itr = admins.iterator();

        while (itr.hasNext()){
            AdminInfo adminInfo = itr.next();
            if(adminInfo.getLogin().equals(login)){
                if(adminInfo.getPassword().equals(password)) return true;
            }
        }
        return false;
    }
}
