package com.howzit.java.dao;

import com.howzit.java.model.CategoryEntity;
import com.howzit.java.model.type.Status;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ashif
 * Date: 9/20/14
 * Time: 1:08 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CategoryDao extends BaseDao{

    CategoryEntity getCategory(Long id);

    List<CategoryEntity> getCategories(int level, Status status);

    List<CategoryEntity> getChildCategories(Long parentCategoryId);
}
