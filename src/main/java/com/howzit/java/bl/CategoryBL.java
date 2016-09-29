package com.howzit.java.bl;

import com.howzit.java.exception.HowItResponseStatus;
import com.howzit.java.service.wso.Categories;
import com.howzit.java.service.wso.Category;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ashif
 * Date: 9/20/14
 * Time: 1:07 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CategoryBL {

    Category save(Category category, HowItResponseStatus status);

    Categories getCategories();

    Category getCategory(Long id);
}
