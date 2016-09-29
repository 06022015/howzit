package com.howzit.java.util.interceptor;

import com.howzit.java.model.UserEntity;
import org.hibernate.search.indexes.interceptor.EntityIndexingInterceptor;
import org.hibernate.search.indexes.interceptor.IndexingOverride;

/**
 * Created by IntelliJ IDEA.
 * User: ashif
 * Date: 10/16/14
 * Time: 11:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class IndexUserInterceptor implements EntityIndexingInterceptor<UserEntity> {
    public IndexingOverride onAdd(UserEntity user) {
        return user.isEnabled()&&user.isAccountNonExpired() && user.isAccountNonLocked()
                && user.isCredentialsNonExpired()?IndexingOverride.APPLY_DEFAULT:IndexingOverride.SKIP;
    }

    public IndexingOverride onUpdate(UserEntity user) {
        return user.isEnabled()&&user.isAccountNonExpired() && user.isAccountNonLocked()
                && user.isCredentialsNonExpired()?IndexingOverride.APPLY_DEFAULT:IndexingOverride.SKIP;
    }

    public IndexingOverride onDelete(UserEntity user) {
        return IndexingOverride.APPLY_DEFAULT;
    }

    public IndexingOverride onCollectionUpdate(UserEntity user) {
        return onUpdate(user);
    }
}
