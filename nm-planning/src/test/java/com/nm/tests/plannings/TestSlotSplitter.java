package com.nm.tests.plannings;

import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nm.plannings.constants.PlanningDays;
import com.nm.plannings.constants.SlotTypeDefault;
import com.nm.plannings.dtos.DtoSlotOccurrence;
import com.nm.plannings.model.TimeSlot;
import com.nm.plannings.model.TimeSlotRecurrent;
import com.nm.plannings.splitters.DaySplitterStrategy;
import com.nm.plannings.splitters.impl.DaySplitterStrategyDefault;
import com.nm.plannings.splitters.impl.DaySplitterStrategyKeep;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestPlanning.class)
public class TestSlotSplitter {
	//
	private DaySplitterStrategy defaultStrategy = new DaySplitterStrategyDefault();
	private DaySplitterStrategy keepStrtegy = new DaySplitterStrategyKeep();
	private SimpleDateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private SimpleDateFormat dFormat = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat tFormat = new SimpleDateFormat("HH:mm");

	@Autowired
	public void setAppliContent(ConfigurableApplicationContext applicationContext) {
	}

	//
	@Before
	public void setUp() {

	}

	@Test
	public void testShouldSplitDefaultAllDays() throws Exception {
		TimeSlot slot = new TimeSlotRecurrent().setBeginHoraire(tFormat.parse("12:00")).setDays(PlanningDays.AllDays)
				.setEndHoraire(tFormat.parse("14:00")).setNoEndPlan(true).setBeginPlan(dFormat.parse("01/09/2015"))
				.setEndPlan(dtFormat.parse("09/09/2015 23:00")).setType(SlotTypeDefault.Open);
		//
		List<DtoSlotOccurrence> events = defaultStrategy.convert(slot, dFormat.parse("01/09/2015"),
				dtFormat.parse("09/09/2015 23:00"));
		Assert.assertEquals(9, events.size());
		for (int i = 0; i < 9; i++) {
			Assert.assertEquals(dtFormat.parse(String.format("0%s/09/2015 12:00", i + 1)), events.get(i).getStart());
			Assert.assertEquals(dtFormat.parse(String.format("0%s/09/2015 14:00", i + 1)), events.get(i).getEnd());
		}
	}

	@Test
	public void testShouldSplitDefaultAllDaysWithPlanIncludeInRange() throws Exception {
		TimeSlot slot = new TimeSlotRecurrent().setBeginHoraire(tFormat.parse("12:00")).setDays(PlanningDays.AllDays)
				.setEndHoraire(tFormat.parse("14:00")).setNoEndPlan(true)
				.setBeginPlan(dtFormat.parse("01/09/2015 14:30")).setEndPlan(dtFormat.parse("09/09/2015 00:00"))
				.setType(SlotTypeDefault.Open);
		//
		List<DtoSlotOccurrence> events = defaultStrategy.convert(slot, dFormat.parse("01/09/2015"),
				dtFormat.parse("09/09/2015 23:00"));
		Assert.assertEquals(7, events.size());
		for (int i = 0; i < 7; i++) {
			Assert.assertEquals(dtFormat.parse(String.format("0%s/09/2015 12:00", i + 2)), events.get(i).getStart());
			Assert.assertEquals(dtFormat.parse(String.format("0%s/09/2015 14:00", i + 2)), events.get(i).getEnd());
		}
	}

	@Test
	public void testShouldSplitDefaultAllDaysWithRangeInPlan() throws Exception {
		TimeSlot slot = new TimeSlotRecurrent().setBeginHoraire(tFormat.parse("12:00")).setDays(PlanningDays.AllDays)
				.setEndHoraire(tFormat.parse("14:00")).setNoEndPlan(true)
				.setBeginPlan(dtFormat.parse("01/09/2015 00:00")).setEndPlan(dtFormat.parse("09/09/2015 23:00"))
				.setType(SlotTypeDefault.Open);
		//
		List<DtoSlotOccurrence> events = defaultStrategy.convert(slot, dtFormat.parse("01/09/2015 15:00"),
				dtFormat.parse("09/09/2015 00:00"));
		Assert.assertEquals(7, events.size());
		for (int i = 0; i < 7; i++) {
			Assert.assertEquals(dtFormat.parse(String.format("0%s/09/2015 12:00", i + 2)), events.get(i).getStart());
			Assert.assertEquals(dtFormat.parse(String.format("0%s/09/2015 14:00", i + 2)), events.get(i).getEnd());
		}
	}

