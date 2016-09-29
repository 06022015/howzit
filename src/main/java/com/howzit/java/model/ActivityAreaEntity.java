package com.howzit.java.model;

import com.howzit.java.model.type.ActivityAreaType;
import com.howzit.java.model.type.Status;
import com.howzit.java.util.interceptor.IndexActivityAreaInterceptor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ashif.Qureshi
 * Date: 15/9/14
 * Time: 12:57 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Indexed(interceptor = IndexActivityAreaInterceptor.class)
@Table(name = "activity_area")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ActivityAreaEntity extends BaseEntity {

    private static final long serialVersionUID = 3256446889040622647L;

    private Long id;
    private String name;
    private String description;
    private String address;
    private Long latitude;
    private Long longitude;
    private CategoryEntity category;
    private UserEntity createdBy;
    private ActivityAreaType activityAreaType;
    private Status status = Status.ACTIVE;
    private List<ActivityEntity> activityList;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, updatable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Field(index = Index.YES,store = Store.NO, analyze = Analyze.YES)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Field(store = Store.NO, analyze = Analyze.YES, index = Index.YES)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Field(index = Index.YES, store = Store.NO)
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

    @IndexedEmbedded
    @ManyToOne()
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false, insertable = true, updatable = true)
    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = true, insertable = true, updatable = true)
    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "activity_area_type")
    public ActivityAreaType getActivityAreaType() {
        return activityAreaType;
    }

    public void setActivityAreaType(ActivityAreaType activityAreaType) {
        this.activityAreaType = activityAreaType;
    }

    @Enumerated(EnumType.ORDINAL)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /*@OneToMany(mappedBy = "activityArea", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)*/
    @Transient
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

        ActivityAreaEntity that = (ActivityAreaEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
