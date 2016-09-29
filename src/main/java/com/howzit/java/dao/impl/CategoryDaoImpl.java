package com.howzit.java.dao.impl;

import com.howzit.java.dao.CategoryDao;
import com.howzit.java.model.CategoryEntity;
import com.howzit.java.model.type.Status;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ashif
 * Date: 9/20/14
 * Time: 1:08 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository("categoryDao")
public class CategoryDaoImpl extends BaseDaoImpl implements CategoryDao {


    public CategoryEntity getCategory(Long id) {
        Criteria criteria = getCurrentSession().createCriteria(CategoryEntity.class)
                .add(Restrictions.eq("id", id))
                .add(Restrictions.eq("status", Status.ACTIVE));
        return (CategoryEntity)criteria.uniqueResult();
    }

    public List<CategoryEntity> getCategories(int level, Status status) {
        Criteria criteria = getCurrentSession().createCriteria(CategoryEntity.class)
                .add(Restrictions.eq("level",level))
                .add(Restrictions.eq("status", status));
        return criteria.list();
    }

    public List<CategoryEntity> getChildCategories(Long parentCategoryId) {
        Criteria criteria = getCurrentSession().createCriteria(CategoryEntity.class)
                .createAlias("parentCategory","parent")
                .add(Restrictions.eq("parent.id", parentCategoryId))
                .add(Restrictions.eq("status",Status.ACTIVE));
        return criteria.list();
    }
}
