package com.howzit.java.service.wso;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ashif
 * Date: 10/19/14
 * Time: 8:33 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "users")
public class Users {

    private List<User> users;

    @XmlElement
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user){
        if(null==getUsers())
            setUsers(new ArrayList<User>());
        getUsers().add(user);
    }
}
