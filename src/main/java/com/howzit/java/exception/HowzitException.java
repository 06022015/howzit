package com.howzit.java.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

/**
 * Created with IntelliJ IDEA.
 * User: Ashif.Qureshi
 * Date: 9/18/14
 * Time: 5:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class HowzitException extends RuntimeException {

    private static final long serialVersionUID = -2706518848253209769L;
    private Logger logger = LoggerFactory.getLogger(HowzitException.class);

    private int code;
    private String message;

    public HowzitException() {
        super();
        logger.error("HowzitException occurred");
    }

    public HowzitException(int code) {
        super();
        this.code = code;
        this.message = HttpStatus.valueOf(code).toString();
        logger.error("HowzitException occurred code:- "+ code);
    }

    public HowzitException(String message) {
        super(message);
        this.code=HttpStatus.INTERNAL_SERVER_ERROR.value();
        logger.error("HowzitException occured "+message);
    }

    public HowzitException(int code, String message) {
        super(message);
        this.code = code;
        logger.error("HowzitException occurred code "+code+" | Message "+message);
    }

    public HowzitException(int code,String message, Throwable cause) {
        super(message, cause);
        this.code= code;
        logger.error("HowzitException occurred "+message);
        cause.printStackTrace();
    }

    public int getCode() {
        return code;
    }
}
