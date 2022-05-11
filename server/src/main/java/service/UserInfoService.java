package service;

import dao.UserInfoInt;
import entity.UserInfo;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserInfoService implements UserInfoInt {

    public void add(UserInfo userInfo){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        session.save(userInfo);

        session.getTransaction().commit();

    }
    public List<UserInfo> getAll(){
//        List<UserInfo> users =new ArrayList<>();
//        Session session = HibernateUtil.getSession();
//
//        session.beginTransaction();
//
//        List<Object[]> list = session.createNativeQuery("select id, login, password, firstName, number from userinfo").list();
//
//
//        Iterator itr = list.iterator();
//
//        while(itr.hasNext()){
//            Object[] object = (Object[]) itr.next();
//            UserInfo userInfo = new UserInfo();
//            userInfo.setId((Integer) object[0]);
//
//            userInfo.setLogin((String)object[1]);
//            userInfo.setPassword((String)object[2]);
//            userInfo.setFirstName((String) object[3]);
//            userInfo.setNumber((String) object[4]);
//            users.add(userInfo);
//        }
//        System.out.println(users);

        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Query q = session.createQuery("from UserInfo");
        List<UserInfo> users = q.list();
        session.getTransaction().commit();
        return users;
    }
}
