package may.neo4j.neo4j;

import may.neo4j.neo4j.service.NeoService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class Neo4jTest extends Neo4jApplicationTests {
    @Autowired
    private NeoService neoService;

    @Test
    public void testSave(){
        neoService.initData();
    }
}
