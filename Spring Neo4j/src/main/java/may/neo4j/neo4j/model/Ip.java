package may.neo4j.neo4j.model;

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
    @GeneratedValue
    private Long id;

    @NonNull
    private String ip;


}
