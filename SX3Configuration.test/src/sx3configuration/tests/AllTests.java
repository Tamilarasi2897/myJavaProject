package sx3configuration.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ Scenario1_NewConfiguration.class, Scenario2_LoadConfiguration.class, Scenario3_Validations.class })
public class AllTests {

}
