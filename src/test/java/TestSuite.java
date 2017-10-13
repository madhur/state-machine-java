import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.util.ArrayList;

/**
 * Created by madhur on 14 Oct.
 */
@RunWith(Suite.class)

@Suite.SuiteClasses({
        FunctionalTests.class,
        InputValidationTests.class
})

public class TestSuite {

}
