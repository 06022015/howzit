package com.howzit.java.service.imp;

import com.howzit.java.service.SearchService;
import com.howzit.java.service.wso.ActivityArea;
import com.howzit.java.service.wso.ActivityAreas;
import com.howzit.java.service.wso.User;
import com.howzit.java.service.wso.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ashif
 * Date: 9/30/14
 * Time: 11:51 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/search")
@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
public class SearchServiceImpl extends BaseServiceImpl implements SearchService {

     private Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);


    public SearchServiceImpl(@Context SecurityContext security) {
        super(security);
    }

    @GET
    @Path("/area")
    public Response getActivityArea(@QueryParam("query") String searchText) {
        ActivityAreas activityAreas = getActivityBL().searchActivityArea(searchText);
        return Response.ok(activityAreas).build();
    }


    @GET
    @Path("/user")
    public Response getUser(@QueryParam("query") String searchText) {
        Users users = getUserBL().searchUser(searchText);
        return Response.ok(users).build();
    }

}
