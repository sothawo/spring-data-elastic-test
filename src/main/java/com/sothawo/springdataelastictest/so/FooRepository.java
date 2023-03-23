/*
 * (c) Copyright 2020 sothawo
 */
package com.sothawo.springdataelastictest.so;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.time.LocalDateTime;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public interface FooRepository extends ElasticsearchRepository<Foo, String> {

	SearchHits<Foo> searchBy();

	@Query("{" +
				 "        \"bool\" : {" +
				 "          \"must\" : [" +
				 "              { \"range\" : { \"start-time\" : { \"gte\": \"?0\" } } }," +
				 "              { \"range\" : { \"end-time\" :   { \"lte\": \"?1\"  } } } " +
				 "          ]" +
				 "        }" +
				 "}")
	SearchHits<Foo> search(LocalDateTime from, LocalDateTime to);

	SearchHits<Foo> getUserQuery(Integer fieldId);

	@Query("""
		{
			"match": {
			"dotted.text": {
				"query": "?0"
				}
			}
		}
		""")
	SearchHits<Foo> searchByDottedTextQuery(String text);
	SearchHits<Foo> searchByDottedText(String text);
}
