package com.lotaris.rox.annotations;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for {@link RoxableTestClass}
 * 
 * @author Laurent Prevost, laurent.prevost@lotaris.com
 */
@RoxableTestClass(tags = "roxable-test-class")
public class RoxableTestClassTest {
	private RoxableTestClass annotation;
	
	@Before
	public void setUp() {
		try {
			annotation = DummyClass.class.getAnnotation(RoxableTestClass.class);
		}
		catch (Exception e) {}
	}
	
	@Test
	@RoxableTest(key = "1cd6a3e9bf66")
	public void roxableClassAnnotationShouldHaveEmptyValueForCategoryByDefault() {
		assertEquals("The default value for rox category should be empty", "", annotation.category());
	}
	
	@Test
	@RoxableTest(key = "3956134c2cf2")
	public void roxableClassAnnotationShouldHaveEmptyArrayOfStringForTagsByDefault() {
		assertEquals("The default value for rox tags should be empty array", 0, annotation.tags().length);
	}

	@Test
	@RoxableTest(key = "0e36fe8b68d6")
	public void roxableClassAnnotationShouldHaveEmptyArrayOfStringForTicketsByDefault() {
		assertEquals("The default value for rox tickets should be empty array", 0, annotation.tickets().length);
	}

	@RoxableTestClass
	public class DummyClass {}
}
