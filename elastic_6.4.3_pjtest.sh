#!/usr/bin/env bash
docker run \
    -d \
    --name elastic_pjtest \
    -p 9200:9200 \
    -p 9300:9300 \
    -e "discovery.type=single-node" \
    -e "cluster.name=pjtest" \
    docker.elastic.co/elasticsearch/elasticsearch:6.4.3
