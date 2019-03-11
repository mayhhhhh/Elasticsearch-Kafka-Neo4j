package com.may.es_kafka_neo4j.model.eventModel;

import com.may.es_kafka_neo4j.config.CustomIdStrategy;
import com.may.es_kafka_neo4j.constants.eventConsts;
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
public class TargetField {

    @Id
    @GeneratedValue(strategy = CustomIdStrategy.class)
    private String id;

    @NonNull
    private String targetField;

    @Relationship(eventConsts.R_HAS_IP)
    @NonNull
    private Ip t_ip;

    @Relationship(eventConsts.R_HAS_PORT)
    @NonNull
    private Port t_port;

    @Relationship(eventConsts.R_HAS_ZONE)
    @NonNull
    private Zone t_zone;
}
