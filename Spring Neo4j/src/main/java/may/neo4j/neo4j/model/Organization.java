package may.neo4j.neo4j.model;

import lombok.*;
import may.neo4j.neo4j.constants.assetConsts;
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
    @GeneratedValue
    private Long id;

    @NonNull
    private String organization;

    @Relationship(assetConsts.R_HAS_A_URL)
    @NonNull
    private List<Asset> assets;
}
