package com.howzit.java.service.imp;

import com.howzit.java.exception.HowItResponseStatus;
import com.howzit.java.exception.HowzitException;
import com.howzit.java.service.UserService;
import com.howzit.java.service.wso.Activities;
import com.howzit.java.service.wso.Activity;
import com.howzit.java.util.Constants;
import com.sun.jersey.server.impl.application.WebApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * UserEntity: Ashif.Qureshi
 * Date: 20/8/14
 * Time: 12:26 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/dash")
@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
public class UserServiceImpl extends BaseServiceImpl implements UserService, Constants {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(@Context SecurityContext security) {
        super(security);
    }

    @GET
    public Response dashBoard(@QueryParam(PARAM_START_DATE) Date startDate) {
        Activities activities = getActivityBL().getActivity(startDate);
        return Response.ok(activities).build();
    }

    @GET
    @Path("profile")
    public Response getProfile(@QueryParam("id") Long id) {
        if (null == id)
            id = getUser().getId();
        return Response.ok(getUserBL().getUser(id)).build();
    }


    @PUT
    @Path("profile")
    public Response updateProfile(@Context UriInfo uriInfo) {
        MultivaluedMap<String, String> requestData = ((WebApplicationContext) uriInfo).getContainerRequest()
                .getQueryParameters();
        if (null != requestData && requestData.containsKey(PARAM_FIELD_NAME)) {
            HowItResponseStatus status = new HowItResponseStatus();
            getUserBL().updateProfile(requestData.getFirst(PARAM_FIELD_NAME),
                    requestData.getFirst(PARAM_FIELD_VALUE), getUser().getId(), status);
            return status.getCode()==HttpStatus.OK.value()?Response.ok().build()
                    :Response.status(status.getCode()).entity(status).build();
        }
        throw new HowzitException(HttpStatus.BAD_REQUEST.value());
    }

    @POST
    @Path("/change/password")
    public Response identify(@FormParam(PARAM_USER_OLD_PASSWORD) String oldPassword,
                             @FormParam(PARAM_USER_PASSWORD) String password,
                             @FormParam(PARAM_USER_CONFIRM_PASSWORD) String confirmPassword) {
        HowItResponseStatus status = new HowItResponseStatus();
        getUserBL().changePassword(oldPassword, password, confirmPassword,getUser().getId(), status);
        return Response.status(status.getCode()).entity(status).build();
    }
}
