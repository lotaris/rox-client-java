package com.lotaris.rox.utils;

import com.lotaris.rox.annotations.RoxableTest;
import com.lotaris.rox.annotations.RoxableTestClass;
import com.lotaris.rox.test.utils.TestHelper;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test class for {@link CollectionHelper}
 * 
 * @author Laurent Prevost, laurent.prevost@lotaris.com
 */
@RoxableTestClass(tags = "collection-helper")
public class CollectionHelperTest {
	@Mock
	private Logger LOGGER = LoggerFactory.getLogger(CollectionHelper.class);;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		try {
			TestHelper.setFinalStatic(CollectionHelper.class.getDeclaredField("LOGGER"), LOGGER);
		}
		catch (Exception e) {}
	}
	
	@After
	public void tearDown() {
		try {
			TestHelper.setFinalStatic(CollectionHelper.class.getDeclaredField("LOGGER"), LoggerFactory.getLogger(CollectionHelper.class));
		}
		catch (Exception e) {}
	}
	
	@Test
	@RoxableTest(key = "954f500c6c06")
	public void whenNoParameterAreGivenToGetTheTagsItShouldReturnEmptyTags() {
		assertTrue("The tags must be emtpy", CollectionHelper.getTags(null, null, null).isEmpty());
	}
	
	@Test
	@RoxableTest(key = "2b71fa21a2ea")
	public void whenNoTagsAreAvailableTheTagsMustBeEmpty() {
		assertTrue("The tags must be empty", CollectionHelper.getTags(new HashSet<String>(), null, null).isEmpty());
	}
	
	@Test
	@RoxableTest(key = "8d7040ae3a30")
	public void whenTagsAreSpecifiedOnlyInTheConfigurationOnlyTheseMustBeReturned() {
		Set<String> tags = CollectionHelper.getTags(new HashSet<String>(Arrays.asList(new String[]{"tag1", "tag2"})), null, null);
		
		assertEquals("The tags should contains two tags", 2, tags.size());
		assertTrue("[tag1] must be present in the tags", tags.contains("tag1"));
		assertTrue("[tag2] must be present in the tags", tags.contains("tag2"));
	}
	
	@Test
	@RoxableTest(key = "d526a0fde438")
	public void theTagsShouldRespectCertainFormat() {
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) {
				assertTrue("The message should contains [The tag *ç%&/( does not respect the following pattern]",
					((String) invocation.getArguments()[0]).contains("The tag *ç%&/( does not respect the following pattern"));
				return null;
			}
		}).when(LOGGER).warn(any(String.class));
		
		Set<String> tags = CollectionHelper.getTags(new HashSet<String>(Arrays.asList(new String[]{"*ç%&/(", "tagValid"})), null, null);
		
		assertEquals("The tags should contains one tag", 1, tags.size());
		assertTrue("The tags should contains [tagValid]", tags.contains("tagValid"));
	}
	
	@Test
	@RoxableTest(key = "d44bfb239374")
	public void whenTagsAreSpecifiedInTheConfigurationAndTheClassAnnotationTheCompiledTagsMustBeReturned() {
		RoxableTestClass classAnnotation = mock(RoxableTestClass.class);
		
		when(classAnnotation.tags()).thenReturn(new String[]{"tag3", "tag4"});
		
		Set<String> tags = CollectionHelper.getTags(new HashSet<String>(Arrays.asList(new String[]{"tag1", "tag2"})), null, classAnnotation);
		
		assertEquals("The tags should contains four tags", 4, tags.size());
		assertTrue("[tag1] must be present in the tags", tags.contains("tag1"));
		assertTrue("[tag2] must be present in the tags", tags.contains("tag2"));
		assertTrue("[tag3] must be present in the tags", tags.contains("tag3"));
		assertTrue("[tag4] must be present in the tags", tags.contains("tag4"));
	}

	@Test
	@RoxableTest(key = "10a407c69e99")
	public void whenTagsAreSpecifiedInTheConfigurationAndAnnotationsTheCompiledTagsMustBeReturned() {
		RoxableTestClass classAnnotation = mock(RoxableTestClass.class);
		when(classAnnotation.tags()).thenReturn(new String[]{"tag3", "tag4"});

		RoxableTest methodAnnotation = mock(RoxableTest.class);
		when(methodAnnotation.tags()).thenReturn(new String[]{"tag5", "tag6"});
		
		Set<String> tags = CollectionHelper.getTags(new HashSet<String>(Arrays.asList(new String[]{"tag1", "tag2"})), methodAnnotation, classAnnotation);
		
		assertEquals("The tags should contains six tags", 6, tags.size());
		assertTrue("[tag1] must be present in the tags", tags.contains("tag1"));
		assertTrue("[tag2] must be present in the tags", tags.contains("tag2"));
		assertTrue("[tag3] must be present in the tags", tags.contains("tag3"));
		assertTrue("[tag4] must be present in the tags", tags.contains("tag4"));
		assertTrue("[tag5] must be present in the tags", tags.contains("tag5"));
		assertTrue("[tag6] must be present in the tags", tags.contains("tag6"));
	}
	
	@Test
	@RoxableTest(key = "79163f595599")
	public void addingMultipleTimesTheSameTagShouldOnlyKeepOne() {
		RoxableTestClass classAnnotation = mock(RoxableTestClass.class);
		when(classAnnotation.tags()).thenReturn(new String[]{"tag1", "tag2"});

		RoxableTest methodAnnotation = mock(RoxableTest.class);
		when(methodAnnotation.tags()).thenReturn(new String[]{"tag1", "tag2"});
		
		Set<String> tags = CollectionHelper.getTags(new HashSet<String>(Arrays.asList(new String[]{"tag1", "tag2"})), methodAnnotation, classAnnotation);
		
		assertEquals("The tags should contains six tags", 2, tags.size());
		assertTrue("[tag1] must be present in the tags", tags.contains("tag1"));
		assertTrue("[tag2] must be present in the tags", tags.contains("tag2"));
	}

	@Test
	@RoxableTest(key = "804333341a0a")
	public void whenNoParameterAreGivenToGetTheTicketsItShouldReturnEmptyTickets() {
		assertTrue("The tickets must be emtpy", CollectionHelper.getTickets(null, null, null).isEmpty());
	}
	
	@Test
	@RoxableTest(key = "75533d91c5d3")
	public void whenNoTicketsAreAvailableTheTicketsMustBeEmpty() {
		assertTrue("The tickets must be empty", CollectionHelper.getTickets(new HashSet<String>(), null, null).isEmpty());
	}
	
	@Test
	@RoxableTest(key = "080a38cfd0e5")
	public void whenTicketsAreSpecifiedOnlyInTheConfigurationOnlyTheseMustBeReturned() {
		Set<String> tickets = CollectionHelper.getTickets(new HashSet<String>(Arrays.asList(new String[]{"ticket-1", "ticket-2"})), null, null);
		
		assertEquals("The tickets should contains two tickets", 2, tickets.size());
		assertTrue("[ticket-1] must be present in the tickets", tickets.contains("ticket-1"));
		assertTrue("[ticket-2] must be present in the tickets", tickets.contains("ticket-2"));
	}
	
	@Test
	@RoxableTest(key = "5f4848dcdc85")
	public void whenTicketsAreSpecifiedInTheConfigurationAndTheClassAnnotationTheCompiledTicketsMustBeReturned() {
		RoxableTestClass classAnnotation = mock(RoxableTestClass.class);
		
		when(classAnnotation.tickets()).thenReturn(new String[]{"ticket-3", "ticket-4"});
		
		Set<String> tickets = CollectionHelper.getTickets(new HashSet<String>(Arrays.asList(new String[]{"ticket-1", "ticket-2"})), null, classAnnotation);
		
		assertEquals("The tickets should contains four tickets", 4, tickets.size());
		assertTrue("[ticket-1] must be present in the tickets", tickets.contains("ticket-1"));
		assertTrue("[ticket-2] must be present in the tickets", tickets.contains("ticket-2"));
		assertTrue("[ticket-3] must be present in the tickets", tickets.contains("ticket-3"));
		assertTrue("[ticket-4] must be present in the tickets", tickets.contains("ticket-4"));
	}

	@Test
	@RoxableTest(key = "234f7fea5302")
	public void whenTicketsAreSpecifiedInTheConfigurationAndAnnotationsTheCompiledTicketsMustBeReturned() {
		RoxableTestClass classAnnotation = mock(RoxableTestClass.class);
		when(classAnnotation.tickets()).thenReturn(new String[]{"ticket-3", "ticket-4"});

		RoxableTest methodAnnotation = mock(RoxableTest.class);
		when(methodAnnotation.tickets()).thenReturn(new String[]{"ticket-5", "ticket-6"});
		
		Set<String> tickets = CollectionHelper.getTickets(new HashSet<String>(Arrays.asList(new String[]{"ticket-1", "ticket-2"})), methodAnnotation, classAnnotation);
		
		assertEquals("The tickets should contains six tickets", 6, tickets.size());
		assertTrue("[ticket-1] must be present in the tickets", tickets.contains("ticket-1"));
		assertTrue("[ticket-2] must be present in the tickets", tickets.contains("ticket-2"));
		assertTrue("[ticket-3] must be present in the tickets", tickets.contains("ticket-3"));
		assertTrue("[ticket-4] must be present in the tickets", tickets.contains("ticket-4"));
		assertTrue("[ticket-5] must be present in the tickets", tickets.contains("ticket-5"));
		assertTrue("[ticket-6] must be present in the tickets", tickets.contains("ticket-6"));
	}	

	@Test
	@RoxableTest(key = "3392f6343875")
	public void addingMultipleTimesTheSameTicketShouldOnlyKeepOne() {
		RoxableTestClass classAnnotation = mock(RoxableTestClass.class);
		when(classAnnotation.tickets()).thenReturn(new String[]{"ticket-1", "ticket-2"});

		RoxableTest methodAnnotation = mock(RoxableTest.class);
		when(methodAnnotation.tickets()).thenReturn(new String[]{"ticket-1", "ticket-2"});
		
		Set<String> tickets = CollectionHelper.getTickets(new HashSet<String>(Arrays.asList(new String[]{"ticket-1", "ticket-2"})), methodAnnotation, classAnnotation);
		
		assertEquals("The tickets should contains six tickets", 2, tickets.size());
		assertTrue("[ticket-1] must be present in the tickets", tickets.contains("ticket-1"));
		assertTrue("[ticket-2] must be present in the tickets", tickets.contains("ticket-2"));
	}	
}
