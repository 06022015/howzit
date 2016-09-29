package com.howzit.java.util.interceptor;

import com.howzit.java.model.CategoryEntity;
import com.howzit.java.model.type.Status;
import org.hibernate.search.indexes.interceptor.EntityIndexingInterceptor;
import org.hibernate.search.indexes.interceptor.IndexingOverride;

/**
 * Created by IntelliJ IDEA.
 * User: ashif
 * Date: 10/18/14
 * Time: 11:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class IndexCategoryInterceptor implements EntityIndexingInterceptor<CategoryEntity> {
    
    public IndexingOverride onAdd(CategoryEntity category) {
        return category.getStatus()== Status.ACTIVE?IndexingOverride.APPLY_DEFAULT:IndexingOverride.SKIP;
    }

    public IndexingOverride onUpdate(CategoryEntity category) {
        return category.getStatus()== Status.ACTIVE?IndexingOverride.APPLY_DEFAULT:IndexingOverride.SKIP;
    }

    public IndexingOverride onDelete(CategoryEntity category) {
        return IndexingOverride.APPLY_DEFAULT;
    }

    public IndexingOverride onCollectionUpdate(CategoryEntity category) {
        return onUpdate(category);
    }
}
