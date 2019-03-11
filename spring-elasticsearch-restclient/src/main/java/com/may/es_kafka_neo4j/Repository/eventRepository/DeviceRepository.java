package com.may.es_kafka_neo4j.Repository.eventRepository;

import com.may.es_kafka_neo4j.model.eventModel.Device;
import com.may.es_kafka_neo4j.model.eventModel.GeneratorField;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface DeviceRepository extends Neo4jRepository {
    Optional<Device> findByDevicename(String device_id);

}
