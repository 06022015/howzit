package com.howzit.java.util;

import com.howzit.java.exception.HowItResponseStatus;
import com.howzit.java.model.type.ActivityAreaType;
import com.howzit.java.model.type.ActivityType;
import com.howzit.java.service.wso.Activity;
import com.howzit.java.service.wso.ActivityArea;
import com.howzit.java.service.wso.Category;
import com.howzit.java.service.wso.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ashif
 * Date: 9/19/14
 * Time: 10:12 AM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ValidatorUtil implements Constants {

    private Logger logger = LoggerFactory.getLogger(ValidatorUtil.class);

    private final static String PATTERN_PASSWORD = "^(?=.*\\d)(?=.*[A-Za-z])[A-Za-z0-9]{6,18}$";
    private final static String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private final static String PATTERN_MOBILE_NUMBER = "^(?:0091|\\\\+91|0)[7-9][0-9]{9}$";
    private final static String PATTERN_RATING = "^([0-5]{1})*(\\.[0-9]{1})?[0]{0,1}$";
    private final static int MAX_RATING = 5;
    private final static int SMALL_TEXT_FIELD_LENGTH = 30;
    private final static int NORMAL_TEXT_FIELD_LENGTH = 60;
    private final static int MEDIUM_TEXT_FIELD_LENGTH = 120;
    private final static int LARGE_TEXT_FIELD_LENGTH = 240;


    @Autowired
    private CommonUtil commonUtil;

    public void validate(Activity activity, HowItResponseStatus status) {
        activity.trim();
        if (null == activity.getType()) {
            status.addError(commonUtil.getText("error.activity.type.required", status.getLocale()));
        } else if (!ActivityType.isValidActivityType(activity.getType())) {
            status.addError(commonUtil.getText("error.activity.type.invalid", status.getLocale()));
        } else if (ActivityType.isContentType(activity.getType()) && isNullOrEmpty(activity.getContent())) {
            status.addError(commonUtil.getText("error.activity.comment.required", status.getLocale()));
        } else if (ActivityType.isOnlyLevel0Type(activity.getType())) {
            if(null != activity.getParentActivityId()){
               status.addError(commonUtil.getText("error.activity.invalid", status.getLocale()));
            }else if(activity.getType()==ActivityType.PHOTO.ordinal() && null == activity.getImage()){
                status.addError(commonUtil.getText("error.activity.image.must.be.provided", status.getLocale()));
            }
        } else if (activity.getType() == ActivityType.RATING.ordinal() && !isValidRating(activity.getContent())) {
            status.addError(commonUtil.getText("error.activity.rating.invalid", status.getLocale()));
        } else if (activity.getType() == ActivityType.SHARE.ordinal() && null == activity.getParentActivityId()) {
            status.addError(commonUtil.getText("error.activity.share.activity.not.available", status.getLocale()));
        }
        if (null == activity.getActivityAreaId()) {
            status.addError(commonUtil.getText("error.activity.area.required", status.getLocale()));
        }
    }

    public void validate(ActivityArea activityArea, HowItResponseStatus status) {
        activityArea.trim();
        if (null == activityArea.getCategoryId())
            status.addError(commonUtil.getText("error.activity.area.category.required", status.getLocale()));
        if (isNullOrEmpty(activityArea.getName()))
            status.addError(commonUtil.getText("error.activity.area.name.required", status.getLocale()));
        else if (!isValidLength(activityArea.getName(), SMALL_TEXT_FIELD_LENGTH))
            status.addError(commonUtil.getText("error.text.length.must.be.less.than",
                    new Object[]{PARAM_ACTIVITY_AREA_NAME, SMALL_TEXT_FIELD_LENGTH}, status.getLocale()));
        if (null == activityArea.getType())
            status.addError(commonUtil.getText("error.activity.area.type.required", status.getLocale()));
        else if (!ActivityAreaType.isValidAreaType(activityArea.getType()))
            status.addError(commonUtil.getText("error.activity.area.type.invalid", status.getLocale()));
        else if (activityArea.getType() == ActivityAreaType.LOCATION.ordinal()) {
            if (isNullOrEmpty(activityArea.getAddress()))
                status.addError(commonUtil.getText("error.activity.area.address.required", status.getLocale()));
            if (null == activityArea.getLatitude())
                status.addError(commonUtil.getText("error.activity.area.latitude.required", status.getLocale()));
            if (null == activityArea.getLongitude())
                status.addError(commonUtil.getText("error.activity.area.longitude.required", status.getLocale()));
        } else if (activityArea.getType() == ActivityAreaType.PRODUCT.ordinal()) {
            if (isNullOrEmpty(activityArea.getDescription()))
                status.addError(commonUtil.getText("error.activity.area.description.required", status.getLocale()));
        }
    }

    public void validate(Category category, HowItResponseStatus status) {
        category.trim();
        if (isNullOrEmpty(category.getName()))
            status.addError(commonUtil.getText("error.category.name.required", status.getLocale()));
        else if (!isValidLength(category.getName(), NORMAL_TEXT_FIELD_LENGTH))
            status.addError(commonUtil.getText("error.text.length.must.be.less.than",
                    new Object[]{PARAM_CATEGORY_NAME, NORMAL_TEXT_FIELD_LENGTH}, status.getLocale()));

        if (isNullOrEmpty(category.getDescription()))
            status.addError(commonUtil.getText("error.category.description.required", status.getLocale()));
        else if (!isValidLength(category.getDescription(), MEDIUM_TEXT_FIELD_LENGTH))
            status.addError(commonUtil.getText("error.text.length.must.be.less.than",
                    new Object[]{PARAM_CATEGORY_DESCRIPTION, MEDIUM_TEXT_FIELD_LENGTH}, status.getLocale()));
    }

    public void validate(User user, HowItResponseStatus status) {
        user.trim();
        if (isNullOrEmpty(user.getEmail()))
            status.addError(commonUtil.getText("error.email.required", status.getLocale()));
        else if (!isValidEmail(user.getEmail()))
            status.addError(commonUtil.getText("error.email.invalid", status.getLocale()));

        if (isNullOrEmpty(user.getPassword()))
            status.addError(commonUtil.getText("error.password.required", status.getLocale()));
        else if (!user.getPassword().equals(user.getConfirmPassword()))
            status.addError(commonUtil.getText("error.password.and.confirm.password.mismatch", status.getLocale()));
        else if (!isValidPassword(user.getPassword()))
            status.addError(commonUtil.getText("error.password.invalid", status.getLocale()));

        if (isNullOrEmpty(user.getFirstName()))
            status.addError(commonUtil.getText("error.first.name.required", status.getLocale()));
        else if (!isValidLength(user.getFirstName(), NORMAL_TEXT_FIELD_LENGTH))
            status.addError(commonUtil.getText("error.text.length.must.be.less.than",
                    new Object[]{PARAM_USER_FIRST_NAME, NORMAL_TEXT_FIELD_LENGTH}, status.getLocale()));

        if (isNullOrEmpty(user.getLastName()))
            status.addError(commonUtil.getText("error.last.name.required", status.getLocale()));
        else if (!isValidLength(user.getLastName(), NORMAL_TEXT_FIELD_LENGTH))
            status.addError(commonUtil.getText("error.text.length.must.be.less.than",
                    new Object[]{PARAM_USER_LAST_NAME, NORMAL_TEXT_FIELD_LENGTH}, status.getLocale()));

        if (isNullOrEmpty(user.getMobileNumber()))
            status.addError(commonUtil.getText("error.mobile.number.required", status.getLocale()));
        else if (!isValidMobileNumber(user.getMobileNumber()))
            status.addError(commonUtil.getText("error.mobile.number.invalid", status.getLocale()));
    }


    public void validateUserUpdateAbleFiled(String name, String value, HowItResponseStatus status) {
        name = null!= name?name.trim():name;
        if(!userUpdateAbleField().contains(name)){
            status.addError(commonUtil.getText("error.unknown.field.name", status.getLocale()));
            return;
        }
        value = null != value ? value.trim() : value;
        if (name.equals(PARAM_USER_FIRST_NAME)) {
            if (isNullOrEmpty(value))
                status.addError(commonUtil.getText("error.first.name.required", status.getLocale()));
        }else if(name.equals(PARAM_USER_LAST_NAME)){
            if (isNullOrEmpty(value))
                status.addError(commonUtil.getText("error.last.name.required", status.getLocale()));
            else if (!isValidLength(value, NORMAL_TEXT_FIELD_LENGTH))
                status.addError(commonUtil.getText("error.text.length.must.be.less.than",
                    new Object[]{PARAM_USER_LAST_NAME, NORMAL_TEXT_FIELD_LENGTH}, status.getLocale()));
        }
    }

    public void validatePassword(String password, String confirmPassword, HowItResponseStatus status){
        if(isNullOrEmpty(password))
            status.addError(commonUtil.getText("error.password.required", status.getLocale()));
        else if(!password.equals(confirmPassword))
            status.addError(commonUtil.getText("error.password.and.confirm.password.mismatch"));
        else if(!isValidPassword(password))
            status.addError(commonUtil.getText("error.password.invalid"));
    }

    public static List<String> userUpdateAbleField(){
        List<String> filedNameList = new ArrayList<String>();
        filedNameList.add(PARAM_USER_FIRST_NAME);
        filedNameList.add(PARAM_USER_LAST_NAME);
        filedNameList.add(PARAM_USER_PROFILE_PICK);
        return filedNameList;
    }

    public static boolean isValidLength(String text, int length) {
        return text.length() <= length;
    }

    public static boolean isValidEmail(String email) {
        return email.matches(PATTERN_EMAIL);
    }


    public static boolean isValidPassword(String password) {
        return password.matches(PATTERN_PASSWORD);
    }

    public static boolean isValidMobileNumber(String mobileNumber) {
        return mobileNumber.matches(PATTERN_MOBILE_NUMBER);
    }

    public static boolean isValidRating(String rating) {
        boolean isValid = false;
        if (rating.matches(PATTERN_RATING)) {
            Float rat = Float.parseFloat(rating);
            isValid = rat >= 0 && rat <= MAX_RATING;
        }
        return isValid;
    }

    public static boolean isNullOrEmpty(String text) {
        return null == text || "".equals(text);
    }
}
