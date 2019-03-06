package com.may.es_kafka_neo4j.Repository.eventRepository;

import com.may.es_kafka_neo4j.model.eventModel.SourceField;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface SourceFieldRepository extends Neo4jRepository {
    Optional<SourceField> findBySourceField(String sourceField);

}
