package may.neo4j.neo4j.model;

import lombok.*;
import may.neo4j.neo4j.constants.assetConsts;
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
    @GeneratedValue
    private Long id;

    @NonNull
    private String host;

    @Relationship(assetConsts.R_HAS_AN_IP)
    @NonNull
    private Ip ip;
}
