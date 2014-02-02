package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	   BlackBoxGuiTest.class,
	   TestBbActionPerformed.class,
	   TestControl.class,
	   TestCpu.class,
	   TestWbActionPerformed.class
	})
public class BlackjackTestSuite {

}
