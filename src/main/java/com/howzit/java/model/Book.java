package com.howzit.java.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: ashif
 * Date: 9/29/14
 * Time: 10:30 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Book implements Serializable {

    private static final long serialVersionUID = 3256446889040622647L;
    private Long id;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(unique = true, nullable = false, updatable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
