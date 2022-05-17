package service;

import model.OrderInfo;
import model.OrderedItem;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class OrderedItemService {
    public void add(OrderedItem orderedItem){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        try{
            session.save(orderedItem);
            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public List<OrderedItem> getOrderedItem(){
        List<OrderedItem> list = new ArrayList<>();
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Query q = session.createQuery("from OrderedItem");
        return list;
    }
}
