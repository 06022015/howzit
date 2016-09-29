package com.howzit.java.dao.impl;

import com.howzit.java.model.ActivityEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by IntelliJ IDEA.
 * User: ashif
 * Date: 9/29/14
 * Time: 10:25 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
@Transactional
public class SampleDao {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public ActivityEntity getActivity(Long id){
        return (ActivityEntity)getCurrentSession().get(ActivityEntity.class, id);
    }

    private Session getCurrentSession() {
         return sessionFactory.getCurrentSession();
     }

     private FullTextSession getFullTextSession() {
         return Search.getFullTextSession(getCurrentSession());
     }


}
