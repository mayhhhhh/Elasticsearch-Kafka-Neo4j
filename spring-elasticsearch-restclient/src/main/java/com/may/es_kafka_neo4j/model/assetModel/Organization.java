package com.may.es_kafka_neo4j.model.assetModel;

import com.may.es_kafka_neo4j.config.CustomIdStrategy;
import com.may.es_kafka_neo4j.constants.assetConsts;
import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor
@Builder
@NodeEntity
public class Organization {

    @Id
    @GeneratedValue(strategy = CustomIdStrategy.class)
    private String id;

    @NonNull
    private String organization;

    @Relationship(assetConsts.R_HAS_ASSET)
    @NonNull
    private List<Asset> assets;
}
