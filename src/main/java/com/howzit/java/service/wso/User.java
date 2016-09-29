package com.howzit.java.service.wso;

import com.howzit.java.model.UserEntity;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Ashif.Qureshi
 * Date: 9/17/14
 * Time: 2:47 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "user")
public class User {

    private Long id;
    private Date createdAt;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String mobileNumber;

    public User(){}

    public User(UserEntity user){
        this.id=user.getId();
        this.createdAt=user.getCreatedAt();
        this.firstName=user.getFirstName();
        this.lastName=user.getLastName();
        this.username=user.getUsername();
        this.email=user.getEmail();
        this.mobileNumber=user.getMobile();
    }

    @XmlAttribute
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlAttribute(name = "created_at")
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @XmlElement(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @XmlElement(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    //@XmlElement(name = "username")
    @XmlTransient
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @XmlTransient
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlTransient
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement(name = "mobile_number")
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void trim(){
        if(null!=getFirstName())
            setFirstName(getFirstName().trim());
        if(null!=getLastName())
            setLastName(getLastName().trim());
        if(null!=getEmail())
            setEmail(getEmail().trim());
        if(null!=getUsername())
            setUsername(getUsername().trim());
        /*if(null!=getPassword())
            setPassword(getPassword().trim());*/
        if(null!=getMobileNumber())
            setMobileNumber(getMobileNumber().trim());
    }
}
