package com.lotaris.rox.commons.optimize;

import com.lotaris.rox.common.model.RoxPayload;

/**
 * Define how an optimizer should work
 * 
 * @author Laurent Prevost, laurent.prevost@lotaris.com
 */
public interface Optimizer {
	/**
	 * Execute the optimization 
	 * 
	 * @param store The optimizer store to check if a test has changed or not
	 * @param optimizable The optimizable to optimize
	 * @return The optimizable optimized
	 */
	RoxPayload optimize(OptimizerStore store, RoxPayload optimizable);
}
