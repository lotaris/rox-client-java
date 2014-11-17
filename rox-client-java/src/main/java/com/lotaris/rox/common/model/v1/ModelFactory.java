package com.lotaris.rox.common.model.v1;

import com.lotaris.rox.common.utils.Constants;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Model factory to facilitate the creation of tests, test runs
 * and payloads.
 * 
 * @author Laurent Prevost, laurent.prevost@lotaris.com
 */
public class ModelFactory {
	/**
	 * Create a payload from a test run
	 * 
	 * @param testRun The test run to add in the payload
	 * @return The payload created
	 */
	public static Payload createPayload(TestRun testRun) {
		if (testRun == null) {
			throw new IllegalArgumentException("The test run must be present.");
		}
		
		Payload payload = new Payload();
		payload.setTestRun(testRun);
		
		return payload;
	}
	
	/**
	 * Create a test run
	 * 
	 * @param projectApiId The project API identifier
	 * @param projectVersion The project version
	 * @param endTimeStamp A end time stamp
	 * @param duration The duration of the run (only 0 or positive allowed)
	 * @param group The group
	 * @param uid The unique id of test run (to group multiple test runs in one)
	 * @param tests The tests (at least one must be present)
	 * @return The test run created
	 */
	public static TestRun createTestRun(String projectApiId, String projectVersion, Long endTimeStamp,
		long duration, String group, String uid, List<Test> tests) {

		if (tests == null || tests.isEmpty()) {
			throw new IllegalArgumentException("At least one test must be present.");
		}
		
		if (duration < 0) {
			throw new IllegalArgumentException("The duration cannot be negative.");
		}
		
		TestRun testRun = new TestRun();
		
		testRun.setDuration(duration);
		testRun.setGroup(group);
		testRun.setUid(uid);
		
		final ProjectTestResults results = new ProjectTestResults();
		results.setProject(projectApiId);
		results.setVersion(projectVersion);
		results.getTests().addAll(tests);

		testRun.getResults().add(results);

		return testRun;
	}
	
	/**
	 * Create a test result
	 * 
	 * @param key The unique key that identifies the test (mandatory)
	 * @param name Name
	 * @param endTimeStamp End time stamp
	 * @param duration Approximative duration time (0 or positive)
	 * @param message Message to enrich the result (mandatory when failed, max. 65535 car, truncated)
	 * @param passed Flag to know if a test pass or not
	 * @param flagsValue flags value (sum of all the flags)
	 * @param tags A list of tags
	 * @param tickets A list of tickets
	 * @param data A list of meta data
	 * @return Created test
	 */
	public static Test createTest(String key, String name, String category,
		Long endTimeStamp, long duration, String message, boolean passed, int flagsValue, 
		Set<String> tags, Set<String> tickets, Map<String, String> data) {
		
		if (key == null) {
			throw new IllegalArgumentException("The key must be specified.");
		}
		
		Test test = new Test();

		if (!passed) {
			if (message == null) {
				test.setMessage("No message provided for the failing test");
			}
			else if (message.isEmpty()) {
				test.setMessage("No content for the message provided for the failing test");
			}
		}
		
		if (duration < 0) {
			throw new IllegalArgumentException("The duration cannot be negative.");
		}
				
		test.setKey(key);
		test.setName(name);
		test.setDuration(duration);
		test.setPassed(passed);
		
		if (test.getMessage() == null) {
			if (message != null && message.getBytes(Charset.forName(Constants.ENCODING)).length > 65535) {
				test.setMessage(new String(message.getBytes(Charset.forName(Constants.ENCODING)), 0, 65532, Charset.forName(Constants.ENCODING)) + "...");
			}
			else {
				test.setMessage(message);
			}
		}
		
		if (category != null && !category.isEmpty()) {
			test.setCategory(category);
		}
		
		if (flagsValue > -1) {
			test.setFlags(flagsValue);
		}
		
		if (tags != null) {
			test.addTags(tags);
		}
		
		if (tickets != null) {
			test.addTickets(tickets);
		}
		
		if (data != null) {
			test.addData(data);
		}
		
		return test;
	}
}
