package com.nm.tests.plannings;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.nm.plannings.constants.SlotRepeatKind;
import com.nm.plannings.constants.SlotTypeDefault;
import com.nm.plannings.dtos.DtoSlotOccurrence;
import com.nm.plannings.dtos.DtoSlotOccurrenceGroup;
import com.nm.plannings.rules.EventRuleContextDefault;
import com.nm.plannings.rules.EventRulesContext;
import com.nm.plannings.rules.EventRulesProcessor;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestPlanning.class)
public class TestSlotOptimizer {
	@Autowired
	private EventRulesProcessor processor;
	//
	protected SimpleDateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	protected SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
	protected SimpleDateFormat tFormat = new SimpleDateFormat("HH:mm");
	private EventRulesContext context;
	//

	@Autowired
	public void setAppliContent(ConfigurableApplicationContext applicationContext) {
	}

	//
	@Before
	public void setUp() throws Exception {
		context = new EventRuleContextDefault();
	}

	@Test
	@Transactional
	public void testShouldClearEmpty() throws Exception {
		DtoSlotOccurrenceGroup group = new DtoSlotOccurrenceGroup();
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("01/09/2015")).setEnd(dFormat.parse("02/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("03/09/2015")).setEnd(dFormat.parse("03/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("04/09/2015")).setEnd(dFormat.parse("04/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("05/09/2015")).setEnd(dFormat.parse("06/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		processor.process(group, context);
		Assert.assertEquals(2, group.size());
		for (DtoSlotOccurrence e : group) {
			Date d1 = dFormat.parse("01/09/2015");
			Date d2 = dFormat.parse("05/09/2015");
			Assert.assertTrue(e.getStart().equals(d1) || e.getStart().equals(d2));
		}
	}

	@Test
	@Transactional
	public void testShouldOptimizeOneSlot() throws Exception {
		DtoSlotOccurrenceGroup group = new DtoSlotOccurrenceGroup();
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("01/09/2015")).setEnd(dFormat.parse("02/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		processor.process(group, context);
		Assert.assertEquals(1, group.size());
		Assert.assertEquals(dFormat.parse("01/09/2015"), group.iterator().next().getStart());
		Assert.assertEquals(dFormat.parse("02/09/2015"), group.iterator().next().getEnd());
		Assert.assertEquals(SlotTypeDefault.Close, group.iterator().next().getType());
	}

	@Test
	@Transactional
	public void testShouldOptimizeInclusiveSlots() throws Exception {
		DtoSlotOccurrenceGroup group = new DtoSlotOccurrenceGroup();
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("01/09/2015")).setEnd(dFormat.parse("02/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("03/09/2015")).setEnd(dFormat.parse("06/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("04/09/2015")).setEnd(dFormat.parse("05/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		processor.process(group, context);
		Assert.assertEquals(2, group.size());
		Assert.assertEquals(dFormat.parse("01/09/2015"), group.get(0).getStart());
		Assert.assertEquals(dFormat.parse("02/09/2015"), group.get(0).getEnd());
		Assert.assertEquals(dFormat.parse("03/09/2015"), group.get(1).getStart());
		Assert.assertEquals(dFormat.parse("06/09/2015"), group.get(1).getEnd());
		Assert.assertEquals(SlotTypeDefault.Close, group.get(1).getType());
	}

	@Test
	@Transactional
	public void testShouldOptimizeOverlapsSlots() throws Exception {
		DtoSlotOccurrenceGroup group = new DtoSlotOccurrenceGroup();
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("01/09/2015")).setEnd(dFormat.parse("02/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("03/09/2015")).setEnd(dFormat.parse("06/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("04/09/2015")).setEnd(dFormat.parse("08/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		processor.process(group, context);
		Assert.assertEquals(2, group.size());
		Assert.assertEquals(dFormat.parse("01/09/2015"), group.get(0).getStart());
		Assert.assertEquals(dFormat.parse("02/09/2015"), group.get(0).getEnd());
		Assert.assertEquals(dFormat.parse("03/09/2015"), group.get(1).getStart());
		Assert.assertEquals(dFormat.parse("08/09/2015"), group.get(1).getEnd());
		Assert.assertEquals(SlotTypeDefault.Close, group.get(1).getType());
	}

	@Test
	@Transactional
	public void testShouldOptimizeAbutsSlots() throws Exception {
		DtoSlotOccurrenceGroup group = new DtoSlotOccurrenceGroup();
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("01/09/2015")).setEnd(dFormat.parse("02/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("03/09/2015")).setEnd(dtFormat.parse("04/09/2015 12:30"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		group.add(new DtoSlotOccurrence().setStart(dtFormat.parse("04/09/2015 12:30")).setEnd(dFormat.parse("08/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		processor.process(group, context);
		Assert.assertEquals(2, group.size());
		Assert.assertEquals(dFormat.parse("01/09/2015"), group.get(0).getStart());
		Assert.assertEquals(dFormat.parse("02/09/2015"), group.get(0).getEnd());
		Assert.assertEquals(dFormat.parse("03/09/2015"), group.get(1).getStart());
		Assert.assertEquals(dFormat.parse("08/09/2015"), group.get(1).getEnd());
		Assert.assertEquals(SlotTypeDefault.Close, group.get(1).getType());
	}

	@Test
	@Transactional
	public void testShouldOptimizeEqualsSlots() throws Exception {
		DtoSlotOccurrenceGroup group = new DtoSlotOccurrenceGroup();
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("01/09/2015")).setEnd(dFormat.parse("02/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("03/09/2015")).setEnd(dtFormat.parse("04/09/2015 12:30"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		group.add(new DtoSlotOccurrence().setStart(dtFormat.parse("03/09/2015 00:00")).setEnd(dtFormat.parse("04/09/2015 12:30"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		processor.process(group, context);
		Assert.assertEquals(2, group.size());
		Assert.assertEquals(dFormat.parse("01/09/2015"), group.get(0).getStart());
		Assert.assertEquals(dFormat.parse("02/09/2015"), group.get(0).getEnd());
		Assert.assertEquals(dFormat.parse("03/09/2015"), group.get(1).getStart());
		Assert.assertEquals(dtFormat.parse("04/09/2015 12:30"), group.get(1).getEnd());
		Assert.assertEquals(SlotTypeDefault.Close, group.get(1).getType());
	}

	@Test
	@Transactional
	public void testShouldNotOptimizeExclusiveSlots() throws Exception {
		DtoSlotOccurrenceGroup group = new DtoSlotOccurrenceGroup();
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("01/09/2015")).setEnd(dFormat.parse("02/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("03/09/2015")).setEnd(dtFormat.parse("04/09/2015 12:30"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		group.add(new DtoSlotOccurrence().setStart(dtFormat.parse("04/09/2015 12:31")).setEnd(dFormat.parse("08/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		processor.process(group, context);
		Assert.assertEquals(3, group.size());
	}

	@Test
	@Transactional
	public void testShouldNotOptimizeAbutsSlotsOfDifferentType() throws Exception {
		DtoSlotOccurrenceGroup group = new DtoSlotOccurrenceGroup();
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("01/09/2015")).setEnd(dFormat.parse("02/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("03/09/2015")).setEnd(dtFormat.parse("04/09/2015 00:00"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("04/09/2015")).setEnd(dtFormat.parse("05/09/2015 12:30"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Open));
		processor.process(group, context);
		Assert.assertEquals(3, group.size());
	}
}
