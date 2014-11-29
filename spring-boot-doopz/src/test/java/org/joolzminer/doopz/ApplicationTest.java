package org.joolzminer.doopz;

import org.joolzminer.doopz.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ApplicationTest {
	
	@Autowired
	private ApplicationContext applicationContext;	
	
	@Test
	public void testContextLoads() {
		assertThat(applicationContext, is(notNullValue()));
	}	
	
	@Test
	public void testBeansDefined() {
		assertThat(applicationContext.containsBean("duplicatesSeekerService"), is(true));
		assertThat(applicationContext.containsBean("fileMapBuilderService"), is(true));
		assertThat(applicationContext.containsBean("fileRetrieverService"), is(true));
	}
}
