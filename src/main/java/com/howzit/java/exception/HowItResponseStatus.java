package com.howzit.java.exception;

import org.springframework.http.HttpStatus;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Ashif.Qureshi
 * Date: 9/19/14
 * Time: 12:38 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "status")
public class HowItResponseStatus {

    private static final long serialVersionUID = 1L;

    private int code;
    private String message;
    private List<String> errors;
    private Locale locale = Locale.ENGLISH;

    public HowItResponseStatus(){
        this.code = HttpStatus.OK.value();
    }

    public HowItResponseStatus(int code){
        this.code = code;
        this.message = HttpStatus.valueOf(code).toString();
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @XmlElementWrapper(name = "errors")
    @XmlElement(name = "error")
    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void addError(String error) {
        if (null ==getErrors()) {
            setErrors(new ArrayList<String>());
        }
        getErrors().add(error);
    }

    public void addErrors(List<String> errors) {
        if (null ==getErrors()) {
            setErrors(new ArrayList<String>());
        }
        getErrors().addAll(errors);
    }

    @XmlTransient
    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public boolean hasError(){
        return null!=getErrors() && getErrors().size()>0;
    }

}
