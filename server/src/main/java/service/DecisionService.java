package service;

import controller.DecisionController;
import dao.DecisionInt;
import model.Decision;
import org.hibernate.Session;
import util.HibernateUtil;

public class DecisionService implements DecisionInt {
    public void add(Decision decision){
            Session session = HibernateUtil.getSession();
            try{
                session.getTransaction().begin();
                session.save(decision);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
                session.getTransaction().rollback();
            } finally {
                session.close();
            }
    }
}
