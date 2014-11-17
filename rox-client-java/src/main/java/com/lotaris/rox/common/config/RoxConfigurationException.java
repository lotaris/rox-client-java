package com.lotaris.rox.common.config;

/**
 * @author Simon Oulevay (simon.oulevay@lotaris.com)
 */
public class RoxConfigurationException extends RoxRuntimeException {

	public RoxConfigurationException(String message) {
		super(message);
	}

	public RoxConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}
}
