package com.howzit.java.bl.impl;

import com.howzit.java.exception.HowItResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * Created with IntelliJ IDEA.
 * User: Ashif.Qureshi
 * Date: 9/19/14
 * Time: 3:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseBL {


    protected void saveErrorMessage(HowItResponseStatus status, int code) {
        status.setCode(code);
    }

    protected void saveSuccessMessage(HowItResponseStatus status, String message) {
        status.setCode(HttpStatus.OK.value());
        status.setMessage(message);
    }
}
