package com.nm.tests.plannings;

import java.text.SimpleDateFormat;
import java.util.Arrays;
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
import com.nm.plannings.converters.impl.EventDtoConverterExceptionnal;
import com.nm.plannings.converters.impl.EventDtoConverterRecurrent;
import com.nm.plannings.dtos.DtoSlotOccurrence;
import com.nm.plannings.model.TimeSlot;
import com.nm.plannings.model.TimeSlotExceptionnal;
import com.nm.plannings.model.TimeSlotRecurrent;
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
public class TestSlotConverter {
	//
	private EventDtoConverterExceptionnal excConverter = new EventDtoConverterExceptionnal();
	private EventDtoConverterRecurrent recConverter = new EventDtoConverterRecurrent(new DaySplitterStrategyDefault());
	private EventDtoConverterRecurrent recConverterKeep = new EventDtoConverterRecurrent(new DaySplitterStrategyKeep());
	//
	private SimpleDateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private SimpleDateFormat tFormat = new SimpleDateFormat("HH:mm");
	private TimeSlot slotRecurrent, slotExce;

	@Autowired
	public void setAppliContent(ConfigurableApplicationContext applicationContext) {
	}

	//
	@Before
	public void setUp() throws Exception {
		slotRecurrent = new TimeSlotRecurrent().setBeginHoraire(tFormat.parse("12:00"))
				.setEndHoraire(tFormat.parse("14:00")).setDays(PlanningDays.AllDays).setNoEndPlan(true)
				.setBeginPlan(dtFormat.parse("01/09/2015 00:00")).setEndPlan(dtFormat.parse("04/09/2015 23:00"))
				.setType(SlotTypeDefault.Open);
		//
		slotExce = new TimeSlotExceptionnal().setBeginPlan(dtFormat.parse("01/09/2015 00:00"))
				.setEndPlan(dtFormat.parse("04/09/2015 23:00")).setType(SlotTypeDefault.Open);
	}

	@Test
	public void testShouldConvertExceptionnal() throws Exception {
		List<DtoSlotOccurrence> events = excConverter.toEvents(Arrays.asList(slotExce), dtFormat.parse("01/09/2015 00:00"),
				dtFormat.parse("04/09/2015 23:00"));
		Assert.assertEquals(1, events.size());
		Assert.assertEquals(dtFormat.parse("01/09/2015 00:00"), events.get(0).getStart());
		Assert.assertEquals(dtFormat.parse("04/09/2015 23:00"), events.get(0).getEnd());
	}

	@Test
	public void testShouldConvertRecurrentSplitDefault() throws Exception {
		//
		List<DtoSlotOccurrence> events = recConverter.toEvents(Arrays.asList(slotRecurrent), dtFormat.parse("01/09/2015 00:00"),
				dtFormat.parse("04/09/2015 23:00"));
		Assert.assertEquals(4, events.size());
	}

	@Test
	public void testShouldConvertRecurrentSplitKeep() throws Exception {
		//
		List<DtoSlotOccurrence> events = recConverterKeep.toEvents(Arrays.asList(slotRecurrent),
				dtFormat.parse("01/09/2015 00:00"), dtFormat.parse("04/09/2015 23:00"));
		Assert.assertEquals(1, events.size());
	}

	@Test(expected = ClassCastException.class)
	public void testShouldNotConvertRecurrent() throws Exception {
		excConverter.toEvents(Arrays.asList(slotRecurrent), dtFormat.parse("01/09/2015 00:00"),
				dtFormat.parse("04/09/2015 23:00"));
	}
}
