package testsuite;

import org.jsmart.zerocode.core.domain.TargetEnv;
import org.jsmart.zerocode.core.domain.TestPackageRoot;
import org.junit.runner.RunWith;

import src.test.java.config.StringBootRunner;

@TargetEnv("host_config.properties")
@RunWith(StringBootRunner.class)
@TestPackageRoot("integration_tests")
public class TestSuite{
    
}
