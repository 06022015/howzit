package com.howzit.java.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: ashif
 * Date: 9/21/14
 * Time: 9:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class URLRewriteFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
         HttpServletRequest request = (HttpServletRequest) servletRequest;
        request.getRequestURI();
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
