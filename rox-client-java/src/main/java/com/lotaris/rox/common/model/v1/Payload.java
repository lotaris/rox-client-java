package com.lotaris.rox.common.model.v1;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.lotaris.rox.common.model.RoxPayload;
import com.lotaris.rox.commons.optimize.Optimizer;
import com.lotaris.rox.commons.optimize.v1.PayloadOptimizer;

/**
 * Root element to store a list of test runs
 * 
 * @author Laurent Preost, laurent.prevost@lotaris.com
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class Payload implements RoxPayload {
	private static final String API_VERSION = "v1";
	
	@JsonProperty
	@JsonUnwrapped
	private TestRun testRun;

	@Override
	public String getVersion() {
		return API_VERSION;
	}

	@Override
	public Optimizer getOptimizer() {
		return new PayloadOptimizer();
	}

	public TestRun getTestRun() {
		return testRun;
	}

	public void setTestRun(TestRun testRun) {
		this.testRun = testRun;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("TestRuns: [");
		sb.append(testRun);
		return "Payload: [" + sb.toString().replaceAll(", $", "]") + "]";
	}
}
