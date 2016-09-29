package com.howzit.java.model;

import com.howzit.java.model.type.Gender;

import javax.persistence.Transient;

/**
 * Created by IntelliJ IDEA.
 * User: ashif
 * Date: 10/6/14
 * Time: 10:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserProfileEntity {

    private Gender gender;

    @Transient
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

}
