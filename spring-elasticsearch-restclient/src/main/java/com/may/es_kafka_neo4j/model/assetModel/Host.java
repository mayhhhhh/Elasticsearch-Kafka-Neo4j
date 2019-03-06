package com.may.es_kafka_neo4j.model.assetModel;

import com.may.es_kafka_neo4j.config.CustomIdStrategy;
import com.may.es_kafka_neo4j.constants.assetConsts;
import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor
@Builder
@NodeEntity
public class Host {

    @Id
    @GeneratedValue(strategy = CustomIdStrategy.class)
    private String id;

    @NonNull
    private String host;

    @Relationship(assetConsts.R_HAS_AN_IP)
    @NonNull
    private Ip ip;
}