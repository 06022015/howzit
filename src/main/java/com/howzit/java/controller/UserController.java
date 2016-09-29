package com.howzit.java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

/**
 * Created with IntelliJ IDEA.
 * UserEntity: Ashif.Qureshi
 * Date: 20/8/14
 * Time: 12:23 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {


    @GET
    @RequestMapping("show")
    public String getUser(HttpServletRequest request){
        return "";
    }

    @GET
    @RequestMapping("form")
    public String userForm(HttpServletRequest request){
        return "";
    }

    @POST
    @RequestMapping("/save")
    public String saveUser(HttpServletRequest request){
        return "";
    }

    @GET
    @RequestMapping("/delete")
    public String deleteUser(HttpServletRequest request){
        return "";
    }
}
