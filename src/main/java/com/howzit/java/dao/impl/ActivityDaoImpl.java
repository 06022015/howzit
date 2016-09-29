package com.howzit.java.dao.impl;

import com.howzit.java.dao.ActivityDao;
import com.howzit.java.model.ActivityAreaEntity;
import com.howzit.java.model.ActivityEntity;
import com.howzit.java.model.ActivityPhotoEntity;
import com.howzit.java.model.type.ActivityAreaType;
import com.howzit.java.model.type.ActivityType;
import com.howzit.java.model.type.Status;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Ashif.Qureshi
 * Date: 16/9/14
 * Time: 11:12 AM
 * To change this template use File | Settings | File Templates.
 */
@Repository("activityDao")
@Transactional
public class ActivityDaoImpl extends BaseDaoImpl implements ActivityDao {


    public List<ActivityEntity> getActivity(Date startDate, int maxResult) {
        Criteria criteria = getCurrentSession().createCriteria(ActivityEntity.class)
                .add(Restrictions.le("createdAt", startDate))
                .add(Restrictions.eq("status", Status.ACTIVE))
                .addOrder(Order.desc("createdAt"))
                .setMaxResults(maxResult)
                .setProjection(Projections.projectionList()
                        .add(Projections.property("id"))
                        .add(Projections.property("content"), "content")
                        .add(Projections.property("activityBy"), "activityBy")
                        .add(Projections.property("activityArea"), "activityArea")
                        .add(Projections.property("parentActivity"), "parentActivity")
                        .add(Projections.property("activityType"), "activityType")
                        .add(Projections.property("level"), "level"))
                .setResultTransformer(Transformers.aliasToBean(ActivityEntity.class));
        return criteria.list();
    }

    public ActivityEntity getActivity(Long id) {
        Criteria criteria = getCurrentSession().createCriteria(ActivityEntity.class)
                .add(Restrictions.eq("id", id))
                .add(Restrictions.eq("status", Status.ACTIVE));
        return (ActivityEntity) criteria.uniqueResult();
    }

    public ActivityEntity getActivity(Long parentActivityId, Long userId, Long activityAreaId, ActivityType[] activityType) {
        Criteria criteria = getCurrentSession().createCriteria(ActivityEntity.class)
                .createAlias("activityBy", "activityBy")
                .add(Restrictions.eq("activityBy.id", userId))
                .createAlias("activityArea", "area")
                .add(Restrictions.eq("activityArea.id", activityAreaId))
                .add(Restrictions.in("activityType", activityType));
        if (null != parentActivityId)
            criteria.createAlias("parentActivity", "parent")
                    .add(Restrictions.eq("parentActivity.id", parentActivityId));
        return (ActivityEntity) criteria.uniqueResult();
    }

    public List<ActivityEntity> getActivityByActivityAreaAndType(Long activityAreaId, ActivityType activityType,
                                                                 Date lastActivityDate, int noIfRecord) {
        Criteria criteria = getCurrentSession().createCriteria(ActivityEntity.class)
                .createAlias("activityArea", "activityArea")
                .add(Restrictions.eq("activityArea.id", activityAreaId))
                .add(Restrictions.eq("level", 0))
                .add(Restrictions.eq("status", Status.ACTIVE))
                .addOrder(Order.desc("createdAt"));
        if (null != activityType)
            criteria.add(Restrictions.eq("activityType", activityType));
        criteria.add(Restrictions.lt("createdAt", lastActivityDate))
                .setFirstResult(0).setMaxResults(noIfRecord);
        return criteria.list();
    }

    public ActivityPhotoEntity getActivityPhoto(Long activityId) {
        Criteria criteria = getCurrentSession().createCriteria(ActivityPhotoEntity.class)
                .createAlias("activity","activity")
                .add(Restrictions.eq("activity.id", activityId))
                .add(Restrictions.eq("activity.status", Status.ACTIVE));
        return (ActivityPhotoEntity)criteria.uniqueResult();
    }

    /*Not tested*/

    public Map<String, String> getActivityAreaStatistic(Long activityAreaId) {
        Map<String, String> statistic = new HashMap<String, String>();
        String countHQL = "Select activityType, count(*) from ActivityEntity where activityArea.id=:activityAreaId " +
                "and level =:level and status=:status group by activityType";
        String ratingHQL = "select avg(content) from ActivityEntity where activityArea.id=:activityAreaId" +
                " and level=:level and status=:status and activityType=" + ActivityType.RATING.ordinal();
        Session session = getCurrentSession();
        List typeCounts = session.createQuery(countHQL)
                .setParameter("activityAreaId", activityAreaId)
                .setParameter("level", 0).setParameter("status", Status.ACTIVE).list();
        for (Object typeCount : typeCounts) {
            Object[] objects = (Object[]) typeCount;
            statistic.put(((ActivityType) objects[0]).name().toLowerCase(), objects[1] + "");
        }
        Double rating = (Double) session.createQuery(ratingHQL)
                .setParameter("activityAreaId", activityAreaId)
                .setParameter("level", 0).setParameter("status", Status.ACTIVE).uniqueResult();
        if (null != rating)
            statistic.put("avg_rating", rating + "");
        return statistic;
    }

    /*Activity Area*/
    public ActivityAreaEntity getActivityArea(Long id) {
        Criteria criteria = getCurrentSession().createCriteria(ActivityAreaEntity.class)
                .add(Restrictions.eq("id", id))
                .add(Restrictions.eq("status", Status.ACTIVE));
        return (ActivityAreaEntity) criteria.uniqueResult();
    }

    public List<ActivityAreaEntity> getActivityAreaByCategory(Long categoryId, ActivityAreaType activityAreaType) {
        Criteria criteria = getCurrentSession().createCriteria(ActivityAreaEntity.class)
                .createAlias("category", "category")
                .add(Restrictions.eq("category.id", categoryId))
                .add(Restrictions.eq("category.status", Status.ACTIVE))
                .add(Restrictions.eq("status", Status.ACTIVE));
        if (null != activityAreaType)
            criteria.add(Restrictions.eq("activityAreaType", activityAreaType));
        return criteria.list();
    }
}
