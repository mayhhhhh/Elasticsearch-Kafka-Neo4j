package com.may.es_kafka_neo4j.Repository.eventRepository;

import com.may.es_kafka_neo4j.model.eventModel.Host;
import com.may.es_kafka_neo4j.model.eventModel.Ip;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface HostRepository extends Neo4jRepository {
    Optional<Host> findByHost(String host);

}

