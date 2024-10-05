package com.nm.tests.plannings;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.nm.app.planning.DtoNodeMerge;
import com.nm.app.planning.MergeProcessor;
import com.nm.utils.dates.DateUtilsExt;

/**
 * 
 * @author Nabil
 * 
 */
public class TestMerger {

	//
	protected Log log = LogFactory.getLog(getClass());

	@Test
	public void testShouldMergeAbuts() throws Exception {
		MergeProcessor processor = new MergeProcessor();
		Collection<DtoNodeMerge> all = Lists.newArrayList();
		all.add(new MergeNodeDtoTest("COSTA/Nadia/0970").setStart(DateUtilsExt.from(1, 12, 2015))
				.setEnd(DateUtilsExt.from(31, 12, 2015)));
		all.add(new MergeNodeDtoTest("COSTA/Nadia/0970").setStart(DateUtilsExt.from(1, 1, 2016))
				.setEnd(DateUtilsExt.from(17, 1, 2016)));
		processor.process(all);
		System.out.println(all);
		Assert.assertEquals(1, all.size());
	}

	@Test
	public void testShouldNotMergeForAbuts() throws Exception {
		MergeProcessor processor = new MergeProcessor();
		Collection<DtoNodeMerge> all = Lists.newArrayList();
		all.add(new MergeNodeDtoTest("COSTA/Nadia/0970").setStart(DateUtilsExt.from(1, 12, 2015))
				.setEnd(DateUtilsExt.from(31, 12, 2015)));
		all.add(new MergeNodeDtoTest("COSTA/Nadia/0970").setStart(DateUtilsExt.from(2, 1, 2016))
				.setEnd(DateUtilsExt.from(17, 1, 2016)));
		processor.process(all);
		System.out.println(all);
		Assert.assertEquals(2, all.size());
	}

	@Test
	public void testShouldNotMergeForType() throws Exception {
		MergeProcessor processor = new MergeProcessor();
		Collection<DtoNodeMerge> all = Lists.newArrayList();
		all.add(new MergeNodeDtoTest("COSTA/Nadia/0970").setStart(DateUtilsExt.from(1, 12, 2015))
				.setEnd(DateUtilsExt.from(31, 12, 2015)));
		all.add(new MergeNodeDtoTest("COSTA/Nadia/0971").setStart(DateUtilsExt.from(1, 1, 2016))
				.setEnd(DateUtilsExt.from(17, 1, 2016)));
		processor.process(all);
		System.out.println(all);
		Assert.assertEquals(2, all.size());
	}

}
