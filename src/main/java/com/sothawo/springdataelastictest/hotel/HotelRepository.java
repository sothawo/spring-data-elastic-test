package com.sothawo.springdataelastictest.hotel;

import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface HotelRepository extends ElasticsearchRepository<Hotel, String>, CustomHotelRepository<Hotel> {
    SearchHits<Hotel> searchAllBy();

    SearchHits<Hotel> searchById(String id);
}
