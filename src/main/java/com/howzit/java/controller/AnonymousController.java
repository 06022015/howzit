package com.howzit.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

/**
 * Created with IntelliJ IDEA.
 * UserEntity: Ashif.Qureshi
 * Date: 25/8/14
 * Time: 1:01 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class AnonymousController {

    @GET
    @RequestMapping("/login")
    public String login(HttpServletRequest request){
        return "";
    }

    @POST
    @RequestMapping("/register")
    public String register(HttpServletRequest request){
        return "";
    }

    @GET
    @RequestMapping("/username/validate")
    public String validateUserName(HttpServletRequest request){
        return "";
    }

}
