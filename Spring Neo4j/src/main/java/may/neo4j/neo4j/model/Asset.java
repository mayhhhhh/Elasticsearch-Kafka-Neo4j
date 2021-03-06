package may.neo4j.neo4j.model;

import lombok.*;
import may.neo4j.neo4j.constants.assetConsts;
import org.neo4j.ogm.annotation.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor
@Builder
@NodeEntity
public class Asset {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String asset;

    @NonNull
    @Relationship(assetConsts.R_HAS_AN_IP)
    private Ip ip;


}
