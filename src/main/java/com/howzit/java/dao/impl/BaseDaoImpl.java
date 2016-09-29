package com.howzit.java.dao.impl;

import com.howzit.java.dao.BaseDao;
import com.howzit.java.model.ActivityAreaEntity;
import com.howzit.java.model.UserEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * UserEntity: Ashif.Qureshi
 * Date: 20/8/14
 * Time: 6:46 PM
 * To change this template use File | Settings | File Templates.
 */

public class BaseDaoImpl implements BaseDao {

    private Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        /*try {
            index();
        } catch (InterruptedException e) {
            logger.error("Data base indexing interrupted. "+e.getMessage());
        }*/
    }

    public void save(Object object) {
        getCurrentSession().saveOrUpdate(object);
    }

    public void update(Object object){
        getCurrentSession().update(object);
    }

    protected Session getCurrentSession() {
        Session session = null;
        try{
            session = sessionFactory.getCurrentSession();
        }catch (HibernateException he){
            session = sessionFactory.openSession();
        }
        if(null == session)
            session = sessionFactory.openSession();
        return session;
    }

    protected FullTextSession getFullTextSession() {
        return Search.getFullTextSession(getCurrentSession());
    }

     public void index() throws InterruptedException {
        getFullTextSession().createIndexer(ActivityAreaEntity.class, UserEntity.class).startAndWait();
    }

}
