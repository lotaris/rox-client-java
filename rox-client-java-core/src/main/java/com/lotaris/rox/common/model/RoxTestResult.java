package com.lotaris.rox.common.model;

import java.util.Map;
import java.util.Set;

/**
 * Define what a test result should be
 * 
 * @author Laurent Prevost, laurent.prevost@lotaris.com
 */
public interface RoxTestResult {
	/**
	 * @return The unique identifier of the test. This id comes from ROX itself.
	 */
	String getKey();

	/**
	 * @return The name of the test
	 */
	String getName();

	/**
	 * @return The duration of the test execution
	 */
	long getDuration();
	
	/**
	 * @return If the test is passed or not
	 */
	boolean isPassed();
	
	/**
	 * @return The message of the test result
	 */
	String getMessage();

	/**
	 * @return The category of the test
	 */
	String getCategory();

	/**
	 * @return The flag
	 */
	Integer getFlags();

	/**
	 * @return Set of tags
	 */
	Set<String> getTags();
	
	/**
	 * @return Set of tickets
	 */
	Set<String> getTickets();

	/**
	 * @return Map of data
	 */
	Map<String, String> getData();
}