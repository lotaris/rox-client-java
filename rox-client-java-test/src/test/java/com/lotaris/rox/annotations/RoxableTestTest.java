package com.lotaris.rox.annotations;

import com.lotaris.rox.utils.CollectionHelper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for {@link CollectionHelper}
 * 
 * @author Laurent Prevost, laurent.prevost@lotaris.com
 */
@RoxableTestClass(tags = "roxable-test")
public class RoxableTestTest {
	private RoxableTest annotation;
	
	@RoxableTest(key = "")
	public void dummyMethod() {}
	
	@Before
	public void setUp() {
		try {
			annotation = RoxableTestTest.class.getMethod("dummyMethod").getAnnotation(RoxableTest.class);
		}
		catch (Exception e) {}
	}
	
	@Test
	@RoxableTest(key = "0371d12bcca0")
	public void roxableMethodAnnotationShouldNotAllowNullKey() {
		assertNotNull("The key on annotation cannot be null", annotation.key());
	}
	
	@Test
	@RoxableTest(key = "5e547a3b9704")
	public void roxableMethodAnnotationShouldHaveEmptyValueForCategoryByDefault() {
		assertEquals("The default value for rox category should be empty", "", annotation.category());
	}
	
	@Test
	@RoxableTest(key = "40c7802493a3")
	public void roxableMethodAnnotationShouldHaveEmptyArrayOfStringForTagsByDefault() {
		assertEquals("The default value for rox tags should be empty array", 0, annotation.tags().length);
	}

	@Test
	@RoxableTest(key = "5ac43c3919fd")
	public void roxableMethodAnnotationShouldHaveEmptyArrayOfStringForTicketsByDefault() {
		assertEquals("The default value for rox tickets should be empty array", 0, annotation.tickets().length);
	}
	
	@Test
	@RoxableTest(key = "214f77ca49d8")
	public void roxableMethodAnnotationShouldHaveDummyValueForFlagByDefault() {
		assertEquals("A default value must be present for rox flag", 1, annotation.flags().length);
		assertEquals("The default value for rox flag should be " + TestFlag.SPECIAL_VALUE_NOT_USED, TestFlag.SPECIAL_VALUE_NOT_USED, annotation.flags()[0]);
	}
}
