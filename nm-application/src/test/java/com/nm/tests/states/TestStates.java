package com.nm.tests.states;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.StateMachineBuilder.Builder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.nm.app.states.StateMachineUtils;
import com.nm.app.states.StateMachineUtils.StateResult;
import com.nm.tests.ConfigurationTestApplication;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestApplication.class)
public class TestStates {

	//
	protected Log log = LogFactory.getLog(getClass());

	@Test
	@Transactional
	public void testShouldFindNext() throws Exception {
		Builder<String, Integer> builder = StateMachineBuilder.builder();
		builder.configureConfiguration().withConfiguration().autoStartup(true).taskExecutor(new SyncTaskExecutor());
		builder.configureStates().withStates().initial("S1").state("S2").state("S3").state("SF").state("S4");
		builder.configureTransitions().withExternal().source("S1").target("S2").event(1).and()//
				.withExternal().source("S2").target("S3").event(2).and()//
				.withExternal().source("S3").target("SF").event(3).and()//
				.withExternal().source("S3").target("S4").event(4);
		Collection<String> nexts = StateMachineUtils.findAllNextStates(builder.build(), "S2");
		Assert.assertTrue(nexts.contains("S3"));
		Assert.assertEquals(1, nexts.size());
		//
		nexts = StateMachineUtils.findAllNextStates(builder.build(), "S3");
		Assert.assertTrue(nexts.contains("SF"));
		Assert.assertTrue(nexts.contains("S4"));
		Assert.assertEquals(2, nexts.size());
		//
		nexts = StateMachineUtils.findAllNextStates(builder.build(), "S4");
		Assert.assertEquals(0, nexts.size());
		//
		nexts = StateMachineUtils.findAllNextStates(builder.build(), "SF");
		Assert.assertEquals(0, nexts.size());
	}

	@Test
	@Transactional
	public void testShouldFindNextRecursif() throws Exception {
		Builder<String, Integer> builder = StateMachineBuilder.builder();
		builder.configureConfiguration().withConfiguration().autoStartup(true).taskExecutor(new SyncTaskExecutor());
		builder.configureStates().withStates().initial("S1").state("S2").state("S3").state("SF").state("S4");
		builder.configureTransitions().withExternal().source("S1").target("S2").event(1).and()//
				.withExternal().source("S2").target("S3").event(2).and()//
				.withExternal().source("S3").target("S4").event(2).and()//
				.withExternal().source("S3").target("SF").event(3).and()//
				.withExternal().source("S3").target("S4").event(4);
		StateResult<String> nexts = StateMachineUtils.findAllNextStatesRecursive(builder.build(), "S2");
		Assert.assertTrue(nexts.getNear().contains("S3"));
		Assert.assertTrue(nexts.getFar().contains("S4"));
		Assert.assertEquals(2, nexts.size());
		//
		nexts = StateMachineUtils.findAllNextStatesRecursive(builder.build(), "S3");
		Assert.assertTrue(nexts.getNear().contains("SF"));
		Assert.assertTrue(nexts.getNear().contains("S4"));
		Assert.assertEquals(2, nexts.size());
		//
		nexts = StateMachineUtils.findAllNextStatesRecursive(builder.build(), "S4");
		Assert.assertEquals(0, nexts.size());
		//
		nexts = StateMachineUtils.findAllNextStatesRecursive(builder.build(), "SF");
		Assert.assertEquals(0, nexts.size());
	}

}
