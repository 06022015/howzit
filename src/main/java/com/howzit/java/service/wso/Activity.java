package com.howzit.java.service.wso;

import com.howzit.java.model.ActivityEntity;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ashif.Qureshi
 * Date: 9/17/14
 * Time: 2:48 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "activity")
public class Activity {

    private Long id;
    private Date createdAt;
    private Integer type;
    private String content;
    private String image;
    private ActivityArea activityArea;
    private Long activityAreaId;
    private Activity parentActivity;
    private Long parentActivityId;
    private User activityBy;
    private Long activityById;
    private Integer level;
    private List<Activity> childActivities;

    public Activity() {
    }

    public Activity(ActivityEntity activity, boolean isChild) {
        this.id = activity.getId();
        this.createdAt = activity.getCreatedAt();
        this.type = activity.getActivityType().ordinal();
        this.content = activity.getContent();
        this.level = activity.getLevel();
        if (!isChild) {
            this.activityArea = new ActivityArea();
            this.activityArea.setId(activity.getActivityArea().getId());
            this.activityArea.setName(activity.getActivityArea().getName());
            this.activityArea.setType(activity.getActivityArea().getActivityAreaType().ordinal());
            Category category = new Category();
            category.setId(activity.getActivityArea().getCategory().getId());
            category.setName(activity.getActivityArea().getCategory().getName());
            this.activityArea.setCategory(category);
            if (null != activity.getParentActivity()) {
                this.parentActivity = new Activity();
                this.parentActivity.setId(activity.getParentActivity().getId());
                this.parentActivity.setType(activity.getParentActivity().getActivityType().ordinal());
                User pActivityBy = new User();
                pActivityBy.setId(activity.getParentActivity().getActivityBy().getId());
                pActivityBy.setFirstName(activity.getParentActivity().getActivityBy().getFirstName());
                pActivityBy.setLastName(activity.getParentActivity().getActivityBy().getLastName());
                this.parentActivity.setActivityBy(pActivityBy);
            }
        }
        this.activityBy = new User();
        this.activityBy.setId(activity.getActivityBy().getId());
        this.activityBy.setFirstName(activity.getActivityBy().getFirstName());
        this.activityBy.setLastName(activity.getActivityBy().getLastName());
        if (null != activity.getActivityList()) {
            this.childActivities = new ArrayList<Activity>();
            for (ActivityEntity act : activity.getActivityList()) {
                childActivities.add(new Activity(act, true));
            }
        }
    }

    @XmlAttribute
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlAttribute(name = "created_at")
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @XmlAttribute
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @XmlElement(name = "activity_area")
    public ActivityArea getActivityArea() {
        return activityArea;
    }

    public void setActivityArea(ActivityArea activityArea) {
        this.activityArea = activityArea;
    }

    @XmlTransient
    public Long getActivityAreaId() {
        if (null != getActivityArea())
            activityAreaId = getActivityArea().getId();
        return activityAreaId;
    }

    public void setActivityAreaId(Long activityAreaId) {
        this.activityAreaId = activityAreaId;
        if (null == getActivityArea())
            setActivityArea(new ActivityArea());
        getActivityArea().setId(activityAreaId);
    }

    @XmlElement(name = "parent_activity")
    public Activity getParentActivity() {
        return parentActivity;
    }

    public void setParentActivity(Activity parentActivity) {
        this.parentActivity = parentActivity;
    }

    @XmlTransient
    public Long getParentActivityId() {
        if (null != getParentActivity())
            parentActivityId = getParentActivity().getId();
        return parentActivityId;
    }

    public void setParentActivityId(Long parentActivityId) {
        this.parentActivityId = parentActivityId;
        if (null == getParentActivity())
            setParentActivity(new Activity());
        getParentActivity().setId(parentActivityId);
    }

    @XmlElement(name = "activity_by")
    public User getActivityBy() {
        return activityBy;
    }

    public void setActivityBy(User activityBy) {
        this.activityBy = activityBy;
    }

    @XmlTransient
    public Long getActivityById() {
        if (null != getActivityBy())
            activityById = getActivityBy().getId();
        return activityById;
    }

    public void setActivityById(Long activityById) {
        this.activityById = activityById;
        if (null == getActivityBy())
            setActivityBy(new User());
        getActivityBy().setId(activityById);
    }

    @XmlAttribute
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @XmlElementWrapper(name = "activities")
    @XmlElement(name = "activity")
    public List<Activity> getChildActivities() {
        return childActivities;
    }

    public void setChildActivities(List<Activity> childActivities) {
        this.childActivities = childActivities;
    }

    public void trim(){
        if(null!=getContent())
            setContent(getContent().trim());
    }
}
