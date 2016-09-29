package com.howzit.java.dao.impl;

import com.howzit.java.dao.UserDao;
import com.howzit.java.exception.DuplicateObjectException;
import com.howzit.java.model.LoginStatusEntity;
import com.howzit.java.model.UserEntity;
import com.howzit.java.model.master.Role;
import com.howzit.java.model.type.LoginStatus;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * UserEntity: Ashif.Qureshi
 * Date: 20/8/14
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

    public Role getRole(String name) {
        Criteria criteria  = getCurrentSession().createCriteria(Role.class)
                .add(Restrictions.eq("name", name));
        return (Role)criteria.uniqueResult();
    }

    public UserEntity getUserByUsername(String username){
        Criteria criteria = getCurrentSession().createCriteria(UserEntity.class)
                .add(Restrictions.eq("username", username.trim()));
        return (UserEntity)criteria.uniqueResult();
    }

    public UserEntity getUser(Long id) {
        Criteria criteria = getCurrentSession().createCriteria(UserEntity.class)
                .add(Restrictions.eq("id", id))
                .setFetchMode("roles", FetchMode.SELECT);
        return (UserEntity)criteria.uniqueResult();
    }

    public void saveUser(UserEntity userEntity) throws DuplicateObjectException{
        Criteria criteria = getCurrentSession().createCriteria(UserEntity.class)
                .add(Restrictions.or(Restrictions.eq("username", userEntity.getUsername()),
                        Restrictions.eq("mobile", userEntity.getMobile())));
        if(null!= userEntity.getId()){
            criteria.add(Restrictions.ne("id", userEntity.getId()));
        }
        UserEntity users = (UserEntity)criteria.uniqueResult();
        if(null!=users)
            throw new DuplicateObjectException("User name or mobile already exist");
        save(userEntity);
    }

    public void updateProfile(String name, String value, Long id) throws SQLException{
        String query = "update user set "+ name+" = '" +value+"'where id="+id+"";
        getCurrentSession().createSQLQuery(query).executeUpdate();
    }


    /*Login*/
    public void updateLoginStatus(String deviceId, LoginStatus loginStatus) {
        String hql = "update LoginStatusEntity set loginStatus=:status, updatedAt=:currentDate where deviceId=:deviceId and loginStatus="+LoginStatus.LOGIN.ordinal();
        getCurrentSession().createQuery(hql)
                .setParameter("status", loginStatus)
                .setParameter("currentDate", new Date())
                .setParameter("deviceId", deviceId).executeUpdate();
    }

    public void invalidateUserLoggedIn(Long userId, LoginStatus loginStatus) {
        String hql = "update LoginStatusEntity set loginStatus=:status, updatedAt=:currentDate where user.id=:userId and loginStatus="+LoginStatus.LOGIN.ordinal();
        getCurrentSession().createQuery(hql).setParameter("status", loginStatus)
                .setParameter("userId", userId)
                .setParameter("currentDate", new Date())
                .executeUpdate();
    }

    public LoginStatusEntity getLoginStatus(String token) {
        Criteria criteria = getCurrentSession().createCriteria(LoginStatusEntity.class)
                .add(Restrictions.eq("token", token));
                //.add(Restrictions.eq("loginStatus", LoginStatus.LOGIN));
        return (LoginStatusEntity)criteria.uniqueResult();
    }


}
