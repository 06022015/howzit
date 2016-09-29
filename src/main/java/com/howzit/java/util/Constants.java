package com.howzit.java.util;

import com.howzit.java.exception.AuthenticationException;
import com.howzit.java.exception.HowzitException;
import org.springframework.http.HttpStatus;

/**
 * Created with IntelliJ IDEA.
 * UserEntity: Ashif.Qureshi
 * Date: 20/8/14
 * Time: 12:26 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Constants {

    static final String SECURITY_TOKEN = "SecurityToken";
    static final String AUTHORIZATION_PROPERTY = "Authorization";
    static final String AUTHENTICATION_SCHEME = "Basic";

    static final String ROLE_NAME_USER = "ROLE_USER";
    static final String ROLE_NAME_ADMIN = "ROLE_ADMIN";

    static final String FORGOT_PASSWORD_TEMPLATE = "forgotPassword.vm";


    /*Activity WSO form param name*/
    static final String PARAM_ACTIVITY_IMAGE="image";
    static final String PARAM_ACTIVITY_TYPE = "type";
    static final String PARAM_CONTENT = "content";
    static final String PARAM_PARENT_ACTIVITY_ID = "parent_activity_id";
    static final String PARAM_ACTIVITY_AREA_ID = "activity_area_id";
    static final String PARAM_ACTIVITY_AREA_TYPE = "type";
    static final String PARAM_ACTIVITY_AREA_NAME = "name";
    static final String PARAM_ACTIVITY_AREA_DESCRIPTION = "description";
    static final String PARAM_ACTIVITY_AREA_ADDRESS = "address";
    static final String PARAM_ACTIVITY_AREA_LATITUDE = "latitude";
    static final String PARAM_ACTIVITY_AREA_LONGITUDE = "longitude";
    static final String PARAM_CATEGORY_ID = "category_id";

    static final String PARAM_CATEGORY_NAME = "name";
    static final String PARAM_CATEGORY_DESCRIPTION = "description";
    static final String PARAM_PARENT_CATEGORY_ID = "parent_category_id";

    static final String PARAM_USER_EMAIL = "email";
    static final String PARAM_USER_PASSWORD = "password";
    static final String PARAM_USER_OLD_PASSWORD = "old_password";
    static final String PARAM_USER_CONFIRM_PASSWORD = "confirm_password";
    static final String PARAM_USER_FIRST_NAME = "first_name";
    static final String PARAM_USER_LAST_NAME = "last_name";
    static final String PARAM_USER_MOBILE = "mobile";
    static final String PARAM_USER_PROFILE_PICK = "profile_pick";
    static final String PARAM_USER_STREET = "street";
    static final String PARAM_USER_CITY = "city";
    static final String PARAM_USER_STATE = "state";
    static final String PARAM_USER_COUNTRY = "country";
    static final String PARAM_USER_ZIP_CODE = "zipcode";
    static final String PARAM_USER_DEVICE_ID = "device_id";

    static final String PARAM_FIELD_NAME = "name";
    static final String PARAM_FIELD_VALUE = "value";

    static final String PARAM_START_DATE = "start_date";
}
