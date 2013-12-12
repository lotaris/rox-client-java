package com.lotaris.rox.commons.optimize.v1;

import com.lotaris.rox.common.model.RoxPayload;
import com.lotaris.rox.common.model.v1.Payload;
import com.lotaris.rox.common.model.v1.ProjectTestResults;
import com.lotaris.rox.common.model.v1.Test;
import com.lotaris.rox.common.model.v1.TestRun;
import com.lotaris.rox.common.utils.FootprintGenerator;
import com.lotaris.rox.commons.optimize.Optimizer;
import com.lotaris.rox.commons.optimize.OptimizerStore;
import java.util.Map;

/**
 * Payload optimizer that remove some fields from the final
 * payload to optimize what is sent to ROX server.
 * 
 * This optimizer is expected to work for a {@link Payload} in
 * version 1 of the API level
 * 
 * @author Laurent Prevost, laurent.prevost@lotaris.com
 */
public class PayloadOptimizer implements Optimizer {
	@Override
	public RoxPayload optimize(OptimizerStore store, RoxPayload optimizable) {
		if (store == null) {
			throw new IllegalArgumentException("The optimizer store cannot be null");
		}
		
		if (optimizable == null) {
			throw new IllegalArgumentException("The optimizable object cannot be null");
		}
		
		if (optimizable.getClass() != Payload.class) {
			throw new IllegalArgumentException("The payload given is not the one that is expected.");
		}

		Payload originalPayload = (Payload) optimizable;
		Payload optimizedPayload = new Payload();

		TestRun newTestRun = copyTestRun(originalPayload.getTestRun());
		optimizedPayload.setTestRun(newTestRun);
		
		for (ProjectTestResults originalResults : originalPayload.getTestRun().getResults()) {
			
			ProjectTestResults newResults = new ProjectTestResults();
			newResults.setProject(originalResults.getProject());
			newResults.setVersion(originalResults.getVersion());
			optimizedPayload.getTestRun().getResults().add(newResults);

			String testProject = newResults.getProject();
			String testVersion = newResults.getVersion();

			for (Test test : originalResults.getTests()) {

				// Calculate the footprint
				String footprint = footprint(test);

				// Copy the test in an optimized way
				newResults.getTests().add(copyTest(test, store.testHasChanged(testProject, testVersion, test.getKey(), footprint)));

				// Store the footprint
				store.storeTestFootprint(testProject, testVersion, test.getKey(), footprint);
			}
		}
		
		return optimizedPayload;
	}
	
	/**
	 * Copy a test run
	 * 
	 * @param testRun Test run to copy
	 * @return Test run copied
	 */
	private TestRun copyTestRun(TestRun testRun) {
		TestRun newTestRun = new TestRun();
		
		newTestRun.setDuration(testRun.getDuration());
		newTestRun.setGroup(testRun.getGroup());
		newTestRun.setUid(testRun.getUid());
		
		return newTestRun;
	}
	
	/**
	 * Copy a test
	 * 
	 * @param test Test to copy
	 * @param full Define if full attributes should be copied or not
	 * @return The test copied
	 */
	private Test copyTest(Test test, boolean full) {
		Test newTest = new Test();
		
		newTest.setDuration(test.getDuration());
		newTest.setKey(test.getKey());
		newTest.setPassed(test.isPassed());
		newTest.setMessage(test.getMessage());

		if (full) {
			newTest.setFlags(test.getFlags());
			newTest.setCategory(test.getCategory());
			newTest.setName(test.getName());
			newTest.addTags(test.getTags());
			newTest.addTickets(test.getTickets());
			newTest.addData(test.getData());
		}
		
		return newTest;
	}
	
	/**
	 * Compute the footprint for a test
	 * 
	 * @param test The test for which the footprint is computed
	 * @return The footprint computed
	 */
	private String footprint(Test test) {
		StringBuilder sb = new StringBuilder();
		
		sb
			.append(test.getCategory())
			.append(test.getName())
			.append(test.getFlags());
		
		for (String tag : test.getTags()) {
			sb.append(tag);
		}
		
		for (String ticket : test.getTickets()) {
			sb.append(ticket);
		}
		
		for (Map.Entry<String, String> entry : test.getData().entrySet()) {
			sb.append(entry.getKey()).append(entry.getValue());
		}
	
		return FootprintGenerator.footprint(sb.toString());
	}
}