package com.howzit.java.util.interceptor;

import com.howzit.java.model.ActivityAreaEntity;
import com.howzit.java.model.type.Status;
import org.hibernate.search.indexes.interceptor.EntityIndexingInterceptor;
import org.hibernate.search.indexes.interceptor.IndexingOverride;

/**
 * Created by IntelliJ IDEA.
 * User: ashif
 * Date: 10/18/14
 * Time: 12:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class IndexActivityAreaInterceptor implements EntityIndexingInterceptor<ActivityAreaEntity> {
    public IndexingOverride onAdd(ActivityAreaEntity activityArea) {
        return activityArea.getStatus()== Status.ACTIVE?IndexingOverride.APPLY_DEFAULT:IndexingOverride.SKIP;
    }

    public IndexingOverride onUpdate(ActivityAreaEntity activityArea) {
        return onAdd(activityArea);
    }

    public IndexingOverride onDelete(ActivityAreaEntity activityArea) {
        return IndexingOverride.APPLY_DEFAULT;
    }

    public IndexingOverride onCollectionUpdate(ActivityAreaEntity activityArea) {
        return onAdd(activityArea);
    }
}
