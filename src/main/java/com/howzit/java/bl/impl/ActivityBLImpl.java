package com.howzit.java.bl.impl;

import com.howzit.java.bl.ActivityBL;
import com.howzit.java.dao.ActivityDao;
import com.howzit.java.dao.CategoryDao;
import com.howzit.java.dao.SearchDao;
import com.howzit.java.exception.HowItResponseStatus;
import com.howzit.java.exception.HowzitException;
import com.howzit.java.exception.NoRecordFoundException;
import com.howzit.java.model.*;
import com.howzit.java.model.type.ActivityAreaType;
import com.howzit.java.model.type.ActivityType;
import com.howzit.java.model.type.Status;
import com.howzit.java.service.wso.Activities;
import com.howzit.java.service.wso.Activity;
import com.howzit.java.service.wso.ActivityArea;
import com.howzit.java.service.wso.ActivityAreas;
import com.howzit.java.util.CommonUtil;
import com.howzit.java.util.ValidatorUtil;
import org.apache.lucene.queryParser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ashif.Qureshi
 * Date: 16/9/14
 * Time: 11:31 AM
 * To change this template use File | Settings | File Templates.
 */
@Repository("activityBL")
@Transactional
public class ActivityBLImpl extends BaseBL implements ActivityBL {

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private SearchDao searchDao;

    @Autowired
    private ValidatorUtil validatorUtil;

    @Autowired
    private CommonUtil commonUtil;

    public Activities getActivity(Date startDate) {
        startDate = null == startDate ? new Date() : startDate;
        int maxResult = Integer.parseInt(CommonUtil.getProperty("max.activity.per.request"));
        List<ActivityEntity> activities = activityDao.getActivity(startDate, maxResult);
        return getActivities(activities, false);
    }


    public Activity getActivity(Long id) {
        ActivityEntity activityEntity = activityDao.getActivity(id);
        if (null == activityEntity)
            throw new NoRecordFoundException(commonUtil.getText("404.activity.no.record.found", id + ""));
        return new Activity(activityEntity, false);
    }

    public Activity save(Activity activity, HowItResponseStatus status) {
        validatorUtil.validate(activity, status);
        if (status.hasError()) {
            saveErrorMessage(status, HttpStatus.BAD_REQUEST.value());
            return activity;
        }
        ActivityEntity parentActivity = null;
        ActivityAreaEntity activityAreaEntity = null;
        ActivityType activityType = ActivityType.values()[activity.getType()];
        if (null != activity.getParentActivityId()) {
            parentActivity = activityDao.getActivity(activity.getParentActivityId());
            if (null == parentActivity)
                throw new NoRecordFoundException(commonUtil.getText("404.activity.no.record.found",
                        activity.getParentActivityId() + "", status.getLocale()));
            if (activityType == ActivityType.SHARE && !ActivityType.isShareAble(parentActivity.getActivityType().ordinal())) {
                status.addError(commonUtil.getText("error.not.sharable.activity", status.getLocale()));
                saveErrorMessage(status, HttpStatus.BAD_REQUEST.value());
                return activity;
            }
            activityAreaEntity = parentActivity.getActivityArea();
        }
        ActivityEntity activityEntity = null;
        if (null == activityAreaEntity) {
            activityAreaEntity = activityDao.getActivityArea(activity.getActivityAreaId());
            if (null == activityAreaEntity)
                throw new NoRecordFoundException(commonUtil.getText("404.activity.area.no.record.found",
                        activity.getActivityAreaId() + "", status.getLocale()));
        } else if (activityType == ActivityType.COMMENT) {
            if (parentActivity.getLevel() >= Integer.parseInt(CommonUtil.getProperty("activity.max.level"))) {
                status.addError(commonUtil.getText("error.no.more.child.possible", status.getLocale()));
                saveErrorMessage(status, HttpStatus.BAD_REQUEST.value());
                return activity;
            }
        }
        boolean isUpdate = false;
        String message = "";
        if (activityType == ActivityType.LIKE || activityType == ActivityType.UNLIKE) {
            ActivityType[] activityTypes = {ActivityType.LIKE, ActivityType.UNLIKE};
            activityEntity = activityDao.getActivity(activity.getParentActivityId(), activity.getActivityById(),
                    activityAreaEntity.getId(), activityTypes);
            if (null != activityEntity) {
                if (activityEntity.getActivityType() == activityType)
                    throw new HowzitException(HttpStatus.CONFLICT.value(),
                            commonUtil.getText("error.activity.duplicate", status.getLocale()));
                activityEntity.setActivityType(activityType);
                activityEntity.setUpdatedAt(new Date());
                isUpdate = true;
            }
        }
        if (isUpdate) {
            message = commonUtil.getText("success.update", status.getLocale());
        } else {
            activityEntity = new ActivityEntity();
            prepare(activityEntity, activity);
            activityEntity.setActivityArea(activityAreaEntity);
            if (null != parentActivity) {
                activityEntity.setParentActivity(parentActivity);
                activityEntity.setLevel(parentActivity.getLevel() + 1);
            }
            message = commonUtil.getText("success.save", status.getLocale());
        }
        if (activityType == ActivityType.PHOTO) {
            ActivityPhotoEntity activityPhotoEntity = new ActivityPhotoEntity();
            activityPhotoEntity.setActivity(activityEntity);
            activityPhotoEntity.setPhoto(activity.getImage().getBytes());
            activityDao.save(activityPhotoEntity);
        } else
            activityDao.save(activityEntity);
        saveSuccessMessage(status, message);
        return new Activity(activityEntity, false);
    }

