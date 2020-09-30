package com.sothawo.springdataelastictest.hotel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
@SuppressWarnings("unused")
public class CustomHotelRepositoryImpl implements CustomHotelRepository<Hotel> {

    private static final Logger LOG = LoggerFactory.getLogger(CustomHotelRepositoryImpl.class);

    private final ElasticsearchOperations operations;

    private final ConcurrentHashMap<String, IndexCoordinates> knownIndexCoordinates = new ConcurrentHashMap<>();
    @Nullable
    private Document mapping;

    @SuppressWarnings("unused")
    public CustomHotelRepositoryImpl(ElasticsearchOperations operations) {
        this.operations = operations;
    }

    @Override
    public <S extends Hotel> S save(S hotel) {

        IndexCoordinates indexCoordinates = getIndexCoordinates(hotel);
        LOG.info("saving {} to {}", hotel, indexCoordinates);

        S saved = operations.save(hotel, indexCoordinates);

        operations.indexOps(indexCoordinates).refresh();

        return saved;
    }

    @NonNull
    private <S extends Hotel> IndexCoordinates getIndexCoordinates(S hotel) {

        String indexName = "hotel-" + hotel.getCountryCode();
        return knownIndexCoordinates.computeIfAbsent(indexName, i -> {

                IndexCoordinates indexCoordinates = IndexCoordinates.of(i);
                IndexOperations indexOps = operations.indexOps(indexCoordinates);

                if (!indexOps.exists()) {
                    indexOps.create();

                    if (mapping == null) {
                        mapping = indexOps.createMapping(Hotel.class);
                    }

                    indexOps.putMapping(mapping);
                }
                return indexCoordinates;
            }
        );
    }
}
