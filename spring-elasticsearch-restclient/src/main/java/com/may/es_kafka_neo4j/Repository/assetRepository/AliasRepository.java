package com.may.es_kafka_neo4j.Repository.assetRepository;

import com.may.es_kafka_neo4j.model.assetModel.Alias;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AliasRepository extends Neo4jRepository {

    Optional<Alias> findByAlias(String alias);
}
