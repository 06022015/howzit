package com.howzit.java.model;

import com.howzit.java.model.type.ActivityType;
import com.howzit.java.model.type.Status;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ashif.Qureshi
 * Date: 15/9/14
 * Time: 12:55 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "activity")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ActivityEntity extends BaseEntity{

    private static final long serialVersionUID = 3256446889040622647L;

    private Long id;
    private String content;
    private UserEntity activityBy;
    private ActivityAreaEntity activityArea;
    private ActivityEntity parentActivity;
    private ActivityType activityType;
    private int level=0;
    private Status status = Status.ACTIVE;
    private List<ActivityEntity> activityList;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(unique = true, nullable = false, updatable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "content", columnDefinition = "blob")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ManyToOne
    @JoinColumn(name = "activity_by", referencedColumnName = "id", nullable = false, insertable = true, updatable = true)
    public UserEntity getActivityBy() {
        return activityBy;
    }

    public void setActivityBy(UserEntity activityBy) {
        this.activityBy = activityBy;
    }

    @ManyToOne
    @JoinColumn(name = "activity_area_id", referencedColumnName = "id", nullable = false, insertable = true, updatable = true)
    /*@org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)*/
    public ActivityAreaEntity getActivityArea() {
        return activityArea;
    }

    public void setActivityArea(ActivityAreaEntity activityArea) {
        this.activityArea = activityArea;
    }

    @ManyToOne
    @JoinColumn(name = "parent_activity_id", referencedColumnName = "id", nullable = true, insertable = true, updatable = true)
    public ActivityEntity getParentActivity() {
        return parentActivity;
    }

    public void setParentActivity(ActivityEntity parentActivity) {
        this.parentActivity = parentActivity;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "activity_type")
    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Enumerated(EnumType.ORDINAL)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "parentActivity", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    public List<ActivityEntity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<ActivityEntity> activityList) {
        this.activityList = activityList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityEntity that = (ActivityEntity) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