    public void deleteActivity(Long activityId, Long userId) {
        ActivityEntity activityEntity = activityDao.getActivity(activityId);
        if (null == activityEntity)
            throw new NoRecordFoundException("404.activity.no.record.found");
        if (activityEntity.getActivityBy().getId().equals(userId))
            activityEntity.setStatus(Status.DELETED);
        else if (activityEntity.getLevel() > 0) {
            UserEntity activityOriginator = getActivityOriginator(activityEntity);
            if (activityOriginator.getId().equals(userId))
                activityEntity.setStatus(Status.DELETED);
        } else {
            throw new HowzitException(HttpStatus.FORBIDDEN.value());
        }
        activityDao.update(activityEntity);
    }

    public byte[] getActivityImage(Long activityId) {
        ActivityPhotoEntity activityPhotoEntity = activityDao.getActivityPhoto(activityId);
        if(null!=activityPhotoEntity)
            activityPhotoEntity.getPhoto();
        return null;
    }

    private UserEntity getActivityOriginator(ActivityEntity activityEntity) {
        if (null == activityEntity.getParentActivity())
            return activityEntity.getActivityBy();
        else
            return getActivityOriginator(activityEntity.getParentActivity());
    }

    private void prepare(ActivityEntity activityEntity, Activity activity) {
        activityEntity.setContent(activity.getContent());
        UserEntity activityBy = new UserEntity();
        activityBy.setId(activity.getActivityById());
        activityEntity.setActivityBy(activityBy);
        activityEntity.setActivityType(ActivityType.values()[activity.getType()]);
        activityEntity.setCreatedAt(activity.getCreatedAt());
    }

    private Activities getActivities(List<ActivityEntity> activityEntities, boolean isChildActivity) {
        Activities activities = new Activities();
        if (null != activityEntities) {
            for (ActivityEntity activity : activityEntities) {
                activities.addActivity(new Activity(activity, isChildActivity));
            }
        }
        return activities;
    }

