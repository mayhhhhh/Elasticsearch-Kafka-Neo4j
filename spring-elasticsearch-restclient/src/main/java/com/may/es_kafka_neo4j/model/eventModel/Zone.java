package com.may.es_kafka_neo4j.model.eventModel;


import com.may.es_kafka_neo4j.config.CustomIdStrategy;
import com.may.es_kafka_neo4j.constants.eventConsts;
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
public class Zone {
    @Id
    @GeneratedValue(strategy = CustomIdStrategy.class)
    private String id;

    @NonNull
    private String zonename;

    @NonNull
    private String zoneid;

    @Relationship(eventConsts.R_HAS_IP)
    @NonNull
    private List<Ip> ips;
}
