package com.lotaris.rox.common.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * TestRun class to handle the data related to test runs
 *
 * @author Laurent Prevost, laurent.prevost@lotaris.com
 */
public class TestRun {
	
	@JsonProperty("g")
	private String group;
	
	@JsonProperty("u")
	private String uid;
	
	@JsonProperty("d")
	private long duration;
	
	@JsonProperty("r")
	private List<ProjectTestResults> results;
	
	public TestRun() {
		results = new ArrayList<>();
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public List<ProjectTestResults> getResults() {
		return results;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (ProjectTestResults r : results) {
			sb.append(r).append(", ");
		}
		
		return 
			"TestRun: [" + 
				"Group: " + group + ", " +
				"Uid: " + uid + ", " +
				"Duration: " + duration + ", " + 
				"Tests: [" + sb.toString().replaceAll(", $", "") + "]" +
			"]";
	}
}
