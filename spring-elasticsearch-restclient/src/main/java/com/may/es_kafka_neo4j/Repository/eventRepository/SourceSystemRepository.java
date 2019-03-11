package com.may.es_kafka_neo4j.Repository.eventRepository;

import com.may.es_kafka_neo4j.model.eventModel.GeneratorField;
import com.may.es_kafka_neo4j.model.eventModel.SourceSystem;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface SourceSystemRepository extends Neo4jRepository {
    Optional<SourceSystem> findBySourcesystem(String sourceSystem);

}
