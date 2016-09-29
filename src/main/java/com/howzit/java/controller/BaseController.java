package com.howzit.java.controller;

import com.howzit.java.model.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created with IntelliJ IDEA.
 * UserEntity: Ashif.Qureshi
 * Date: 25/8/14
 * Time: 1:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseController {

    public UserEntity getCurrentUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserEntity) authentication.getPrincipal();
    }

    public String sendRedirect(String url) {
        return "redirect:" + url;
    }
}
