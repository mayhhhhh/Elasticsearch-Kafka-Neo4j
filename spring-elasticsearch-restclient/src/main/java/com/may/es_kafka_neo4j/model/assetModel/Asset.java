package com.may.es_kafka_neo4j.model.assetModel;

import com.may.es_kafka_neo4j.config.CustomIdStrategy;
import com.may.es_kafka_neo4j.constants.assetConsts;
import lombok.*;
import org.neo4j.ogm.annotation.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor
@Builder
@NodeEntity
public class Asset {

    @Id
    @GeneratedValue(strategy = CustomIdStrategy.class)
    private String id;

    @NonNull
    private String asset;

    @NonNull
    @Relationship(assetConsts.R_HAS_AN_IP)
    private Ip ip;


}