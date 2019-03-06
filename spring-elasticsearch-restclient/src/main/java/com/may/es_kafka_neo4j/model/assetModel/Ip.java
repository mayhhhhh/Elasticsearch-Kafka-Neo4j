package com.may.es_kafka_neo4j.model.assetModel;

import com.may.es_kafka_neo4j.config.CustomIdStrategy;
import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor
@Builder
@NodeEntity
public class Ip {
    @Id
    @GeneratedValue(strategy = CustomIdStrategy.class)
    private String id;

    @NonNull
    private String ip;

}