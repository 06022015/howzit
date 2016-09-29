package com.howzit.java.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: ashif
 * Date: 10/19/14
 * Time: 11:17 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "company")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CompanyEntity extends BaseEntity{

    private static final long serialVersionUID = 3256446889040622647L;

    private Long id;
    private String name;
    private String tagLine;
    private CategoryEntity category;

}
