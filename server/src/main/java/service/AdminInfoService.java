package service;

import model.AdminInfo;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class AdminInfoService {
    public List<AdminInfo> getAll(){
        List<AdminInfo> list = new ArrayList<>();
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        try{
            Query q = session.createQuery("from AdminInfo");
            list = q.list();
            session.getTransaction().commit();
        } catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return list;
    }

    public AdminInfo getAdminInfo(String login){
        AdminInfo adminInfo = null;

        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        try{
            Query q = session.createQuery("from AdminInfo where login='" + login +"'");
            adminInfo = (AdminInfo) q.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally{
            session.close();
        }
        return adminInfo;
    }
}