    /*Activity Area*/
    public ActivityArea getActivityArea(Long id, Integer activityType, Date lastActivityDate) {
        ActivityAreaEntity activityAreaEntity = activityDao.getActivityArea(id);
        if (null == activityAreaEntity)
            throw new NoRecordFoundException(commonUtil.getText("404.activity.area.no.record.found", id + ""));
        ActivityArea activityArea = new ActivityArea(activityAreaEntity);
        if (null == activityType || !ActivityType.isValidActivityType(activityType)) {
            activityArea.setStatistic(activityDao.getActivityAreaStatistic(id));
        } else {
            ActivityType type = ActivityType.values()[activityType];
            if (null == lastActivityDate)
                lastActivityDate = new Date();
            int maxResult = type == ActivityType.COMMENT ? Integer.parseInt(CommonUtil.getProperty("max.activity.record.per.request.for.comment"))
                    : Integer.parseInt(CommonUtil.getProperty("max.activity.record.per.request.except.comment"));
            List<ActivityEntity> activities = activityDao.getActivityByActivityAreaAndType(id, type, lastActivityDate, maxResult);
            activityArea.setActivityList(getActivities(activities, true).getActivities());
        }
        return activityArea;
    }

    public ActivityArea save(ActivityArea activityArea, HowItResponseStatus status) {
        validatorUtil.validate(activityArea, status);
        if (status.hasError()) {
            saveErrorMessage(status, HttpStatus.BAD_REQUEST.value());
            return activityArea;
        }
        CategoryEntity categoryEntity = categoryDao.getCategory(activityArea.getCategoryId());
        if (null == categoryEntity)
            throw new NoRecordFoundException(commonUtil.getText("404.category.no.record.found", activityArea.getCategoryId() + ""));
        ActivityAreaEntity activityAreaEntity = new ActivityAreaEntity();
        prepare(activityAreaEntity, activityArea);
        activityAreaEntity.setCategory(categoryEntity);
        activityDao.save(activityAreaEntity);
        saveSuccessMessage(status, commonUtil.getText("success.save", status.getLocale()));
        return new ActivityArea(activityAreaEntity);
    }

    public ActivityAreas getActivityAreaByCategory(Long categoryId, Integer areaType) {
        ActivityAreaType activityAreaType = null;
        if (null != areaType && ActivityAreaType.isValidAreaType(areaType))
            activityAreaType = ActivityAreaType.values()[areaType];
        List<ActivityAreaEntity> activityAreas = activityDao.getActivityAreaByCategory(categoryId, activityAreaType);
        return getActivityAreas(activityAreas);
    }

    private ActivityAreas getActivityAreas(List<ActivityAreaEntity> activityAreaEntities) {
        ActivityAreas activityAreas = new ActivityAreas();
        if (null != activityAreaEntities) {
            for (ActivityAreaEntity activityArea : activityAreaEntities) {
                activityAreas.addActivityArea(new ActivityArea(activityArea));
            }
        }
        return activityAreas;
    }

    private void prepare(ActivityAreaEntity activityAreaEntity, ActivityArea activityArea) {
        activityAreaEntity.setName(activityArea.getName());
        activityAreaEntity.setDescription(activityArea.getName());
        activityAreaEntity.setActivityAreaType(ActivityAreaType.values()[activityArea.getType()]);
        activityAreaEntity.setAddress(activityArea.getAddress());
        activityAreaEntity.setLatitude(activityArea.getLatitude());
        activityAreaEntity.setLongitude(activityArea.getLongitude());
        UserEntity createdBy = new UserEntity();
        createdBy.setId(activityArea.getCreatedById());
        activityAreaEntity.setCreatedBy(createdBy);
        activityArea.setCreatedAt(activityAreaEntity.getCreatedAt());
    }

    public ActivityAreas searchActivityArea(String searchText) {
        List<ActivityAreaEntity> activityAreaEntities = null;
        try {
            activityAreaEntities = searchDao.searchActivityArea(searchText);
        } catch (ParseException e) {
            throw new HowzitException("Unable to parse query");
        }
        ActivityAreas activityAreas = new ActivityAreas();
        for (ActivityAreaEntity activityArea : activityAreaEntities) {
            activityAreas.addActivityArea(new ActivityArea(activityArea));
        }
        return activityAreas;
    }
}
