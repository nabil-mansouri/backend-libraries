package com.nm.tests.plannings;

import java.text.SimpleDateFormat;

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
public class TestSlotSubstracter {
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
	public void testShouldSubstractOneSlot() throws Exception {
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
	public void testShouldExcludeInclusiveSlots() throws Exception {
		DtoSlotOccurrenceGroup group = new DtoSlotOccurrenceGroup();
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("01/09/2015")).setEnd(dFormat.parse("02/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		group.add(new DtoSlotOccurrence().setStart(dtFormat.parse("03/09/2015 12:00")).setEnd(dtFormat.parse("06/09/2015 12:30"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("04/09/2015")).setEnd(dFormat.parse("05/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Open));
		processor.process(group, context);
		Assert.assertEquals(2, group.size());
		Assert.assertEquals(dFormat.parse("01/09/2015"), group.get(0).getStart());
		Assert.assertEquals(dFormat.parse("02/09/2015"), group.get(0).getEnd());
		Assert.assertEquals(dtFormat.parse("03/09/2015 12:00"), group.get(1).getStart());
		Assert.assertEquals(dtFormat.parse("06/09/2015 12:30"), group.get(1).getEnd());
		Assert.assertEquals(SlotTypeDefault.Close, group.get(1).getType());
	}

	@Test
	@Transactional
	public void testShouldExcludeInclusiveInverseSlots() throws Exception {
		DtoSlotOccurrenceGroup group = new DtoSlotOccurrenceGroup();
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("01/09/2015")).setEnd(dFormat.parse("02/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		group.add(new DtoSlotOccurrence().setStart(dtFormat.parse("03/09/2015 12:00")).setEnd(dtFormat.parse("06/09/2015 12:00"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Open));
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("04/09/2015")).setEnd(dFormat.parse("05/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		processor.process(group, context);
		Assert.assertEquals(4, group.size());
		Assert.assertEquals(dtFormat.parse("03/09/2015 12:00"), group.get(1).getStart());
		Assert.assertEquals(dFormat.parse("04/09/2015"), group.get(1).getEnd());
		Assert.assertEquals(SlotTypeDefault.Open, group.get(1).getType());
		Assert.assertEquals(dFormat.parse("04/09/2015"), group.get(2).getStart());
		Assert.assertEquals(dFormat.parse("05/09/2015"), group.get(2).getEnd());
		Assert.assertEquals(SlotTypeDefault.Close, group.get(2).getType());
		Assert.assertEquals(dFormat.parse("05/09/2015"), group.get(3).getStart());
		Assert.assertEquals(dtFormat.parse("06/09/2015 12:00"), group.get(3).getEnd());
		Assert.assertEquals(SlotTypeDefault.Open, group.get(3).getType());
	}

	@Test
	@Transactional
	public void testShouldExcludeExclusiveSlots() throws Exception {
		DtoSlotOccurrenceGroup group = new DtoSlotOccurrenceGroup();
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("01/09/2015")).setEnd(dFormat.parse("02/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		group.add(new DtoSlotOccurrence().setStart(dtFormat.parse("03/09/2015 12:00")).setEnd(dtFormat.parse("06/09/2015 12:30"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("08/09/2015")).setEnd(dFormat.parse("10/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Open));
		processor.process(group, context);
		Assert.assertEquals(3, group.size());
		Assert.assertEquals(dFormat.parse("01/09/2015"), group.get(0).getStart());
		Assert.assertEquals(dFormat.parse("02/09/2015"), group.get(0).getEnd());
		Assert.assertEquals(dtFormat.parse("03/09/2015 12:00"), group.get(1).getStart());
		Assert.assertEquals(dtFormat.parse("06/09/2015 12:30"), group.get(1).getEnd());
		Assert.assertEquals(SlotTypeDefault.Close, group.get(1).getType());
		Assert.assertEquals(dFormat.parse("08/09/2015"), group.get(2).getStart());
		Assert.assertEquals(dFormat.parse("10/09/2015"), group.get(2).getEnd());
		Assert.assertEquals(SlotTypeDefault.Open, group.get(2).getType());
	}

	@Test
	@Transactional
	public void testShouldExcludeEqualsSlots() throws Exception {
		DtoSlotOccurrenceGroup group = new DtoSlotOccurrenceGroup();
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("01/09/2015")).setEnd(dFormat.parse("02/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		group.add(new DtoSlotOccurrence().setStart(dtFormat.parse("03/09/2015 12:00")).setEnd(dtFormat.parse("06/09/2015 12:00"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Open));
		group.add(new DtoSlotOccurrence().setStart(dtFormat.parse("03/09/2015 12:00")).setEnd(dtFormat.parse("06/09/2015 12:00"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		processor.process(group, context);
		Assert.assertEquals(2, group.size());
		Assert.assertEquals(dtFormat.parse("03/09/2015 12:00"), group.get(1).getStart());
		Assert.assertEquals(dtFormat.parse("06/09/2015 12:00"), group.get(1).getEnd());
		Assert.assertEquals(SlotTypeDefault.Close, group.get(1).getType());
	}

	@Test
	@Transactional
	public void testShouldExcludeOverlapsSlots() throws Exception {
		DtoSlotOccurrenceGroup group = new DtoSlotOccurrenceGroup();
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("01/09/2015")).setEnd(dFormat.parse("02/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		group.add(new DtoSlotOccurrence().setStart(dtFormat.parse("03/09/2015 12:00")).setEnd(dtFormat.parse("06/09/2015 12:00"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Open));
		group.add(new DtoSlotOccurrence().setStart(dtFormat.parse("06/09/2015 10:00")).setEnd(dFormat.parse("10/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		processor.process(group, context);
		Assert.assertEquals(3, group.size());
		Assert.assertEquals(dtFormat.parse("03/09/2015 12:00"), group.get(1).getStart());
		Assert.assertEquals(dtFormat.parse("06/09/2015 10:00"), group.get(1).getEnd());
		Assert.assertEquals(SlotTypeDefault.Open, group.get(1).getType());
		Assert.assertEquals(dtFormat.parse("06/09/2015 10:00"), group.get(2).getStart());
		Assert.assertEquals(dFormat.parse("10/09/2015"), group.get(2).getEnd());
		Assert.assertEquals(SlotTypeDefault.Close, group.get(2).getType());
	}

	@Test
	@Transactional
	public void testShouldExcludeAbutsSlots() throws Exception {
		DtoSlotOccurrenceGroup group = new DtoSlotOccurrenceGroup();
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("01/09/2015")).setEnd(dFormat.parse("02/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		group.add(new DtoSlotOccurrence().setStart(dtFormat.parse("03/09/2015 12:00")).setEnd(dtFormat.parse("06/09/2015 10:00"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Open));
		group.add(new DtoSlotOccurrence().setStart(dtFormat.parse("06/09/2015 10:00")).setEnd(dFormat.parse("10/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		processor.process(group, context);
		Assert.assertEquals(3, group.size());
		Assert.assertEquals(dtFormat.parse("03/09/2015 12:00"), group.get(1).getStart());
		Assert.assertEquals(dtFormat.parse("06/09/2015 10:00"), group.get(1).getEnd());
		Assert.assertEquals(SlotTypeDefault.Open, group.get(1).getType());
		Assert.assertEquals(dtFormat.parse("06/09/2015 10:00"), group.get(2).getStart());
		Assert.assertEquals(dFormat.parse("10/09/2015"), group.get(2).getEnd());
		Assert.assertEquals(SlotTypeDefault.Close, group.get(2).getType());
	}

	@Test
	@Transactional
	public void testShouldOptimizeAndOverlapsSlots() throws Exception {
		DtoSlotOccurrenceGroup group = new DtoSlotOccurrenceGroup();
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("01/09/2015")).setEnd(dFormat.parse("04/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		group.add(new DtoSlotOccurrence().setStart(dtFormat.parse("03/09/2015 12:00")).setEnd(dtFormat.parse("08/09/2015 12:00"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		group.add(new DtoSlotOccurrence().setStart(dtFormat.parse("06/09/2015 10:00")).setEnd(dFormat.parse("10/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Open));
		processor.process(group, context);
		Assert.assertEquals(2, group.size());
		Assert.assertEquals(dtFormat.parse("01/09/2015 00:00"), group.get(0).getStart());
		Assert.assertEquals(dtFormat.parse("08/09/2015 12:00"), group.get(0).getEnd());
		Assert.assertEquals(SlotTypeDefault.Close, group.get(0).getType());
		Assert.assertEquals(dtFormat.parse("08/09/2015 12:00"), group.get(1).getStart());
		Assert.assertEquals(dFormat.parse("10/09/2015"), group.get(1).getEnd());
		Assert.assertEquals(SlotTypeDefault.Open, group.get(1).getType());
	}

	@Test
	@Transactional
	public void testShouldOptimizeAndOverlapsInverseSlots() throws Exception {
		DtoSlotOccurrenceGroup group = new DtoSlotOccurrenceGroup();
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("01/09/2015")).setEnd(dFormat.parse("04/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Open));
		group.add(new DtoSlotOccurrence().setStart(dtFormat.parse("03/09/2015 12:00")).setEnd(dtFormat.parse("08/09/2015 12:00"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Open));
		group.add(new DtoSlotOccurrence().setStart(dtFormat.parse("06/09/2015 10:00")).setEnd(dFormat.parse("10/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close));
		processor.process(group, context);
		for (DtoSlotOccurrence ev : group) {
			System.out.println(ev);
		}
		Assert.assertEquals(2, group.size());
		Assert.assertEquals(dtFormat.parse("01/09/2015 00:00"), group.get(0).getStart());
		Assert.assertEquals(dtFormat.parse("06/09/2015 10:00"), group.get(0).getEnd());
		Assert.assertEquals(SlotTypeDefault.Open, group.get(0).getType());
		Assert.assertEquals(dtFormat.parse("06/09/2015 10:00"), group.get(1).getStart());
		Assert.assertEquals(dFormat.parse("10/09/2015"), group.get(1).getEnd());
		Assert.assertEquals(SlotTypeDefault.Close, group.get(1).getType());
	}

	@Test
	@Transactional
	public void testShouldMergeIdsIfOptimized() throws Exception {
		DtoSlotOccurrenceGroup group = new DtoSlotOccurrenceGroup();
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("01/09/2015")).setEnd(dFormat.parse("04/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Open).setId("1"));
		group.add(new DtoSlotOccurrence().setStart(dtFormat.parse("03/09/2015 12:00")).setEnd(dtFormat.parse("08/09/2015 12:00"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Open).setId("2"));
		group.add(new DtoSlotOccurrence().setStart(dtFormat.parse("06/09/2015 10:00")).setEnd(dFormat.parse("10/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close).setId("3"));
		processor.process(group, context);
		Assert.assertEquals(2, group.size());
		Assert.assertTrue(group.get(0).getId().equals("1"));
		Assert.assertTrue(group.get(0).getIdsMerged().contains("2"));
	}

	@Test
	@Transactional
	public void testShouldCloneIdsIfExcluded() throws Exception {
		DtoSlotOccurrenceGroup group = new DtoSlotOccurrenceGroup();
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("01/09/2015")).setEnd(dFormat.parse("02/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close).setId("1"));
		group.add(new DtoSlotOccurrence().setStart(dtFormat.parse("03/09/2015 12:00")).setEnd(dtFormat.parse("06/09/2015 12:00"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Open).setId("2"));
		group.add(new DtoSlotOccurrence().setStart(dFormat.parse("04/09/2015")).setEnd(dFormat.parse("05/09/2015"))
				.setEventType(SlotRepeatKind.Recurrent).setType(SlotTypeDefault.Close).setId("3"));
		processor.process(group, context);
		for (DtoSlotOccurrence e : group) {
			System.out.println(e);
		}
		Assert.assertEquals(4, group.size());
		Assert.assertTrue(group.get(0).getId().equals("1"));
		Assert.assertTrue(group.get(0).getIdsMerged().isEmpty());
		Assert.assertTrue(group.get(1).getId().equals("2"));
		Assert.assertTrue(group.get(1).getIdsMerged().isEmpty());
		Assert.assertTrue(group.get(2).getId().equals("3"));
		Assert.assertTrue(group.get(2).getIdsMerged().isEmpty());
		Assert.assertTrue(group.get(3).getId().equals("2"));
		Assert.assertTrue(group.get(3).getIdsMerged().isEmpty());
	}
}
