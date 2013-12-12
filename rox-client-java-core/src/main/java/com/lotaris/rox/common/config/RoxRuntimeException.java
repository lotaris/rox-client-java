package com.lotaris.rox.common.config;

/**
 * @author Simon Oulevay (simon.oulevay@lotaris.com)
 */
public class RoxRuntimeException extends RuntimeException {

	public RoxRuntimeException(String message) {
		super(message);
	}

	public RoxRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
}
