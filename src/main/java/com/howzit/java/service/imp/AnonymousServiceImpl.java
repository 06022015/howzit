package com.howzit.java.service.imp;

import com.howzit.java.bl.UserBL;
import com.howzit.java.exception.HowItResponseStatus;
import com.howzit.java.service.AnonymousService;
import com.howzit.java.service.wso.LoginStatus;
import com.howzit.java.service.wso.User;
import com.howzit.java.util.ApplicationContextUtil;
import com.howzit.java.util.Constants;
import org.springframework.http.HttpStatus;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by IntelliJ IDEA.
 * User: ashif
 * Date: 10/6/14
 * Time: 10:42 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/ann")
@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
public class AnonymousServiceImpl implements AnonymousService, Constants {

    public UserBL getUserBL() {
        return (UserBL) ApplicationContextUtil.getApplicationContext().getBean("userBL");
    }


    @POST
    @Path("/login")
    public Response login(@FormParam(PARAM_USER_EMAIL) String username,
                          @FormParam(PARAM_USER_PASSWORD) String password,
                          @FormParam(PARAM_USER_DEVICE_ID) String deviceId) {
        LoginStatus loginStatus = getUserBL().login(username, password, deviceId);
        return Response.ok().entity(loginStatus).build();
    }

    @GET
    @Path("/logout")
    public Response logout(@QueryParam("device_id") String deviceId) {
        getUserBL().logout(deviceId);
        return Response.ok().build();
    }


    @POST
    @Path("/register")
    public Response saveUser(@FormParam(PARAM_USER_EMAIL) String email,
                             @FormParam(PARAM_USER_PASSWORD) String password,
                             @FormParam(PARAM_USER_CONFIRM_PASSWORD) String confirmPassword,
                             @FormParam(PARAM_USER_FIRST_NAME) String firstName,
                             @FormParam(PARAM_USER_LAST_NAME) String lastName,
                             @FormParam(PARAM_USER_MOBILE) String mobile) {
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setConfirmPassword(confirmPassword);
        user.setMobileNumber(mobile);
        HowItResponseStatus status = new HowItResponseStatus();
        user = getUserBL().save(user, status);
        return status.getCode() == HttpStatus.OK.value()
                ? Response.ok(user).build() : Response.status(status.getCode()).entity(status).build();
    }

    @POST
    @Path("/help/identify")
    public Response identify(@FormParam("email") String email) {
        HowItResponseStatus status = new HowItResponseStatus();
        getUserBL().identify(email, status);
        return Response.status(status.getCode()).entity(status).build();
    }
}
