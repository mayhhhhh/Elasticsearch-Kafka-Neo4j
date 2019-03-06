package com.may.es_kafka_neo4j.Repository.assetRepository;

import com.may.es_kafka_neo4j.model.assetModel.Organization;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface OrganizationRepository extends Neo4jRepository {
    Optional<Organization> findByOrganization(String organization);
}

