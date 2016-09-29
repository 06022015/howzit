package com.howzit.java.service.wso;

import com.howzit.java.model.ActivityAreaEntity;
import com.howzit.java.model.ActivityEntity;
import com.howzit.java.util.MapAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Ashif.Qureshi
 * Date: 9/17/14
 * Time: 2:48 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "area")
public class ActivityArea {

    private Long id;
    private Date createdAt;
    private String name;
    private String description;
    private String address;
    private Long latitude;
    private Long longitude;
    private Category category;
    private Long categoryId;
    private Long createdById;
    private Integer type;
    private List<Activity> activityList;
    private Map<String, String> statistic = new HashMap<String, String>();

    public ActivityArea() {
    }

    public ActivityArea(ActivityAreaEntity activityArea) {
        this.id = activityArea.getId();
        this.createdAt = activityArea.getCreatedAt();
        this.name = activityArea.getName();
        this.description = this.getDescription();
        this.address = activityArea.getAddress();
        this.latitude = activityArea.getLatitude();
        this.longitude = activityArea.getLongitude();
        this.category = new Category(activityArea.getCategory(), false);
        /*category.setId(activityArea.getCategory().getId());
        category.setName(activityArea.getCategory().getName());*/
        this.type = activityArea.getActivityAreaType().ordinal();
        if (null != activityArea.getActivityList()) {
            this.activityList = new ArrayList<Activity>();
            for (ActivityEntity activity : activityArea.getActivityList()) {
                this.activityList.add(new Activity(activity, true));
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @XmlAttribute
    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    @XmlAttribute
    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    @XmlElement(name = "category")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @XmlTransient
    public Long getCategoryId() {
        if (null != getCategory())
            categoryId = getCategory().getId();
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
        if (null == getCategory())
            setCategory(new Category());
        getCategory().setId(categoryId);
    }

    @XmlTransient
    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    @XmlAttribute
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @XmlElementWrapper(name = "activities")
    @XmlElement(name = "activity")
    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    @XmlJavaTypeAdapter(MapAdapter.class)
    @XmlElement(name = "statistic")
    public Map<String, String> getStatistic() {
        return statistic;
    }

    public void setStatistic(Map<String, String> statistic) {
        this.statistic = statistic;
    }

    public void addStatistic(String key, String value) {
        getStatistic().put(key, value);
    }

    public void trim(){
        if(null!=getName())
            setName(getName().trim());
        if(null!=getDescription())
            setDescription(getDescription().trim());
        if(null !=getAddress())
            setDescription(getDescription().trim());
    }
}
