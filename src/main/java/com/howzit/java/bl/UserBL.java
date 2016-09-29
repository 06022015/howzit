package com.howzit.java.bl;

import com.howzit.java.exception.HowItResponseStatus;
import com.howzit.java.model.UserEntity;
import com.howzit.java.service.wso.LoginStatus;
import com.howzit.java.service.wso.User;
import com.howzit.java.service.wso.Users;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * UserEntity: Ashif.Qureshi
 * Date: 20/8/14
 * Time: 12:24 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserBL {

    UserEntity loadUserByUsername(String username) throws UsernameNotFoundException;

    User getUser(Long id);

    User save(User user, HowItResponseStatus status);

    void updateProfile(String name, String value, Long id, HowItResponseStatus status);

    void changePassword(String oldPassword, String password, String confirmPassword,Long id, HowItResponseStatus status);

    Users searchUser(String searchText);

    /*login status*/

    LoginStatus login(String username, String password, String deviceId);

    void logout(String deviceId);

    UserEntity validateAuthentication(String token);

    void identify(String email, HowItResponseStatus status);

}
