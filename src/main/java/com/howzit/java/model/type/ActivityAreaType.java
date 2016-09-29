package com.howzit.java.model.type;

/**
 * Created with IntelliJ IDEA.
 * User: Ashif.Qureshi
 * Date: 15/9/14
 * Time: 5:19 PM
 * To change this template use File | Settings | File Templates.
 */
public enum ActivityAreaType {

    LOCATION, PRODUCT;

    public static boolean isValidAreaType(int type){
        return type<ActivityAreaType.values().length;
    }
}
