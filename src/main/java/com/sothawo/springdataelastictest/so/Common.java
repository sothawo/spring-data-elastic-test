/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.springdataelastictest.so;

import org.springframework.data.annotation.Id;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public class Common {

    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

