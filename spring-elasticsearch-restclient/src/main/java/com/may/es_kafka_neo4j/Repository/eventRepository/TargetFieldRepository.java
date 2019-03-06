package com.may.es_kafka_neo4j.Repository.eventRepository;

import com.may.es_kafka_neo4j.model.eventModel.TargetField;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface TargetFieldRepository extends Neo4jRepository {
    Optional<TargetField> findByTargetField(String targetField);

}
