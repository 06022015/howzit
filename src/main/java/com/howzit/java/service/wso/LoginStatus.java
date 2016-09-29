package com.howzit.java.service.wso;

import com.howzit.java.model.LoginStatusEntity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by IntelliJ IDEA.
 * User: ashif
 * Date: 10/10/14
 * Time: 10:23 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "logged_in")
public class LoginStatus {

    private String token;

    public LoginStatus(){}

    public LoginStatus(LoginStatusEntity loginStatus){
        this.token=loginStatus.getToken();
    }

    @XmlElement(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
