package com.howzit.java.service.wso;

import com.howzit.java.model.CategoryEntity;

import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ashif.Qureshi
 * Date: 9/17/14
 * Time: 2:48 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "category")
public class Category {

    private Long id;
    private Date createdAt;
    private String name;
    private String description;
    private Category parentCategory;
    private Long parentCategoryId;
    private User createdBy;
    private Long createdById;
    private int level = 0;
    private List<Category> categories;

    public Category() {
    }


    public Category(CategoryEntity category, boolean isChild) {
        this.id = category.getId();
        this.createdAt = category.getCreatedAt();
        this.name = category.getName();
        this.description = category.getDescription();
        this.level = category.getLevel();
        if (!isChild) {
            if (null != category.getParentCategory()) {
                parentCategory = new Category(category.getParentCategory(), isChild);
                /*parentCategory.setId(category.getParentCategory().getId());
                parentCategory.setName(category.getParentCategory().getName());*/
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

    @XmlAttribute(name = "create_at")
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

    @XmlElement(name = "parent_category")
    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    @XmlTransient
    public Long getParentCategoryId() {
        if (null == getParentCategory())
            parentCategoryId = getParentCategory().getId();
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
        if (null == getParentCategory())
            setParentCategory(new Category());
        getParentCategory().setId(parentCategoryId);
    }

    @XmlElement(name = "created_by")
    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    @XmlTransient
    public Long getCreatedById() {
        if (null != getCreatedBy())
            createdById = getCreatedBy().getId();
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
        if (null == getCreatedBy())
            setCreatedBy(new User());
        getCreatedBy().setId(createdById);
    }

    @XmlAttribute
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @XmlElementWrapper(name = "categories")
    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void trim(){
        if(null!=getName())
            setName(getName().trim());
        if(null!=getDescription())
            setDescription(getDescription().trim());
    }
}
