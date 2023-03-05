package com.sothawo.springdataelastictest.enums;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface GuitarRepository extends ElasticsearchRepository<Guitar, String> {
	List<Guitar> findAllByOrderByYearAsc();
}