	@Test
	public void testShouldSplitDefaultAllDaysWithRangeOverlapsPlan() throws Exception {
		TimeSlot slot = new TimeSlotRecurrent().setBeginHoraire(tFormat.parse("12:00")).setDays(PlanningDays.AllDays)
				.setEndHoraire(tFormat.parse("14:00")).setNoEndPlan(true)
				.setBeginPlan(dtFormat.parse("01/09/2015 00:00")).setEndPlan(dtFormat.parse("04/09/2015 23:00"))
				.setType(SlotTypeDefault.Open);
		//
		List<DtoSlotOccurrence> events = defaultStrategy.convert(slot, dtFormat.parse("03/09/2015 00:00"),
				dtFormat.parse("09/09/2015 00:00"));
		Assert.assertEquals(2, events.size());
		for (int i = 0; i < 2; i++) {
			Assert.assertEquals(dtFormat.parse(String.format("0%s/09/2015 12:00", i + 3)), events.get(i).getStart());
			Assert.assertEquals(dtFormat.parse(String.format("0%s/09/2015 14:00", i + 3)), events.get(i).getEnd());
		}
	}

	@Test
	public void testShouldSplitDefaultAllDaysWithRangeExclusivePlan() throws Exception {
		TimeSlot slot = new TimeSlotRecurrent().setBeginHoraire(tFormat.parse("12:00")).setDays(PlanningDays.AllDays)
				.setEndHoraire(tFormat.parse("14:00")).setNoEndPlan(true)
				.setBeginPlan(dtFormat.parse("01/09/2015 00:00")).setEndPlan(dtFormat.parse("04/09/2015 23:00"))
				.setType(SlotTypeDefault.Open);
		//
		List<DtoSlotOccurrence> events = defaultStrategy.convert(slot, dtFormat.parse("04/09/2015 23:00"),
				dtFormat.parse("09/09/2015 00:00"));
		Assert.assertEquals(0, events.size());
	}

	@Test
	public void testShouldSplitDefaultAllDaysWithEmptyPlan() throws Exception {
		TimeSlot slot = new TimeSlotRecurrent().setBeginHoraire(tFormat.parse("12:00")).setDays(PlanningDays.AllDays)
				.setEndHoraire(tFormat.parse("14:00")).setNoEndPlan(true)
				.setBeginPlan(dtFormat.parse("01/09/2015 00:00")).setEndPlan(dtFormat.parse("01/09/2015 00:00"))
				.setType(SlotTypeDefault.Open);
		//
		List<DtoSlotOccurrence> events = defaultStrategy.convert(slot, dtFormat.parse("01/09/2015 00:00"),
				dtFormat.parse("09/09/2015 00:00"));
		Assert.assertEquals(0, events.size());
	}

	@Test
	public void testShouldSplitDefaultAllDaysWithEmptyRange() throws Exception {
		TimeSlot slot = new TimeSlotRecurrent().setBeginHoraire(tFormat.parse("12:00")).setDays(PlanningDays.AllDays)
				.setEndHoraire(tFormat.parse("14:00")).setNoEndPlan(true)
				.setBeginPlan(dtFormat.parse("01/09/2015 00:00")).setEndPlan(dtFormat.parse("09/09/2015 00:00"))
				.setType(SlotTypeDefault.Open);
		//
		List<DtoSlotOccurrence> events = defaultStrategy.convert(slot, dtFormat.parse("01/09/2015 00:00"),
				dtFormat.parse("01/09/2015 00:00"));
		Assert.assertEquals(0, events.size());
	}

	@Test
	public void testShouldSplitKeepAllDays() throws Exception {
		TimeSlot slot = new TimeSlotRecurrent().setBeginHoraire(tFormat.parse("12:00")).setDays(PlanningDays.AllDays)
				.setEndHoraire(tFormat.parse("14:00")).setNoEndPlan(true).setBeginPlan(dFormat.parse("01/09/2015"))
				.setEndPlan(dtFormat.parse("09/09/2015 23:00")).setType(SlotTypeDefault.Open);
		//
		List<DtoSlotOccurrence> events = keepStrtegy.convert(slot, dFormat.parse("01/09/2015"),
				dtFormat.parse("09/09/2015 23:00"));
		Assert.assertEquals(1, events.size());
		Assert.assertEquals(dtFormat.parse("01/09/2015 12:00"), events.get(0).getStart());
		Assert.assertEquals(dtFormat.parse("09/09/2015 14:00"), events.get(0).getEnd());
	}

