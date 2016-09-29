package com.howzit.java.service;

import com.howzit.java.exception.*;
import com.howzit.java.util.CommonUtil;
import com.sun.jersey.api.ParamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created with IntelliJ IDEA.
 * UserEntity: Ashif.Qureshi
 * Date: 26/4/13
 * Time: 7:29 PM
 * To change this template use File | Settings | File Templates.
 */
@Provider
public class RestExceptionMapper implements ExceptionMapper<RuntimeException> {
    private Logger logger = LoggerFactory.getLogger(RestExceptionMapper.class);

    private CommonUtil commonUtil = (CommonUtil)CommonUtil.getBean("commonUtil");

    public Response toResponse(RuntimeException re) {
        HowItResponseStatus status = new HowItResponseStatus();
        if (re instanceof NoRecordFoundException) {
            status.setCode(HttpStatus.NO_CONTENT.value());
            status.setMessage(re.getMessage());
        } else if(re instanceof com.sun.jersey.api.NotFoundException){
            status.setCode(HttpStatus.NOT_FOUND.value());
            status.setMessage(commonUtil.getText("404.message", status.getLocale()));
        }else if(re instanceof ParamException){
            status.setCode(HttpStatus.BAD_REQUEST.value());
            status.setMessage(commonUtil.getText("error.request.param.type.mismatch", status.getLocale()));
        } else if(re instanceof WebApplicationException){
            status.setCode(HttpStatus.METHOD_NOT_ALLOWED.value());
            status.setMessage(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase());
        }else if(re instanceof HowzitException){
            HowzitException howzitException = (HowzitException)re;
            status.setCode(howzitException.getCode());
            status.setMessage(re.getMessage());
        }else if (re instanceof AccessDeniedException) {
            status.setCode(HttpStatus.FORBIDDEN.value());
            status.setMessage(commonUtil.getText("403.message", status.getLocale()));
        }else if (re instanceof CredentialNotFoundException) {
            status.setCode(HttpStatus.NON_AUTHORITATIVE_INFORMATION.value());
            status.setMessage(commonUtil.getText("error.token.must.be.provide", status.getLocale()));
        } else if (re instanceof UsernameNotFoundException) {
            status.setCode(HttpStatus.NO_CONTENT.value());
            status.setMessage(commonUtil.getText("error.email.not.exist", status.getLocale()));
        }else if(re instanceof AuthenticationException){
            status.setCode(HttpStatus.UNAUTHORIZED.value());
            status.setMessage(commonUtil.getText("error.credential.invalid", status.getLocale()));
        }else {
            status.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            status.setMessage(commonUtil.getText("500.message"));
            logger.error("Unhandled exception:- "+re.getMessage());
            re.printStackTrace();
        }
        return Response.status(status.getCode()).entity(status).build();
    }
}
