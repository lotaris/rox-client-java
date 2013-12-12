package com.lotaris.rox.annotations;

import java.util.List;

/**
 * Define several flags to enrich the tests
 * 
 * @author Laurent Prevost, laurent.prevost@lotaris.com
 */
public enum TestFlag {
	/**
	 * Special status that will never be sent to ROX or any other tool.
	 * As annotations in Java do not allow to specify a default value <em>null</em>
	 * for a enumeration type, we should to bypass the limitation by using a special
	 * enum value that will should be processed correctly.
	 */
	SPECIAL_VALUE_NOT_USED {
		@Override
		public int position() {
			return -1;
		}
	},
	
	/**
	 * Define a test currently writing that the result is not 
	 * expected to be stable for a while.
	 * 
	 * @since API V1
	 */
	INACTIVE {
		@Override
		public int position() {
			return 0;
		}
	};
	
	/**
	 * @return The flag position
	 */
	public abstract int position();
	
	/**
	 * @return Calculate the flag value
	 */
	public int flagValue() {
		return (int) Math.pow(2.0, (double) position());
	}
	
	/**
	 * @param flags A list of flags
	 * @return Calculate the sum of flag values
	 */
	public static int flagsValue(List<TestFlag> flags) {
		int flagValue = 0;
		
		if (flags != null) {
			for (TestFlag tf : flags) {
				if (tf != TestFlag.SPECIAL_VALUE_NOT_USED) {
					flagValue += (int) Math.pow(2.0, (double) tf.position());
				}
			}		
		}
		
		return flagValue;
	}
}
