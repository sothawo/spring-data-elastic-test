package com.sothawo.springdataelastictest.idfield;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface IdRepository extends ElasticsearchRepository<IdEntity, String> {
}
