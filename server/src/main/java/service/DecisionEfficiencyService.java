package service;

import model.DecisionEfficiency;
import org.hibernate.Session;
import util.HibernateUtil;

import org.hibernate.query.Query;
import java.util.List;

public class DecisionEfficiencyService {
    public void add(DecisionEfficiency decisionEfficiency){
        Session session = HibernateUtil.getSession();
        try{
            session.getTransaction().begin();
            session.save(decisionEfficiency);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public List<DecisionEfficiency> getAll(){
        Session session = HibernateUtil.getSession();
        List<DecisionEfficiency> list = null;
        try{
            session.getTransaction().begin();
            Query q = (Query) session.createQuery("from DecisionEfficiency");
            list = q.list();
            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally{
            session.close();
        }
        return list;
    }
}
