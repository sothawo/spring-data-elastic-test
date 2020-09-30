package com.sothawo.springdataelastictest.hotel;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface CustomHotelRepository<T> {
    <S extends T> S save(S entity);
}
