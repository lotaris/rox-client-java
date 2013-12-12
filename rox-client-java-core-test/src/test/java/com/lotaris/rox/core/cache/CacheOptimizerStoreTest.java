package com.lotaris.rox.core.cache;

import com.lotaris.rox.annotations.RoxableTest;
import com.lotaris.rox.common.config.Configuration;
import com.lotaris.rox.common.config.ServerConfiguration;
import com.lotaris.rox.common.model.v1.Payload;
import com.lotaris.rox.common.model.v1.ModelFactory;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * 
 * @author Laurent Prévost, laurent.prevost@lotaris.com
 */
public class CacheOptimizerStoreTest {
	
	private Payload referencePayload;
	private CacheOptimizerStore cacheOptimizerStore;
	
	@Mock
	private Configuration configuration;
	@Mock
	private ServerConfiguration serverConfiguration;

	@Before
	public void setUp() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("dataKey", "dataValue");
		
		referencePayload = ModelFactory.createPayload(
			ModelFactory.createTestRun("project", "version", System.currentTimeMillis(), 10L, "group", "uid", 
				Arrays.asList(new com.lotaris.rox.common.model.v1.Test[] {
					ModelFactory.createTest("key", "name", "category", System.currentTimeMillis(), 10L, "message", true, -1, 
						new HashSet<String>(Arrays.asList(new String[] { "tag1", "tag2" })),
						new HashSet<String>(Arrays.asList(new String[] { "ticket1", "ticket2" })),
						data
					)
				})
			)
		);
		
		MockitoAnnotations.initMocks(this);

		when(configuration.getOptimizerCacheDir()).thenReturn("/tmp/rox-cache");
		when(configuration.getServerConfiguration()).thenReturn(serverConfiguration);
		when(serverConfiguration.getBaseUrlFootprint()).thenReturn("footprint");
		