	@Test
	public void testShouldSplitKeepAllDaysWithPlanIncludeInRange() throws Exception {
		TimeSlot slot = new TimeSlotRecurrent().setBeginHoraire(tFormat.parse("12:00")).setDays(PlanningDays.AllDays)
				.setEndHoraire(tFormat.parse("14:00")).setNoEndPlan(true)
				.setBeginPlan(dtFormat.parse("01/09/2015 14:30")).setEndPlan(dtFormat.parse("09/09/2015 00:00"))
				.setType(SlotTypeDefault.Open);
		//
		List<DtoSlotOccurrence> events = keepStrtegy.convert(slot, dFormat.parse("01/09/2015"),
				dtFormat.parse("09/09/2015 23:00"));
		Assert.assertEquals(1, events.size());
		Assert.assertEquals(dtFormat.parse("02/09/2015 12:00"), events.get(0).getStart());
		Assert.assertEquals(dtFormat.parse("08/09/2015 14:00"), events.get(0).getEnd());
	}

	@Test
	public void testShouldSplitKeepAllDaysWithRangeInPlan() throws Exception {
		TimeSlot slot = new TimeSlotRecurrent().setBeginHoraire(tFormat.parse("12:00")).setDays(PlanningDays.AllDays)
				.setEndHoraire(tFormat.parse("14:00")).setNoEndPlan(true)
				.setBeginPlan(dtFormat.parse("01/09/2015 00:00")).setEndPlan(dtFormat.parse("09/09/2015 23:00"))
				.setType(SlotTypeDefault.Open);
		//
		List<DtoSlotOccurrence> events = keepStrtegy.convert(slot, dtFormat.parse("01/09/2015 15:00"),
				dtFormat.parse("09/09/2015 00:00"));
		Assert.assertEquals(1, events.size());
		Assert.assertEquals(dtFormat.parse("02/09/2015 12:00"), events.get(0).getStart());
		Assert.assertEquals(dtFormat.parse("08/09/2015 14:00"), events.get(0).getEnd());
	}

	@Test
	public void testShouldSplitKeepAllDaysWithRangeOverlapsPlan() throws Exception {
		TimeSlot slot = new TimeSlotRecurrent().setBeginHoraire(tFormat.parse("12:00")).setDays(PlanningDays.AllDays)
				.setEndHoraire(tFormat.parse("14:00")).setNoEndPlan(true)
				.setBeginPlan(dtFormat.parse("01/09/2015 00:00")).setEndPlan(dtFormat.parse("04/09/2015 23:00"))
				.setType(SlotTypeDefault.Open);
		//
		List<DtoSlotOccurrence> events = keepStrtegy.convert(slot, dtFormat.parse("03/09/2015 00:00"),
				dtFormat.parse("09/09/2015 00:00"));
		Assert.assertEquals(1, events.size());
		Assert.assertEquals(dtFormat.parse("03/09/2015 12:00"), events.get(0).getStart());
		Assert.assertEquals(dtFormat.parse("04/09/2015 14:00"), events.get(0).getEnd());
	}

	@Test
	public void testShouldSplitKeepAllDaysWithRangeExclusivePlan() throws Exception {
		TimeSlot slot = new TimeSlotRecurrent().setBeginHoraire(tFormat.parse("12:00")).setDays(PlanningDays.AllDays)
				.setEndHoraire(tFormat.parse("14:00")).setNoEndPlan(true)
				.setBeginPlan(dtFormat.parse("01/09/2015 00:00")).setEndPlan(dtFormat.parse("04/09/2015 23:00"))
				.setType(SlotTypeDefault.Open);
		//
		List<DtoSlotOccurrence> events = keepStrtegy.convert(slot, dtFormat.parse("04/09/2015 23:00"),
				dtFormat.parse("09/09/2015 00:00"));
		Assert.assertEquals(0, events.size());
	}
}
