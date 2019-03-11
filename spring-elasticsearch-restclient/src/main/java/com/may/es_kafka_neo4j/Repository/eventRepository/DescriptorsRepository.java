package com.may.es_kafka_neo4j.Repository.eventRepository;

import com.may.es_kafka_neo4j.model.eventModel.Descriptors;
import com.may.es_kafka_neo4j.model.eventModel.GeneratorField;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface DescriptorsRepository extends Neo4jRepository {
    Optional<Descriptors> findByObjectname(String object_name);
}
