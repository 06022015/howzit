package com.howzit.java.model.type;

/**
 * Created with IntelliJ IDEA.
 * User: Ashif.Qureshi
 * Date: 15/9/14
 * Time: 3:57 PM
 * To change this template use File | Settings | File Templates.
 */
public enum ActivityType {
    
    LIKE, UNLIKE,CHECK_IN, COMMENT, RATING, PHOTO, SHARE;

    public static boolean isValidActivityType(int type){
        return type<ActivityType.values().length;
    }

    public  static boolean isNonContentType(int type){
        return type==LIKE.ordinal() || type==UNLIKE.ordinal()||type==SHARE.ordinal() || type==CHECK_IN.ordinal();
    }

    public static boolean isContentType(int type){
        return type == COMMENT.ordinal() || type==RATING.ordinal();// || type==PHOTO.ordinal();
    }

    public static boolean isOnlyLevel0Type(int type){
        return type == CHECK_IN.ordinal() || type==RATING.ordinal() || type == PHOTO.ordinal();
    }

    public static boolean isShareAble(int type){
        return type==PHOTO.ordinal() || type == COMMENT.ordinal() || type==RATING.ordinal() || type==CHECK_IN.ordinal();
    }
}
