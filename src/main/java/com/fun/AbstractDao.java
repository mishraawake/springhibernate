package com.fun;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.lang.reflect.ParameterizedType;

/**
 * Created by panmishr on 18/08/18.
 */
public class AbstractDao<PK extends Serializable, T>  {

  @Autowired
  private SessionFactory sessionFactory;

  private final Class<T> persistentClass;

  @SuppressWarnings("unchecked")
  public AbstractDao(){
    this.persistentClass =(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
  }

  public <T> T save(T obj) throws HibernateException {
    session().save(obj);
    return obj;
  }

  @SuppressWarnings("unchecked")
  public T getByKey(PK key) {
    return (T) session().get(persistentClass, key);
  }

  protected Session session(){
    Session session = sessionFactory.getCurrentSession();
    return  session;
  }

  protected Session newSession(){
    Session session = sessionFactory.openSession();
    return  session;
  }

  public void delete(T entity) {
    session().delete(entity);
  }

  protected Criteria createEntityCriteria(){
    return session().createCriteria(persistentClass);
  }
}
