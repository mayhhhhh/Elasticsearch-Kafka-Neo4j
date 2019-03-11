package com.may.es_kafka_neo4j.Repository.eventRepository;

import com.may.es_kafka_neo4j.model.eventModel.SourceSystem;
import com.may.es_kafka_neo4j.model.eventModel.Zone;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface ZoneRepository extends Neo4jRepository {
    Optional<Zone> findByZonename(String zone);

}
