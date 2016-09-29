package com.howzit.java.dao;

import com.howzit.java.model.ActivityAreaEntity;
import com.howzit.java.model.UserEntity;
import org.apache.lucene.queryParser.ParseException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ashif
 * Date: 9/30/14
 * Time: 9:29 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SearchDao extends BaseDao{

    List<ActivityAreaEntity> searchActivityArea(String searchText) throws ParseException;

    List<UserEntity> searchUser(String searchText) throws ParseException;

}
