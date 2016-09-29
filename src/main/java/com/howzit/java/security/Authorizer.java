package com.howzit.java.security;

import com.howzit.java.model.UserEntity;
import com.howzit.java.model.master.Role;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 * Created with IntelliJ IDEA.
 * UserEntity: Ashif.Qureshi
 * Date: 26/4/13
 * Time: 4:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class Authorizer implements SecurityContext {


    private UserEntity user;

    public Authorizer(final UserEntity userEntity) {
        if (userEntity != null) {
            this.user = userEntity;
        }
    }

    public Principal getUserPrincipal() {
        return user;
    }

    public boolean isUserInRole(String s) {
        boolean isUserInRole = false;
        if(null!=user && null!=user.getRoles()){
            for(Role role : user.getRoles()){
                if(role.getName().trim().equals(s.trim())){
                   isUserInRole = true;
                    break;
                }
            }
        }
        return isUserInRole;
    }

    public boolean isSecure() {
        return true;
    }

    public String getAuthenticationScheme() {
        if (user == null) {
            return null;
        }
        return SecurityContext.FORM_AUTH;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Authorizer that = (Authorizer) o;

        if (user != null ? !user.equals(that.user) : that.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return user != null ? user.hashCode() : 0;
    }
}
