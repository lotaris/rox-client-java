package com.lotaris.rox.common.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * A list of test results for a specific version of a project.
 *
 * @author Simon Oulevay (simon.oulevay@lotaris.com)
 */
public class ProjectTestResults {

	@JsonProperty("j")
	private String project;
	@JsonProperty("v")
	private String version;
	@JsonProperty("t")
	private List<Test> tests;

	public ProjectTestResults() {
		tests = new ArrayList<Test>();
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<Test> getTests() {
		return tests;
	}
}
