package com.lotaris.rox.core.utils;

import com.lotaris.rox.annotations.RoxableTest;
import com.lotaris.rox.common.utils.Inflector;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for class {@link Inflector}
 * 
 * @author Laurent Prevost, laurent.prevost@lotaris.com
 */
public class InflectorTest {
	@Test
	@RoxableTest(key = "100ee866fc6d")
	public void inflectorShouldReturnCorrectTransformation() {
		assertEquals("The name theMethodNameShouldBeTransformed was not humanized", 
			Inflector.getHumanName("theMethodNameShouldBeTransformed"), 
			"The method name should be transformed");
	}
	
	@Test
	@RoxableTest(key = "c3c3aa18a847")
	public void inflectorShouldReturnCorrectTransformationFromMethodName() {
		try {
			assertEquals("The name theMethodNameShouldBeTransformed was not humanized", 
				Inflector.getHumanName(InflectorTest.class.getMethod("inflectorShouldReturnCorrectTransformationFromMethodName")), 
				"Inflector should return correct transformation from method name");
		}
		catch (NoSuchMethodException nme) {
			fail(nme.getMessage());
		}
		catch (SecurityException se) {
			fail(se.getMessage());
		}
	}
	
	@Test
	@RoxableTest(key = "937e9aab4a84")
	public void inflectorShouldWorkWhenNumbersArePresentInTheMethodName() {
		assertEquals("The name methodWithNumber1AndNumber2PresentInTheMethodName was not humanized", 
			Inflector.getHumanName("methodWithNumber1AndNumber2PresentInTheMethodName"), 
			"Method with number 1 and number 2 present in the method name");
	}
}
