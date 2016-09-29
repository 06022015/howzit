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
@XmlRootElement(name = "activities")
public class Activities {

    private List<Activity> activities;

    @XmlElement
    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public void addActivity(Activity activity){
        if(null == getActivities())
            setActivities(new ArrayList<Activity>());
        getActivities().add(activity);
    }
}

