package com.howzit.java.dao.impl;

import com.howzit.java.dao.SearchDao;
import com.howzit.java.model.ActivityAreaEntity;
import com.howzit.java.model.UserEntity;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.util.Version;
import org.hibernate.Session;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.QueryContextBuilder;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ashif
 * Date: 9/30/14
 * Time: 9:29 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository("searchDao")
public class SearchDaoImpl extends BaseDaoImpl implements SearchDao {


    public List<ActivityAreaEntity> searchActivityArea(String searchText) throws ParseException {
        Query query = buildMultiFieldSearchQuery(new String[]{"name", "description", "address", "category.name","category.parentCategory.name"}, searchText+"*");
        Session session = getCurrentSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        /*Query query = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(ActivityAreaEntity.class).get()
                .keyword().wildcard().onField("name").matching(searchText+"*").createQuery();*/
        return fullTextSession.createFullTextQuery(query, ActivityAreaEntity.class).list();
        /*return getFullTextSession().createFullTextQuery(query, ActivityAreaEntity.class)
                .setSort(new Sort(new SortField("name", SortField.STRING))).list();*/
    }


    public List<UserEntity> searchUser1(String query){
        QueryBuilder qb = getFullTextSession().getSearchFactory().buildQueryBuilder().forEntity(UserEntity.class).get();
        //qb.bool().must(qb.)
        return null;
    }

    public List<UserEntity> searchUser(String searchText) throws ParseException {
         Query query = buildMultiFieldSearchQuery(new String[]{"first_name","last_name","email","mobile","username"}, searchText+"*");
        return getFullTextSession().createFullTextQuery(query, UserEntity.class)
                .setSort(new Sort(new SortField("firstName", SortField.STRING),new SortField("lastName", SortField.STRING))).list();
    }



    private Query buildMultiFieldSearchQuery(String fieldName[], String searchText) throws ParseException {
        return new MultiFieldQueryParser(Version.LUCENE_31, fieldName,
                               new StandardAnalyzer(Version.LUCENE_31)).parse(searchText);
    }


    private FullTextQuery buildFullTextQuery(Query query,Class clazz, String index, int firstResult, int maxResults) {
        return getFullTextSession()
                .createFullTextQuery(query, clazz)
                .setSort(new Sort(new SortField(index, SortField.STRING)))
                .setFirstResult(firstResult)
                .setMaxResults(maxResults);
    }

    private Query buildPhraseQuery(Class clazz, String field, String value) {
        return getFullTextSession().getSearchFactory()
                .buildQueryBuilder().forEntity(clazz)
                .get().phrase().onField(field)
                .sentence(value).createQuery();
    }

    private Query buildPrefixQuery(String fieldName, String value) {
        return new PrefixQuery(new Term(fieldName, value));
    }



}
