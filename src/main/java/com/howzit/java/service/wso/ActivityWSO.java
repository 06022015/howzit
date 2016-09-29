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
public class ActivityWSO {

    private Long id;
    private Date createdAt;
    private Integer type;
    private String content;
    private ActivityAreaWSO activityArea;
    private Long activityAreaId;
    private ActivityWSO parentActivity;
    private Long parentActivityId;
    private UserWSO activityBy;
    private Long activityById;
    private Integer level;
    private List<ActivityWSO> childActivities;

    public ActivityWSO() {
    }

    public ActivityWSO(ActivityEntity activity, boolean isChild) {
        this.id = activity.getId();
        this.createdAt = activity.getCreatedAt();
        this.type = activity.getActivityType().ordinal();
        this.content = activity.getContent();
        this.level = activity.getLevel();
        if (!isChild) {
            this.activityArea = new ActivityAreaWSO();
            this.activityArea.setId(activity.getActivityArea().getId());
            this.activityArea.setName(activity.getActivityArea().getName());
            this.activityArea.setType(activity.getActivityArea().getActivityAreaType().ordinal());
            CategoryWSO category = new CategoryWSO();
            category.setId(activity.getActivityArea().getCategory().getId());
            category.setName(activity.getActivityArea().getCategory().getName());
            this.activityArea.setCategory(category);
            if (null != activity.getParentActivity()) {
                this.parentActivity = new ActivityWSO();
                this.parentActivity.setId(activity.getParentActivity().getId());
                this.parentActivity.setType(activity.getParentActivity().getActivityType().ordinal());
                UserWSO pActivityBy = new UserWSO();
                pActivityBy.setId(activity.getParentActivity().getActivityBy().getId());
                pActivityBy.setFirstName(activity.getParentActivity().getActivityBy().getFirstName());
                pActivityBy.setLastName(activity.getParentActivity().getActivityBy().getLastName());
                this.parentActivity.setActivityBy(pActivityBy);
            }
        }
        this.activityBy = new UserWSO();
        this.activityBy.setId(activity.getActivityBy().getId());
        this.activityBy.setFirstName(activity.getActivityBy().getFirstName());
        this.activityBy.setLastName(activity.getActivityBy().getLastName());
        if (null != activity.getActivityList()) {
            this.childActivities = new ArrayList<ActivityWSO>();
            for (ActivityEntity act : activity.getActivityList()) {
                childActivities.add(new ActivityWSO(act, true));
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

    @XmlElement(name = "activity_area")
    public ActivityAreaWSO getActivityArea() {
        return activityArea;
    }

    public void setActivityArea(ActivityAreaWSO activityArea) {
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
            setActivityArea(new ActivityAreaWSO());
        getActivityArea().setId(activityAreaId);
    }

    @XmlElement(name = "parent_activity")
    public ActivityWSO getParentActivity() {
        return parentActivity;
    }

    public void setParentActivity(ActivityWSO parentActivity) {
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
            setParentActivity(new ActivityWSO());
        getParentActivity().setId(parentActivityId);
    }

    @XmlElement(name = "activity_by")
    public UserWSO getActivityBy() {
        return activityBy;
    }

    public void setActivityBy(UserWSO activityBy) {
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
            setActivityBy(new UserWSO());
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
    public List<ActivityWSO> getChildActivities() {
        return childActivities;
    }

    public void setChildActivities(List<ActivityWSO> childActivities) {
        this.childActivities = childActivities;
    }

    public void trim(){
        if(null!=getContent())
            setContent(getContent().trim());
    }
}