		cacheOptimizerStore = new CacheOptimizerStore();
		cacheOptimizerStore.start(configuration);
		cacheOptimizerStore.cleanCaches();
	}
	
	@Test
	@RoxableTest(key = "4e60735e7e5d")
	public void payloadShouldBeCompleteWhenNotCached() {
		Payload optiOne = (Payload) referencePayload.getOptimizer().optimize(cacheOptimizerStore, referencePayload);
		
		com.lotaris.rox.common.model.v1.Test test = optiOne.getTestRun().getResults().get(0).getTests().get(0);
		
		assertNotNull("Name must be filled", test.getName());
		assertNotNull("Category must be filled", test.getCategory());
		assertFalse("Tags must be filled", test.getTags().isEmpty());
		assertFalse("Tickets must be filled", test.getTickets().isEmpty());
		assertFalse("Data must be filled", test.getData().isEmpty());
	}
	
	@Test
	@RoxableTest(key = "0294fde33f88")
	public void payloadShouldBeCleanedWhenCached() {
		referencePayload.getOptimizer().optimize(cacheOptimizerStore, referencePayload);
		Payload optiTwo = (Payload) referencePayload.getOptimizer().optimize(cacheOptimizerStore, referencePayload);
		
		com.lotaris.rox.common.model.v1.Test test = optiTwo.getTestRun().getResults().get(0).getTests().get(0);
		
		assertNull("Name must be null", test.getName());
		assertNull("Category must be null", test.getCategory());
		assertNull("Tags must be empty", test.getTags());
		assertNull("Tickets must be empty", test.getTickets());
		assertNull("Data must be empty", test.getData());
	}
	
	@Test
	@RoxableTest(key = "87d0a5a2548c")
	public void payloadShouldBeCompleteWhenNameChanged() {
		Payload optiOne = (Payload) referencePayload.getOptimizer().optimize(cacheOptimizerStore, referencePayload);
		
		referencePayload.getTestRun().getResults().get(0).getTests().get(0).setName("different name");
		String optiOneStr = optiOne.getTestRun().getResults().get(0).getTests().get(0).toString();
		
		Payload optiTwo = (Payload) referencePayload.getOptimizer().optimize(cacheOptimizerStore, referencePayload);
		String optiTwoStr = optiTwo.getTestRun().getResults().get(0).getTests().get(0).toString();
		
		assertNotNull("Name must be filled", optiTwo.getTestRun().getResults().get(0).getTests().get(0).getName());
		assertFalse("The test toString should be different", optiOneStr.equals(optiTwoStr));
	}
	
	@Test
	@RoxableTest(key = "eab23b149bc7")
	public void payloadShouldBeCompleteWhenCategoryChanged() {
		Payload optiOne = (Payload) referencePayload.getOptimizer().optimize(cacheOptimizerStore, referencePayload);
		
		referencePayload.getTestRun().getResults().get(0).getTests().get(0).setCategory("different category");
		String optiOneStr = optiOne.getTestRun().getResults().get(0).getTests().get(0).toString();
		
		Payload optiTwo = (Payload) referencePayload.getOptimizer().optimize(cacheOptimizerStore, referencePayload);
		String optiTwoStr = optiTwo.getTestRun().getResults().get(0).getTests().get(0).toString();
		
		assertNotNull("Category must be filled", optiTwo.getTestRun().getResults().get(0).getTests().get(0).getCategory());
		assertFalse("The test toString should be different", optiOneStr.equals(optiTwoStr));
	}
	
	@Test
	@RoxableTest(key = "525306f508b8")
	public void payloadShouldBeCompleteWhenProjectChanged() {
		referencePayload.getOptimizer().optimize(cacheOptimizerStore, referencePayload);
		
		referencePayload.getTestRun().getResults().get(0).setProject("different project");
		
		Payload optiTwo = (Payload) referencePayload.getOptimizer().optimize(cacheOptimizerStore, referencePayload);
		
		assertNotNull("Name must be filled", optiTwo.getTestRun().getResults().get(0).getTests().get(0).getName());
		//assertFalse("The test toString should be different", optiOneStr.equals(optiTwoStr));
	}

	@Test
	@RoxableTest(key = "c66d8d1598a6")
	public void payloadShouldBeCompleteWhenVersionChanged() {
		referencePayload.getOptimizer().optimize(cacheOptimizerStore, referencePayload);
		
		referencePayload.getTestRun().getResults().get(0).setVersion("different version");
		
		Payload optiTwo = (Payload) referencePayload.getOptimizer().optimize(cacheOptimizerStore, referencePayload);
		
		assertNotNull("Name must be filled", optiTwo.getTestRun().getResults().get(0).getTests().get(0).getName());
	}

	@Test
	@RoxableTest(key = "0a4eb4bcf9be")
	public void payloadShouldBeCompleteWhenTagsChanged() {
		Payload optiOne = (Payload) referencePayload.getOptimizer().optimize(cacheOptimizerStore, referencePayload);
		
		referencePayload.getTestRun().getResults().get(0).getTests().get(0).getTags().add("new tag");
		String optiOneStr = optiOne.getTestRun().getResults().get(0).getTests().get(0).toString();
		
		Payload optiTwo = (Payload) referencePayload.getOptimizer().optimize(cacheOptimizerStore, referencePayload);
		String optiTwoStr = optiTwo.getTestRun().getResults().get(0).getTests().get(0).toString();
		
		assertFalse("Tags must be filled", optiTwo.getTestRun().getResults().get(0).getTests().get(0).getTags().isEmpty());
		assertFalse("The test toString should be different", optiOneStr.equals(optiTwoStr));
	}

	@Test
	@RoxableTest(key = "aebaf2d8dd70")
	public void payloadShouldBeCompleteWhenTicketsChanged() {
		Payload optiOne = (Payload) referencePayload.getOptimizer().optimize(cacheOptimizerStore, referencePayload);
		
		referencePayload.getTestRun().getResults().get(0).getTests().get(0).getTickets().add("new ticket");
		String optiOneStr = optiOne.getTestRun().getResults().get(0).getTests().get(0).toString();
		
		Payload optiTwo = (Payload) referencePayload.getOptimizer().optimize(cacheOptimizerStore, referencePayload);
		String optiTwoStr = optiTwo.getTestRun().getResults().get(0).getTests().get(0).toString();
		
		assertFalse("Tickets must be filled", optiTwo.getTestRun().getResults().get(0).getTests().get(0).getTickets().isEmpty());
		assertFalse("The test toString should be different", optiOneStr.equals(optiTwoStr));
	}
	
	@Test
	@RoxableTest(key = "7d59cf7ca57b")
	public void payloadShouldBeCompleteWhenDataChanged() {
		Payload optiOne = (Payload) referencePayload.getOptimizer().optimize(cacheOptimizerStore, referencePayload);
		
		referencePayload.getTestRun().getResults().get(0).getTests().get(0).getData().put("new key", "new value");
		String optiOneStr = optiOne.getTestRun().getResults().get(0).getTests().get(0).toString();
		
		Payload optiTwo = (Payload) referencePayload.getOptimizer().optimize(cacheOptimizerStore, referencePayload);
		String optiTwoStr = optiTwo.getTestRun().getResults().get(0).getTests().get(0).toString();
		
		assertFalse("Data must be filled", optiTwo.getTestRun().getResults().get(0).getTests().get(0).getData().isEmpty());
		assertFalse("The test toString should be different", optiOneStr.equals(optiTwoStr));
	}
}
