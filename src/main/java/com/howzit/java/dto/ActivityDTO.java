package com.howzit.java.dto;

import com.howzit.java.model.UserEntity;

/**
 * Created with IntelliJ IDEA.
 * User: Ashif.Qureshi
 * Date: 9/19/14
 * Time: 1:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class ActivityDTO {

    private Long id;
    private String comment;
    private Long activityById;
    private Long parentActivityId;
    private Integer activityType;
    private Long activityAreaId;
    private String name;
    private String description;
    private String address;
    private Long latitude;
    private Long longitude;
    private Long categoryId;
    private Integer activityAreaType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    public Long getActivityById() {
        return activityById;
    }

    public void setActivityById(Long activityById) {
        this.activityById = activityById;
    }

    public Long getParentActivityId() {
        return parentActivityId;
    }

    public void setParentActivityId(Long parentActivityId) {
        this.parentActivityId = parentActivityId;
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }

    public Long getActivityAreaId() {
        return activityAreaId;
    }

    public void setActivityAreaId(Long activityAreaId) {
        this.activityAreaId = activityAreaId;
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

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getActivityAreaType() {
        return activityAreaType;
    }

    public void setActivityAreaType(Integer activityAreaType) {
        this.activityAreaType = activityAreaType;
    }

    public UserEntity getUser(){
        UserEntity user = new UserEntity();
        user.setId(getActivityById());
        return user;
    }
}
