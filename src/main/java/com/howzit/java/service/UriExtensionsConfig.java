package com.howzit.java.service;

import com.sun.jersey.api.core.PackagesResourceConfig;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: ashif
 * Date: 9/17/14
 * Time: 1:43 AM
 * To change this template use File | Settings | File Templates.
 */
//http://zcox.wordpress.com/2009/08/11/uri-extensions-in-jersey/
public class UriExtensionsConfig extends PackagesResourceConfig {

    private Map<String, MediaType> mediaTypeMap;

    public UriExtensionsConfig() {
        super();
    }

    public UriExtensionsConfig(Map<String, Object> props) {
        super(props);
    }

    public UriExtensionsConfig(String[] paths) {
        super(paths);
    }

    @Override
    public Map<String, MediaType> getMediaTypeMappings() {
        Map<String, MediaType> map = new HashMap<String, MediaType>();
        map.put("xml", MediaType.APPLICATION_XML_TYPE);
        map.put("json", MediaType.APPLICATION_JSON_TYPE);
        return map;
    }
}
