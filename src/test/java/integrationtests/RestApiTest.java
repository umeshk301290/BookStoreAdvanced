package integrationtests;

import org.jsmart.zerocode.core.domain.JsonTestCase;
import org.jsmart.zerocode.core.domain.TargetEnv;
import org.junit.Test;
import org.junit.runner.RunWith;

import src.test.java.config.SpringBootUnitRunner;

@TargetEnv("host_config.properties")
@RunWith(SpringBootUnitRunner.class)
public class RestApiTest{
    @Test
    @JsonTestCase("zerocode.json")
    public void testGetHappyAndSad() throws Exception {
    }
}