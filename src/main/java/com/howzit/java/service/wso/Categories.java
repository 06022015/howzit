package com.howzit.java.service.wso;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ashif
 * Date: 10/19/14
 * Time: 8:25 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "categories")
public class Categories {

    private List<Category> categories;

    @XmlElement
    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void addCategory(Category category){
        if(null==getCategories())
            setCategories(new ArrayList<Category>());
        getCategories().add(category);
    }
}
