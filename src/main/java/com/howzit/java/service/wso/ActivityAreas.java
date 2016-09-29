package com.howzit.java.service.wso;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ashif
 * Date: 10/19/14
 * Time: 8:25 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "areas")
public class ActivityAreas {

    private List<ActivityArea> activityAreas;


    @XmlElement
    public List<ActivityArea> getActivityAreas() {
        return activityAreas;
    }

    public void setActivityAreas(List<ActivityArea> activityAreas) {
        this.activityAreas = activityAreas;
    }

    public void addActivityArea(ActivityArea activityArea){
        if(null==getActivityAreas())
            setActivityAreas(new ArrayList<ActivityArea>());
        getActivityAreas().add(activityArea);
    }
}
