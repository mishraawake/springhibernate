package com.fun;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Employee emp = new Employee();
            emp.setJoiningDate(new Date());
            emp.setName("pankaj");
            emp.setSalary(new BigDecimal(2));
            emp.setSsn("ssn");
           // Transaction tr = session.getTransaction();
           // tr.begin();
            session.save(emp);
           // session.flush();
          //  tr.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
           // HibernateUtil.getSessionFactory().close();
        }
    }
}