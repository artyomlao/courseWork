package service;

import model.OrderInfo;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;
import java.util.List;

public class OrderInfoService {
    public void add(OrderInfo orderInfo){
        Session session = HibernateUtil.getSession();

        try{
            session.getTransaction().begin();
            session.save(orderInfo);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public List<Object[]> getOrderInfo(String login){
        Session session = HibernateUtil.getSession();

        List<Object[]> list = null;
        try{
            session.getTransaction().begin();
            Query q = session.createQuery("from OrderInfo o inner join o.userInfo u inner join o.orderedItem where u.login = '" + login + "'");

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
}
