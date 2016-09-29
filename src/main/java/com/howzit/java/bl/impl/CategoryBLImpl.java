package com.howzit.java.bl.impl;

import com.howzit.java.bl.CategoryBL;
import com.howzit.java.dao.CategoryDao;
import com.howzit.java.exception.HowItResponseStatus;
import com.howzit.java.exception.NoRecordFoundException;
import com.howzit.java.model.CategoryEntity;
import com.howzit.java.model.UserEntity;
import com.howzit.java.model.type.Status;
import com.howzit.java.service.wso.Categories;
import com.howzit.java.service.wso.Category;
import com.howzit.java.util.CommonUtil;
import com.howzit.java.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ashif
 * Date: 9/20/14
 * Time: 1:07 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository("categoryBL")
@Transactional
public class CategoryBLImpl extends BaseBL implements CategoryBL {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ValidatorUtil validatorUtil;

    @Autowired
    private CommonUtil commonUtil;

    public Category save(Category category, HowItResponseStatus status) {
        validatorUtil.validate(category, status);
        if (status.hasError()) {
            saveErrorMessage(status, HttpStatus.BAD_REQUEST.value());
            return category;
        }
        CategoryEntity parentCategoryEntity = null;
        if (null != category.getParentCategoryId()) {
            parentCategoryEntity = categoryDao.getCategory(category.getParentCategoryId());
            if (null == parentCategoryEntity)
                throw new NoRecordFoundException(commonUtil.getText("404.category.no.record.found", category.getParentCategoryId() + ""));
            if (parentCategoryEntity.getLevel() >= Integer.parseInt(CommonUtil.getProperty("category.max.level"))) {
                status.addError(commonUtil.getText("error.no.more.child.possible", status.getLocale()));
                saveErrorMessage(status, HttpStatus.BAD_REQUEST.value());
                return category;
            }
        }
        CategoryEntity categoryEntity = new CategoryEntity();
        if (null != parentCategoryEntity) {
            categoryEntity.setParentCategory(parentCategoryEntity);
            categoryEntity.setLevel(parentCategoryEntity.getLevel() + 1);
        }
        prepare(categoryEntity, category);
        categoryDao.save(categoryEntity);
        saveSuccessMessage(status, commonUtil.getText("success.save", status.getLocale()));
        return new Category(categoryEntity,false);
    }

    public Categories getCategories() {
        List<CategoryEntity> categoryEntities = categoryDao.getCategories(0, Status.ACTIVE);
        return get(categoryEntities,false);
    }

    private Categories get(List<CategoryEntity> categoryEntities, boolean isChild) {
        Categories categories = new Categories();
        if (null != categoryEntities) {
            for (CategoryEntity categoryEntity : categoryEntities) {
                categories.addCategory(new Category(categoryEntity,isChild));
            }
        }
        return categories;
    }

    public Category getCategory(Long id) {
        CategoryEntity categoryEntity = categoryDao.getCategory(id);
        if (null == categoryEntity)
            throw new NoRecordFoundException(commonUtil.getText("404.category.no.record.found", id + ""));
        Category category = new Category(categoryEntity, false);
        category.setCategories(get(categoryDao.getChildCategories(id), true).getCategories());
        return category;
    }

    private void prepare(CategoryEntity categoryEntity, Category category) {
        categoryEntity.setName(category.getName());
        categoryEntity.setDescription(category.getDescription());
        UserEntity createdBy = new UserEntity();
        createdBy.setId(category.getCreatedById());
        categoryEntity.setCreatedBy(createdBy);
        category.setCreatedAt(category.getCreatedAt());
    }
}
