package com.may.es_kafka_neo4j.model.eventModel;

import com.may.es_kafka_neo4j.config.CustomIdStrategy;
import com.may.es_kafka_neo4j.constants.assetConsts;
import com.may.es_kafka_neo4j.constants.eventConsts;
import com.may.es_kafka_neo4j.model.assetModel.assetIp;
import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import sun.security.krb5.internal.crypto.Des;

import java.util.List;

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

    @Relationship(eventConsts.R_HAS_PORT)
    @NonNull
    private List<Port> ports;

    @Relationship(eventConsts.R_HAS_OBJECT)
    private List<Descriptors> objects;
}