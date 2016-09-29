package com.howzit.java.bl.impl;

import com.howzit.java.bl.UserBL;
import com.howzit.java.dao.SearchDao;
import com.howzit.java.dao.UserDao;
import com.howzit.java.exception.*;
import com.howzit.java.model.LoginStatusEntity;
import com.howzit.java.model.UserEntity;
import com.howzit.java.model.type.PasswordType;
import com.howzit.java.security.PasswordEncoder;
import com.howzit.java.service.wso.LoginStatus;
import com.howzit.java.service.wso.User;
import com.howzit.java.service.wso.Users;
import com.howzit.java.util.*;
import org.apache.lucene.queryParser.ParseException;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * UserEntity: Ashif.Qureshi
 * Date: 20/8/14
 * Time: 12:25 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository("userBL")
@Transactional
public class UserBLImpl extends BaseBL implements UserBL, UserDetailsService, Constants {

    private Logger logger = LoggerFactory.getLogger(UserBLImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private SearchDao searchDao;

    @Autowired
    private ValidatorUtil validatorUtil;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VelocityEngine velocityEngine;

    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity users = userDao.getUserByUsername(username);
        if (null == users)
            throw new UsernameNotFoundException(commonUtil.getText("404.username.no.record.found", username));
        return users;
    }

    public User getUser(Long id) {
        UserEntity userEntity = userDao.getUser(id);
        if (null == userEntity)
            throw new NoRecordFoundException(commonUtil.getText("404.user.no.record.found", id + ""));
        return new User(userEntity);
    }

    public User save(User user, HowItResponseStatus status) {
        validatorUtil.validate(user, status);
        if (status.hasError()) {
            saveErrorMessage(status, HttpStatus.BAD_REQUEST.value());
            return user;
        }
        UserEntity userEntity = new UserEntity();
        prepare(userEntity, user);
        userEntity.addRole(userDao.getRole(ROLE_NAME_USER));
        try {
            userDao.saveUser(userEntity);
            saveSuccessMessage(status, commonUtil.getText("success.save", status.getLocale()));
        } catch (DuplicateObjectException e) {
            throw new HowzitException(HttpStatus.CONFLICT.value(), commonUtil.getText("error.email.or.mobile.already.exist",
                    new Object[]{userEntity.getEmail(), userEntity.getMobile()}, status.getLocale()));
        }
        return new User(userEntity);
    }

