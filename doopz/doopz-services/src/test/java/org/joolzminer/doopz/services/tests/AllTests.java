package org.joolzminer.doopz.services.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * The Class AllTests groups all the JUnit tests for doopz-services.
 */
@RunWith(Suite.class)
@SuiteClasses({
		FileRetrieverTest.class,
		FileMapBuilderTest.class,
		DuplicatesSeekerTest.class })
public class AllTests {

}
