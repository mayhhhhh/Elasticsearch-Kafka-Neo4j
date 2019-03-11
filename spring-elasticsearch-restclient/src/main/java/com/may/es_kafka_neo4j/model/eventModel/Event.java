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
public class Event {

    @Id
    @GeneratedValue(strategy = CustomIdStrategy.class)
    private String id;

    @NonNull
    private String event;

    @Relationship(eventConsts.R_HAS_SOURCE)
    @NonNull
    private SourceField sourceField;

    @Relationship(eventConsts.R_HAS_TARGET)
    @NonNull
    private TargetField targetField;

    @Relationship(eventConsts.R_HAS_GENERATOR)
    @NonNull
    private GeneratorField generatorField;

    @Relationship(eventConsts.R_HAS_OBJECT)
    @NonNull
    private Descriptors descriptors;

//    @Relationship(eventConsts.R_HAS_SOURCE)
//    @NonNull
//    private Ip s_ip;
//
//    @Relationship(eventConsts.R_HAS_TARGET)
//    @NonNull
//    private Ip t_ip;
//
//    @Relationship(eventConsts.R_HAS_GENERATOR)
//    @NonNull
//    private Ip g_ip;

}
