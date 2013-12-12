package com.lotaris.rox.common.model;

import com.lotaris.rox.commons.optimize.Optimizer;

/**
 * Allow to retrieve REST resource backend path to build
 * correct URL depending the version of the API.
 * 
 * @author Laurent Prevost, laurent.prevost@lotaris.com
 */
public interface RoxPayload {
	/**
	 * @return Retrieve the API version
	 */
	String getVersion();
	
	/**
	 * @return Get the optimizer corresponding to the right payload version
	 */
	Optimizer getOptimizer();	
}
