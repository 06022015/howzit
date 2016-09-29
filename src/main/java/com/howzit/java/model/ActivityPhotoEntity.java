package com.howzit.java.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: ashif
 * Date: 10/19/14
 * Time: 10:16 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "activity_photo")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ActivityPhotoEntity {

    private static final long serialVersionUID = 3256446889040622647L;

    private Long id;
    private byte[] photo;
    private ActivityEntity activity;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(unique = true, nullable = false, updatable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "photo", nullable = true, insertable = true, updatable = true, length = 10485760, precision = 0)
    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @ManyToOne
    @JoinColumn(name = "activity_id", referencedColumnName = "id", nullable = false, insertable = true, updatable = true)
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    public ActivityEntity getActivity() {
        return activity;
    }

    public void setActivity(ActivityEntity activity) {
        this.activity = activity;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