    public void updateProfile(String name, String value, Long id, HowItResponseStatus status) {
        validatorUtil.validateUserUpdateAbleFiled(name, value, status);
        if (status.hasError()) {
            saveErrorMessage(status, HttpStatus.BAD_REQUEST.value());
            return;
        }
        try {
            userDao.updateProfile(name, value, id);
        } catch (SQLException e) {
            throw new HowzitException(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    public void changePassword(String oldPassword, String password, String confirmPassword, Long userId, HowItResponseStatus status) {
        validatorUtil.validatePassword(password, confirmPassword, status);
        if (status.hasError()) {
            saveErrorMessage(status, HttpStatus.BAD_REQUEST.value());
            return;
        }
        UserEntity userEntity = userDao.getUser(userId);
        if (userEntity.getPasswordType().equals(PasswordType.PERMANENT)) {
            if (ValidatorUtil.isNullOrEmpty(oldPassword)) {
                status.addError(commonUtil.getText("error.old.password.required", status.getLocale()));
                saveErrorMessage(status, HttpStatus.BAD_REQUEST.value());
                return;
            }
            if (!passwordEncoder.isPasswordValid(userEntity.getPassword(), oldPassword, null))
                throw new HowzitException(HttpStatus.PRECONDITION_FAILED.value(), commonUtil.getText("error.existing.password.wrong"));
        }
        userEntity.setPassword(passwordEncoder.encodePassword(password, null));
        userEntity.setUpdatedAt(new Date());
        userEntity.setPasswordType(PasswordType.PERMANENT);
        userDao.save(userEntity);
        userDao.invalidateUserLoggedIn(userId, com.howzit.java.model.type.LoginStatus.INVALIDATED);
        saveSuccessMessage(status, commonUtil.getText("success.update", status.getLocale()));
    }

    private void prepare(UserEntity userEntity, User user) {
        userEntity.setUsername(user.getEmail());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
        userEntity.setMobile(user.getMobileNumber());
    }

    public Users searchUser(String searchText) {
        List<UserEntity> userEntities = null;
        try {
            userEntities = searchDao.searchUser(searchText);
        } catch (ParseException e) {
            throw new HowzitException("Unable to create search query");
        }
        Users users = new Users();
        for (UserEntity user : userEntities)
            users.addUser(new User(user));
        return users;
    }

    /*login*/

    public LoginStatus login(String username, String password, String deviceId) {
        if (ValidatorUtil.isNullOrEmpty(username) || ValidatorUtil.isNullOrEmpty(password)
                || ValidatorUtil.isNullOrEmpty(deviceId))
            throw new HowzitException(HttpStatus.NON_AUTHORITATIVE_INFORMATION.value(),
                    commonUtil.getText("error.email.password.deviceId.must.be.provide"));
        UserEntity userEntity = loadUserByUsername(username);
        if (!passwordEncoder.isPasswordValid(userEntity.getPassword(), password, null))
            throw new AuthenticationException("Invalid credential");
        userDao.updateLoginStatus(deviceId, com.howzit.java.model.type.LoginStatus.TERMINATED);
        LoginStatusEntity loginStatusEntity = new LoginStatusEntity();
        loginStatusEntity.setDeviceId(deviceId);
        loginStatusEntity.setToken(generateToken(deviceId));
        loginStatusEntity.setUser(userEntity);
        userDao.save(loginStatusEntity);
        return new LoginStatus(loginStatusEntity);
    }

    public void logout(String deviceId) {
        userDao.updateLoginStatus(deviceId, com.howzit.java.model.type.LoginStatus.LOGOUT);
    }

    public UserEntity validateAuthentication(String token) {
        LoginStatusEntity loginStatusEntity = userDao.getLoginStatus(token);
        if (null == loginStatusEntity)
            throw new AuthenticationException("Invalid credential");
        if (loginStatusEntity.getLoginStatus().equals(com.howzit.java.model.type.LoginStatus.LOGIN)) {
            loginStatusEntity.setUpdatedAt(new Date());
            userDao.save(loginStatusEntity);
        } else {
            throw new HowzitException(HttpStatus.MOVED_TEMPORARILY.value(), commonUtil.getText("302.session.message"));
        }

        return loginStatusEntity.getUser();
    }

    public void identify(String email, HowItResponseStatus status) {
        if (ValidatorUtil.isNullOrEmpty(email))
            status.addError(commonUtil.getText("error.email.required"));
        else if (!ValidatorUtil.isValidEmail(email))
            status.addError(commonUtil.getText("error.email.invalid"));
        if (status.hasError()) {
            saveErrorMessage(status, HttpStatus.BAD_REQUEST.value());
            return;
        }
        UserEntity userEntity = loadUserByUsername(email);
        String tempPassword = HowzitUtil.getTempPassword();
        userEntity.setPassword(passwordEncoder.encodePassword(tempPassword, null));
        userEntity.setUpdatedAt(new Date());
        userEntity.setPasswordType(PasswordType.TEMPORARY);
        userDao.save(userEntity);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userEntity.getEmail());
        mailMessage.setSubject(commonUtil.getText("reset.password.mail.subject", status.getLocale()));
        Map<String, String> model = new HashMap<String, String>();
        model.put("name", userEntity.getName());
        model.put("password", tempPassword);
        try {
            MailSenderUtil.send(mailMessage, velocityEngine, FORGOT_PASSWORD_TEMPLATE, model);
        } catch (MessagingException e) {
            logger.error("Unable to send message:" + e.getMessage());
        }
        saveSuccessMessage(status, commonUtil.getText("password.reset.successfully.check.amil"));
    }

    private String generateToken(String deviceId) {
        String raw = deviceId + new Date().toString();
        return passwordEncoder.encodePassword(raw, null);
    }
}
