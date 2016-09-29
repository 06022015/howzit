package com.howzit.java.service.imp;

import com.howzit.java.bl.ActivityBL;
import com.howzit.java.bl.CategoryBL;
import com.howzit.java.bl.UserBL;
import com.howzit.java.model.UserEntity;
import com.howzit.java.service.wso.User;
import com.howzit.java.util.ApplicationContextUtil;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 * Created with IntelliJ IDEA.
 * UserEntity: Ashif.Qureshi
 * Date: 25/8/14
 * Time: 12:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseServiceImpl {

    private UserEntity user;

    public BaseServiceImpl(@Context SecurityContext security) {
        this.user = (UserEntity)security.getUserPrincipal();
        //this.user=new UserEntity();
        //getUser().setId(1l);
    }

    protected UserEntity getUser() {
        return user;
    }

    protected boolean isUserInRole(String roleName){
        return getUser().isUserInRole(roleName);
    }

    protected User getActivityBy(){
        User activityBy = new User();
        activityBy.setId(getUser().getId());
        return activityBy;
    }

    protected UserBL getUserBL() {
        return (UserBL)ApplicationContextUtil.getApplicationContext().getBean("userBL");
    }

    protected ActivityBL getActivityBL() {
        return (ActivityBL)ApplicationContextUtil.getApplicationContext().getBean("activityBL");
    }

    protected CategoryBL getCategoryBL() {
        return (CategoryBL)ApplicationContextUtil.getApplicationContext().getBean("categoryBL");
    }
}
