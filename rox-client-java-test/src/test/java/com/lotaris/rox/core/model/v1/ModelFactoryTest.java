package com.lotaris.rox.core.model.v1;

import com.lotaris.rox.annotations.RoxableTest;
import com.lotaris.rox.common.model.v1.Payload;
import com.lotaris.rox.common.model.v1.ModelFactory;
import com.lotaris.rox.common.model.v1.TestRun;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for claas {@link RoxModelFactory}
 * 
 * @author Laurent Prevost, laurent.prevost@lotaris.com
 */
public class ModelFactoryTest {
	private com.lotaris.rox.common.model.v1.Test validTest;
	private TestRun validTestRun;

	@Before
	public void setUp() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("key", "value");
	
		validTest = ModelFactory.createTest("key", "name", "category", 
			System.currentTimeMillis(), 10L, "message", true, 0, 
			new HashSet<String>(Arrays.asList(new String[] { "tag" })),
			new HashSet<String>(Arrays.asList(new String[] { "ticket" })),
			data
		);

		validTestRun = ModelFactory.createTestRun(
			"project", "version", System.currentTimeMillis(), 10L, 
			"group", "uid", Arrays.asList(new com.lotaris.rox.common.model.v1.Test[] { validTest })
		);
	}

	@Test
	@RoxableTest(key = "d9455e79e656")
	public void testCreationWithAllAttributesShouldBePossible() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("key", "value");

		com.lotaris.rox.common.model.v1.Test test = 
			ModelFactory.createTest("key", "name", "category", 
				System.currentTimeMillis(), 10L, "message", true, 0, 
				new HashSet<String>(Arrays.asList(new String[] { "tag" })),
				new HashSet<String>(Arrays.asList(new String[] { "ticket" })),
				data
			);

		assertNotNull("The test was not created", test);
	}
	
	@Test
	@RoxableTest(key = "a43fde3e95e1")
	public void testCreationWithOnlyMandatoryAttributesShouldBePossible() {
		com.lotaris.rox.common.model.v1.Test test = ModelFactory.createTest(
			"key", null, null, null, 0L, null, true, -1, null, null, null);

		assertNotNull("The test was not created", test);
	}
	
	@Test
	@RoxableTest(key = "97facacbfbb4")
	public void testCreationWithMissingMandatoryAttributes() {
		try {
			ModelFactory.createTest(null, null, null, null, 0L, null, true, -1, null, null, null);
			fail("The test should not be created without a key");
		}
		catch (IllegalArgumentException iae) {}
	}
	
	@Test
	@RoxableTest(key = "36411695b58e")
	public void testFailedWithEmptyMessageShouldBeFilledWithDefaultMessage() {
		com.lotaris.rox.common.model.v1.Test test = ModelFactory.createTest("key", null, null, null, 0L, "", false, -1, null, null, null);
		assertEquals("No content for the message provided for the failing test", test.getMessage());
	}
	
	@Test
	@RoxableTest(key = "9089ef1435e0")
	public void testFailedWithNullMessageShouldBeFilledWithDefaultMessage() {
		com.lotaris.rox.common.model.v1.Test test = ModelFactory.createTest("key", null, null, null, 0L, null, false, -1, null, null, null);
		assertEquals("No message provided for the failing test", test.getMessage());
	}
	@Test
	@RoxableTest(key = "fe39112be4c4")
	public void testCreationWithThePlaceholderForStatusShouldBeSilentlyIgnored() {
		com.lotaris.rox.common.model.v1.Test test = ModelFactory.createTest("key", null, 
			null, null, 0L, null, true, -1, null, null, null);
			
		assertNotNull("A placeholder status should be silently ignored and the test should be created", test);
	}

	@Test
	@RoxableTest(key = "f37f4083b43e")
	public void testCreationWithNegativeDurationIsNotPossible() {
		try {
			ModelFactory.createTest("key", null, null, null, -10L, null, false, -1, null, null, null);
			
			fail("The test should not be created with a negative duration");
		}
		catch (IllegalArgumentException iae) {}
	}
	
	@Test
	@RoxableTest(key = "486806b6cda0")
	public void testRunCreationWithAllAttributesShouldBePossible() {
		TestRun testRun = 
			ModelFactory.createTestRun(
				"project", "version", System.currentTimeMillis(), 10L, "group", "uid",
				Arrays.asList(new com.lotaris.rox.common.model.v1.Test[] { validTest })
			);

		assertNotNull("The test run was not created", testRun);
	}
	
	@Test
	@RoxableTest(key = "3d52ae2947a0")
	public void testRunCreationWithOnlyMandatoryAttributesShouldBePossible() {
		TestRun testRun = 
			ModelFactory.createTestRun(
				null, null, null, 10L, null, null,
				Arrays.asList(new com.lotaris.rox.common.model.v1.Test[] { validTest })
			);

		assertNotNull("The test run was not created", testRun);
	}
	
	@Test
	@RoxableTest(key = "caefd31617d3")
	public void testRunCreationWithoutAtLeastOneTest() {
		try {
			ModelFactory.createTestRun(
				null, null, null, 10L, null, null,
				Arrays.asList(new com.lotaris.rox.common.model.v1.Test[] { })
			);

			fail("The test run should not be created without at least one test");
		}
		catch (IllegalArgumentException iae) {}
	}

	@Test
	@RoxableTest(key = "578d8efacd89")
	public void testRunCreationWithNegativeDurationIsNotPossible() {
		try {
			ModelFactory.createTestRun(
				null, null, null, -10L, null, null,
				Arrays.asList(new com.lotaris.rox.common.model.v1.Test[] { })
			);

			fail("The test run should not be created with negative duration");
		}
		catch (IllegalArgumentException iae) {}
	}
	
	@Test
	@RoxableTest(key = "5dab9c6b2fd8")
	public void payloadCreationWithOneTestRunShouldBePossible() {
		Payload payload = ModelFactory.createPayload(validTestRun);
		assertNotNull("The payload was not created", payload);
	}
	
	@Test
	@RoxableTest(key = "7d8d96982129")
	public void payloadCreationWithoutTestRunShouldNotBePossible() {
		try {
			ModelFactory.createPayload(null);
			fail("One test run is required to create a payload");
		}
		catch (IllegalArgumentException iae) {}
	}	
}
