package com.howzit.java.service.wso;

import com.howzit.java.model.type.ActivityType;

/**
 * Created by IntelliJ IDEA.
 * User: ashif
 * Date: 10/3/14
 * Time: 11:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class NoOfRecordWSO {

    private ActivityType activityType;
    private Long no;

    public NoOfRecordWSO(){
    }

    public NoOfRecordWSO(ActivityType activityType, Long no){
        this.activityType = activityType;
        this.no = no;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }
}
