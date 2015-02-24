package com.lotaris.rox.core.filters;

import com.lotaris.rox.annotations.RoxableTest;
import com.lotaris.rox.annotations.RoxableTestClass;
import java.lang.reflect.Method;

/**
 * Filter utility
 * 
 * @author Laurent Prevost, laurent.prevost@lotaris.com
 */
public class FilterUtils {
//	private static final Logger LOGGER = LoggerFactory.getLogger(FilterUtils.class);
	
	/**
	 * Define if a test is runnable or not based on a method
	 * 
	 * @param method The method
	 * @param filters The filters to apply
	 * @return True if the test can be run
	 */
	public static boolean isRunnable(Method method, String[] filters) {
		// Get the ROX annotations
		RoxableTest mAnnotation = method.getAnnotation(RoxableTest.class);
		RoxableTestClass cAnnotation = method.getDeclaringClass().getAnnotation(RoxableTestClass.class);

		if (mAnnotation != null || cAnnotation != null) {
			return isRunnable(new FilterTargetData(method, mAnnotation, cAnnotation), filters);
		}		

		return true;
	}
	
	/**
	 * Define if a test is runnable or not based on a method name and class
	 * 
	 * @param cl Class
	 * @param methodName The method name
	 * @param filters The filters to apply
	 * @return True if the test can be run
	 */
	@SuppressWarnings("unchecked")
	public static boolean isRunnable(Class cl, String methodName, String[] filters) {
		try {
			Method m = cl.getMethod(methodName);
			return isRunnable(m, filters);
		}
		catch (NoSuchMethodException | SecurityException e) {
			return true;
		}
	}

	/**
	 * Define if a test is runnable based on its description data
	 * 
	 * @param name Test name
	 * @param technicalName Technical name
	 * @param key ROX key
	 * @param tags The tags
	 * @param tickets The tickets
	 * @param filters The filters
	 * @return True if the test can be run
	 */
	public static boolean isRunnable(String name, String technicalName, String key, String tags, String tickets, String[] filters) {
		return isRunnable(new FilterTargetData(tags, tickets, technicalName, name, key), filters);
	}
	
	/**
	 * Define if a test is runnable based on the object that represents the test
	 * meta data
	 * 
	 * @param targetData The meta data
	 * @param filters The filters
	 * @return True if the test can be run
	 */
	private static boolean isRunnable(FilterTargetData targetData, String[] filters) {
		if (filters == null || filters.length == 0) {
			return true;
		}
		
		for (String filter : filters) {
			String[] filterSplitted = filter.split(":");

			// We must evaluate all the filters and return only when there is a valid match
			if (
				(filterSplitted.length == 1 && targetData.anyMatch(filterSplitted[0])) || // Any filter
				("key".equalsIgnoreCase(filterSplitted[0]) && targetData.keyMatch(filterSplitted[1])) || // Key filter
				("name".equalsIgnoreCase(filterSplitted[0]) && targetData.nameMatch(filterSplitted[1])) || // Name filter
				("tag".equalsIgnoreCase(filterSplitted[0]) && targetData.tagMatch(filterSplitted[1])) || // Tag filter
				("ticket".equalsIgnoreCase(filterSplitted[0]) && targetData.ticketMatch(filterSplitted[1])) // Ticket filter
			) {
				return true;
			}
		}

		return false;
	}
}