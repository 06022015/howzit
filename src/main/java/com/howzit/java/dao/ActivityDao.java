package com.howzit.java.dao;

import com.howzit.java.model.ActivityAreaEntity;
import com.howzit.java.model.ActivityEntity;
import com.howzit.java.model.ActivityPhotoEntity;
import com.howzit.java.model.type.ActivityAreaType;
import com.howzit.java.model.type.ActivityType;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Ashif.Qureshi
 * Date: 16/9/14
 * Time: 11:12 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ActivityDao extends BaseDao {

    List<ActivityEntity> getActivity(Date startDate, int maxResult);

    ActivityEntity getActivity(Long id);

    ActivityEntity getActivity(Long parentActivityId, Long userId, Long activityAreaId, ActivityType[] activityType);

    List<ActivityEntity> getActivityByActivityAreaAndType(Long activityAreaId, ActivityType type, Date lastActivityDate, int noIfRecord);

    ActivityPhotoEntity getActivityPhoto(Long activityId);

    /*Activity Area*/
    ActivityAreaEntity getActivityArea(Long id);

    Map<String, String> getActivityAreaStatistic(Long activityAreaId);

    List<ActivityAreaEntity>  getActivityAreaByCategory(Long categoryId, ActivityAreaType activityAreaType);
}
