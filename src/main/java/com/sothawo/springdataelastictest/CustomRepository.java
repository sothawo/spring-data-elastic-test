/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface CustomRepository<T> {
    T saveNoRefresh(T entity);
}
