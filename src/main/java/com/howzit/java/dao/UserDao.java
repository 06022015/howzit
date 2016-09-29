package com.howzit.java.dao;

import com.howzit.java.exception.DuplicateObjectException;
import com.howzit.java.model.LoginStatusEntity;
import com.howzit.java.model.UserEntity;
import com.howzit.java.model.master.Role;
import com.howzit.java.model.type.LoginStatus;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * UserEntity: Ashif.Qureshi
 * Date: 20/8/14
 * Time: 12:02 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserDao extends BaseDao{

    Role getRole(String name);

    UserEntity getUserByUsername(String username);

    UserEntity getUser(Long id);

    void saveUser(UserEntity userEntity) throws DuplicateObjectException;

    void updateProfile(String name, String value, Long id) throws SQLException;

    /*login*/
    void updateLoginStatus(String deviceId, LoginStatus loginStatus);

    void invalidateUserLoggedIn(Long userId, LoginStatus lo);

    LoginStatusEntity getLoginStatus(String token);
}
