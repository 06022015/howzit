package com.howzit.java.bl;

import com.howzit.java.exception.HowItResponseStatus;
import com.howzit.java.service.wso.Activities;
import com.howzit.java.service.wso.Activity;
import com.howzit.java.service.wso.ActivityArea;
import com.howzit.java.service.wso.ActivityAreas;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ashif.Qureshi
 * Date: 16/9/14
 * Time: 11:31 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ActivityBL {

    Activities getActivity(Date startDate);

    Activity getActivity(Long id);

    Activity save(Activity activity, HowItResponseStatus status);

    void deleteActivity(Long activityId, Long userId);

    byte[] getActivityImage(Long activityId);

    /*Activity Area*/
    ActivityArea getActivityArea(Long id, Integer activityType, Date lastActivityDate);

    ActivityArea save(ActivityArea activityArea, HowItResponseStatus status);

    ActivityAreas getActivityAreaByCategory(Long categoryId, Integer areaType);

    ActivityAreas searchActivityArea(String searchText);
}
