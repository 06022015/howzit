package com.howzit.java.service.imp;

import com.howzit.java.exception.HowItResponseStatus;
import com.howzit.java.service.ActivityService;
import com.howzit.java.service.wso.Activity;
import com.howzit.java.service.wso.ActivityArea;
import com.howzit.java.service.wso.ActivityAreas;
import com.howzit.java.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ashif.Qureshi
 * Date: 15/9/14
 * Time: 1:48 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/activity")
@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
public class ActivityServiceImpl extends BaseServiceImpl implements ActivityService, Constants {

    private Logger logger = LoggerFactory.getLogger(ActivityServiceImpl.class);


    public ActivityServiceImpl(@Context SecurityContext security) {
        super(security);
    }

    @GET
    @Path("/{id}")
    public Response getActivity(@PathParam("id") Long id) {
        return Response.ok(getActivityBL().getActivity(id)).build();
    }

    @POST
    @Path("/save")
    public Response saveActivity(@FormParam(PARAM_ACTIVITY_TYPE) Integer activityType,
                                 @FormParam(PARAM_CONTENT) String content,
                                 @FormParam(PARAM_ACTIVITY_IMAGE) String image,
                                 @FormParam(PARAM_PARENT_ACTIVITY_ID) Long parentActivityId,
                                 @FormParam(PARAM_ACTIVITY_AREA_ID) Long activityAreaId) {
        Activity activity = new Activity();
        activity.setContent(content);
        activity.setImage(image);
        activity.setType(activityType);
        activity.setParentActivityId(parentActivityId);
        activity.setActivityAreaId(activityAreaId);
        activity.setActivityBy(getActivityBy());
        HowItResponseStatus status = new HowItResponseStatus();
        activity  = getActivityBL().save(activity, status);
        return status.getCode()== HttpStatus.OK.value()
                ?Response.ok(activity).build(): Response.status(status.getCode()).entity(status).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteActivity(@PathParam("id") Long id) {
        getActivityBL().deleteActivity(id, getUser().getId());
        return Response.ok().build();
    }

    /*Activity Area*/
    @GET
    @Path("/area/{id}")
    public Response getActivityByArea(@PathParam("id") Long id,
                                      @QueryParam("activity_type")Integer activityType,
                                      @QueryParam("date") Date lastActivityDate) {
        return Response.ok(getActivityBL().getActivityArea(id, activityType, lastActivityDate)).build();
    }

    @POST
    @Path("/area/save")
    public Response saveActivityArea(@FormParam(PARAM_ACTIVITY_AREA_NAME)String name,
                                     @FormParam(PARAM_ACTIVITY_AREA_DESCRIPTION)String description,
                                     @FormParam(PARAM_ACTIVITY_AREA_ADDRESS)String address,
                                     @FormParam(PARAM_ACTIVITY_AREA_LATITUDE)Long latitude,
                                     @FormParam(PARAM_ACTIVITY_AREA_LONGITUDE)Long longitude,
                                     @FormParam(PARAM_ACTIVITY_AREA_TYPE)Integer type,
                                     @FormParam(PARAM_CATEGORY_ID)Long categoryId){
        ActivityArea activityArea = new ActivityArea();
        activityArea.setName(name);
        activityArea.setDescription(description);
        activityArea.setAddress(address);
        activityArea.setLatitude(latitude);
        activityArea.setLongitude(longitude);
        activityArea.setType(type);
        activityArea.setCategoryId(categoryId);
        activityArea.setCreatedById(getActivityBy().getId());
        HowItResponseStatus status = new HowItResponseStatus();
        activityArea = getActivityBL().save(activityArea, status);
        return status.getCode()== HttpStatus.OK.value()
                ?Response.ok(activityArea).build(): Response.status(status.getCode()).entity(status).build();
    }

    @GET
    @Path("/area/category/{id}")
    public Response getActivityAreaByCategory(@PathParam("id")Long categoryId, @QueryParam("type")Integer areaType){
        ActivityAreas activityAreas = getActivityBL().getActivityAreaByCategory(categoryId, areaType);
        return Response.ok(activityAreas).build();
    }

}
