package com.nm.tests.plannings;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.hibernate.criterion.DetachedCriteria;
import org.joda.time.MutableDateTime;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nm.app.locale.LocaleFormDto;
import com.nm.app.locale.SoaLocale;
import com.nm.plannings.constants.SlotRepeatKind;
import com.nm.plannings.constants.PlanningDays;
import com.nm.plannings.constants.SlotTypeDefault;
import com.nm.plannings.dao.DaoTimeSlot;
import com.nm.plannings.dao.impl.TimeSlotQueryBuilder;
import com.nm.plannings.dtos.DtoSlotOccurrence;
import com.nm.plannings.dtos.DtoPlanning;
import com.nm.plannings.dtos.DtoPlanningQuery;
import com.nm.plannings.dtos.DtoPlanningQuery.OperationType;
import com.nm.plannings.dtos.DtoPlanningResult;
import com.nm.plannings.dtos.DtoSlotFilter;
import com.nm.plannings.dtos.DtoTimeSlot;
import com.nm.plannings.model.Planning;
import com.nm.plannings.model.TimeSlot;
import com.nm.plannings.model.TimeSlotRecurrent;
import com.nm.plannings.soa.SoaPlanning;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;
import com.nm.utils.node.DaoModelNode;
import com.nm.utils.node.DaoModelNodeImpl;
import com.nm.utils.node.DtoNode;
import com.nm.utils.node.ModelNode;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestPlanning.class)
public class TestCrudPlanning {
	@Autowired
	private SoaPlanning soaPlanning;
	private IGenericDao<Planning, Long> daoPlanning;
	@Autowired
	private SoaLocale soaLocale;
	@Autowired
	private DaoTimeSlot daoTimeSlot;
	//
	protected SimpleDateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	protected SimpleDateFormat tFormat = new SimpleDateFormat("HH:mm");
	private ObjectMapper mapper = new ObjectMapper();
	private DtoNode dtoN = new DtoNode();
	private OptionsList options = new OptionsList();
	//
	private Planning planning;
	final Long EXT = 1l;

	@Autowired
	public void setAppliContent(ConfigurableApplicationContext applicationContext) {
	}

	//
	@Before
	public void setUp() throws Exception {
		DaoModelNode dao = new DaoModelNodeImpl(AbstractGenericDao.get(ModelNode.class).getHibernateTemplate());
		ModelNode node = new ModelNode();
		node.setId(new BigInteger("1"));
		node.setType(dao.types(Planning.class));
		dao.save(node);
		dtoN.setUuid(node.getId());
		//
		daoPlanning = AbstractGenericDao.get(Planning.class);
		soaLocale.setSelectedLocales(Arrays.asList(new LocaleFormDto("fr"), new LocaleFormDto("en")));
		soaLocale.setDefaultLocale(new LocaleFormDto("fr"));
		planning = daoPlanning.get(soaPlanning.saveOrUpdate(new DtoPlanning().setAbout(dtoN), options).getId());
	}

	@Test
	@Transactional
	public void testShouldCreatePlanning() throws Exception {
		Assert.assertNotNull(planning.getId());
		daoPlanning.flush();
	}

	@Test
	@Transactional
	public void testShouldCreatePlanningOnlyOneTime() throws Exception {
		Long id = planning.getId();
		daoPlanning.flush();
		Planning planning = soaPlanning.saveOrUpdate(new DtoPlanning().setAbout(dtoN), options);
		planning = soaPlanning.saveOrUpdate(new DtoPlanning().setAbout(dtoN), options);
		Assert.assertEquals(id, planning.getId());
		daoPlanning.flush();
	}

	@Test
	@Transactional
	public void testShouldSaveSlotRecurrent() throws Exception {
		DtoTimeSlot form = new DtoTimeSlot().setDateBeginHoraire(tFormat.parse("10:00"))
				.setDateBeginPlan(dtFormat.parse("01/09/2015 00:00")).setDateEndHoraire(tFormat.parse("12:00"))
				.setDateEndPlan(dtFormat.parse("09/09/2015 00:00")).withPlanningDays(PlanningDays.Friday)
				.setType(SlotRepeatKind.Recurrent).setTypeOfSlot(SlotTypeDefault.Open);
		soaPlanning.saveOrUpdateSlot(new DtoPlanning().setId(planning.getId()), form, options);
		daoPlanning.flush();
		daoPlanning.refresh(planning);
		assertEquals(1, planning.getSlots().size());
		daoPlanning.flush();
	}

	@Test
	@Transactional
	public void testShouldEditSlotRecurrentAllDays() throws Exception {
		DtoTimeSlot form = new DtoTimeSlot().setDateBeginHoraire(tFormat.parse("10:00"))
				.setDateBeginPlan(dtFormat.parse("01/09/2015 00:00")).setDateEndHoraire(tFormat.parse("12:00"))
				.setDateEndPlan(dtFormat.parse("09/09/2015 00:00")).withPlanningDays(PlanningDays.AllDays)
				.withPlanningDays(PlanningDays.Friday).setType(SlotRepeatKind.Recurrent).setTypeOfSlot(SlotTypeDefault.Open);
		soaPlanning.saveOrUpdateSlot(new DtoPlanning(planning.getId()), form, options);
		daoPlanning.flush();
		daoPlanning.refresh(planning);
		form = soaPlanning
				.fetch(TimeSlotQueryBuilder.get().withId(planning.getSlots().iterator().next().getId()), options)
				.iterator().next();
		Assert.assertEquals(dtFormat.parse("01/09/2015 00:00"), form.getDateBeginPlan());
		Assert.assertEquals(1, form.getPlanningDays().size());
		Assert.assertEquals(SlotRepeatKind.Recurrent, form.getType());
		Assert.assertEquals(SlotTypeDefault.Open, form.getTypeOfSlot());
		Assert.assertFalse(form.isHasNoLimit());
		daoPlanning.flush();
	}

	@Test
	@Transactional
	public void testShouldEditSlotRecurrentNoEnd() throws Exception {
		DtoTimeSlot form = new DtoTimeSlot().setDateBeginHoraire(tFormat.parse("10:00"))
				.setDateBeginPlan(dtFormat.parse("01/09/2015 00:00")).setDateEndHoraire(tFormat.parse("12:00"))
				.setHasNoLimit(true).withPlanningDays(PlanningDays.AllDays).withPlanningDays(PlanningDays.Friday)
				.setType(SlotRepeatKind.Recurrent).setTypeOfSlot(SlotTypeDefault.Open);
		soaPlanning.saveOrUpdateSlot(new DtoPlanning(planning.getId()), form, options);
		daoPlanning.flush();
		daoPlanning.refresh(planning);
		form = soaPlanning
				.fetch(TimeSlotQueryBuilder.get().withId(planning.getSlots().iterator().next().getId()), options)
				.iterator().next();
		Assert.assertTrue(form.isHasNoLimit());
		Assert.assertNull(form.getDateEndPlan());
	}

	@Test
	@Transactional
	public void testShouldTransformToJson() throws Exception {
		DtoTimeSlot form = new DtoTimeSlot().setDateBeginHoraire(tFormat.parse("10:00"))
				.setDateBeginPlan(dtFormat.parse("01/09/2015 00:00")).setDateEndHoraire(tFormat.parse("12:00"))
				.setHasNoLimit(true).withPlanningDays(PlanningDays.AllDays).withPlanningDays(PlanningDays.Friday)
				.setType(SlotRepeatKind.Recurrent).setTypeOfSlot(SlotTypeDefault.Open);
		String value = mapper.writeValueAsString(form);
		DtoTimeSlot clone = mapper.readValue(value, DtoTimeSlot.class);
		Assert.assertEquals(form.getTypeOfSlot(), clone.getTypeOfSlot());
		daoPlanning.flush();
	}

	@Test
	@Transactional
	public void testShouldTransformCloseToJson() throws Exception {
		DtoTimeSlot form = new DtoTimeSlot().setDateBeginHoraire(tFormat.parse("10:00"))
				.setDateBeginPlan(dtFormat.parse("01/09/2015 00:00")).setDateEndHoraire(tFormat.parse("12:00"))
				.setHasNoLimit(true).withPlanningDays(PlanningDays.AllDays).withPlanningDays(PlanningDays.Friday)
				.setType(SlotRepeatKind.Recurrent).setTypeOfSlot(SlotTypeDefault.Close);
		String value = mapper.writeValueAsString(form);
		DtoTimeSlot clone = mapper.readValue(value, DtoTimeSlot.class);
		Assert.assertEquals(form.getTypeOfSlot(), clone.getTypeOfSlot());
		daoPlanning.flush();
	}

	@Test
	@Transactional
	public void testShouldRemoveSlotRecurrent() throws Exception {
		DtoTimeSlot form = new DtoTimeSlot().setDateBeginHoraire(tFormat.parse("10:00"))
				.setDateBeginPlan(dtFormat.parse("01/09/2015 00:00")).setDateEndHoraire(tFormat.parse("12:00"))
				.setDateEndPlan(dtFormat.parse("09/09/2015 00:00")).withPlanningDays(PlanningDays.Friday)
				.setType(SlotRepeatKind.Recurrent).setTypeOfSlot(SlotTypeDefault.Open);
		soaPlanning.saveOrUpdateSlot(new DtoPlanning(planning.getId()), form, options);
		daoPlanning.flush();
		daoPlanning.refresh(planning);
		assertEquals(1, planning.getSlots().size());
		soaPlanning.removeSlot(new DtoTimeSlot(planning.getSlots().iterator().next().getId()), options);
		daoPlanning.flush();
		daoPlanning.refresh(planning);
		assertEquals(0, planning.getSlots().size());
		daoPlanning.flush();
	}

	@Test
	@Transactional
	public void testShouldRemoveSlotExceptional() throws Exception {
		DtoTimeSlot form = new DtoTimeSlot().setDateBeginPlan(dtFormat.parse("01/09/2015 00:00"))
				.setDateEndPlan(dtFormat.parse("09/09/2015 00:00")).setType(SlotRepeatKind.Exceptionnal)
				.setTypeOfSlot(SlotTypeDefault.Open);
		soaPlanning.saveOrUpdateSlot(new DtoPlanning(planning.getId()), form, options);
		daoPlanning.flush();
		daoPlanning.refresh(planning);
		assertEquals(1, planning.getSlots().size());
		soaPlanning.removeSlot(new DtoTimeSlot(planning.getSlots().iterator().next().getId()), options);
		daoPlanning.flush();
		daoPlanning.refresh(planning);
		assertEquals(0, planning.getSlots().size());
		daoPlanning.flush();
	}

	@Test
	@Transactional
	public void testShouldRemoveAndSplitSlotExceptional() throws Exception {
		DtoTimeSlot form = new DtoTimeSlot().setDateBeginPlan(dtFormat.parse("01/09/2015 00:00"))
				.setDateEndPlan(dtFormat.parse("09/09/2015 00:00")).setType(SlotRepeatKind.Exceptionnal)
				.setTypeOfSlot(SlotTypeDefault.Open).addPlanningDays(PlanningDays.Thirsday);
		soaPlanning.saveOrUpdateSlot(new DtoPlanning(planning.getId()), form, options);
		daoPlanning.flush();
		daoPlanning.refresh(planning);
		assertEquals(1, planning.getSlots().size());
		DtoPlanningQuery query = new DtoPlanningQuery().setFrom(dtFormat.parse("02/09/2015 00:00"))
				.setTo(dtFormat.parse("03/09/2015 00:00")).setPlanning(new DtoPlanning(planning.getId()))
				.setType(OperationType.ComputeDelete).setToEdit(SlotTypeDefault.Open);
		soaPlanning.operation(query, options);
		daoPlanning.flush();
		daoPlanning.refresh(planning);
		assertEquals(2, planning.getSlots().size());
		Map<Date, Date> all = new HashMap<Date, Date>();
		for (TimeSlot slot : planning.getSlots()) {
			all.put(slot.getBeginPlan(), slot.getEndPlan());
		}
		Assert.assertEquals(dtFormat.parse("02/09/2015 00:00"), all.get(dtFormat.parse("01/09/2015 00:00")));
		Assert.assertEquals(dtFormat.parse("09/09/2015 00:00"), all.get(dtFormat.parse("03/09/2015 00:00")));
		daoPlanning.flush();
	}

	@Test
	@Transactional
	public void testShouldRemoveAndSplitSlotRecurrentNoEnd() throws Exception {
		DtoTimeSlot form = new DtoTimeSlot().setDateBeginHoraire(tFormat.parse("10:00"))
				.setDateBeginPlan(dtFormat.parse("01/09/2015 00:00")).setDateEndHoraire(tFormat.parse("12:00"))
				.setHasNoLimit(true).withPlanningDays(PlanningDays.Friday).setType(SlotRepeatKind.Recurrent)
				.setTypeOfSlot(SlotTypeDefault.Open);
		soaPlanning.saveOrUpdateSlot(new DtoPlanning(planning.getId()), form, options);
		daoPlanning.flush();
		daoPlanning.refresh(planning);
		assertEquals(1, planning.getSlots().size());

		DtoPlanningQuery query = new DtoPlanningQuery().setFrom(dtFormat.parse("02/09/2015 00:00"))
				.setTo(dtFormat.parse("05/09/2015 00:00")).setPlanning(new DtoPlanning(planning.getId()))
				.setType(OperationType.ComputeDelete).setToEdit(SlotTypeDefault.Open);
		soaPlanning.operation(query, options);
		daoPlanning.flush();
		daoPlanning.refresh(planning);
		assertEquals(2, planning.getSlots().size());
		Map<Boolean, Date> all = new HashMap<Boolean, Date>();
		Map<Date, Boolean> allB = new HashMap<Date, Boolean>();
		for (TimeSlot slot : planning.getSlots()) {
			all.put(((TimeSlotRecurrent) slot).isNoEndPlan(), slot.getEndPlan());
			allB.put(slot.getBeginPlan(), ((TimeSlotRecurrent) slot).isNoEndPlan());
		}
		// First is limited and second is no limit
		Assert.assertEquals(dtFormat.parse("02/09/2015 00:00"), all.get(false));
		Assert.assertTrue(allB.get(dtFormat.parse("05/09/2015 00:00")));
		daoPlanning.flush();
	}

	@Test
	@Transactional
	public void testShouldEditEvents() throws Exception {
		DtoTimeSlot form = new DtoTimeSlot().setDateBeginPlan(dtFormat.parse("01/09/2015 00:00"))
				.setDateEndPlan(dtFormat.parse("09/09/2015 00:00")).setType(SlotRepeatKind.Exceptionnal)
				.setTypeOfSlot(SlotTypeDefault.Open);
		soaPlanning.saveOrUpdateSlot(new DtoPlanning(planning.getId()), form, options);
		daoPlanning.flush();
		daoPlanning.refresh(planning);
		form = soaPlanning
				.fetch(TimeSlotQueryBuilder.get().withId(planning.getSlots().iterator().next().getId()), options)
				.iterator().next();
		Assert.assertEquals(SlotRepeatKind.Exceptionnal, form.getType());
		Assert.assertEquals(SlotTypeDefault.Open, form.getTypeOfSlot());
		Assert.assertEquals(dtFormat.parse("01/09/2015 00:00"), form.getDateBeginPlan());
		Assert.assertEquals(dtFormat.parse("09/09/2015 00:00"), form.getDateEndPlan());
		daoPlanning.flush();
	}

	@Test
	@Transactional
	public void testShouldShowEventsViewAllDays() throws Exception {
		DtoTimeSlot form1 = new DtoTimeSlot().setDateBeginHoraire(tFormat.parse("10:00"))
				.setDateBeginPlan(dtFormat.parse("01/09/2015 00:00")).setDateEndHoraire(tFormat.parse("12:00"))
				.setHasNoLimit(true).withPlanningDays(PlanningDays.AllDays).setType(SlotRepeatKind.Recurrent)
				.setTypeOfSlot(SlotTypeDefault.Open);
		DtoTimeSlot form2 = new DtoTimeSlot().setDateBeginPlan(dtFormat.parse("01/09/2015 00:00"))
				.setDateEndPlan(dtFormat.parse("31/09/2015 00:00")).setType(SlotRepeatKind.Exceptionnal)
				.setTypeOfSlot(SlotTypeDefault.Close);
		soaPlanning.saveOrUpdateSlot(new DtoPlanning(planning.getId()), form1, options);
		soaPlanning.saveOrUpdateSlot(new DtoPlanning(planning.getId()), form2, options);
		daoPlanning.flush();
		DtoPlanningQuery query = new DtoPlanningQuery().setFrom(dtFormat.parse("01/09/2015 00:00"))
				.setTo(dtFormat.parse("02/10/2015 11:00")).setPlanning(new DtoPlanning(planning.getId()))
				.setType(OperationType.ComputeView).withStronger(SlotTypeDefault.Close);
		DtoPlanningResult evetns = soaPlanning.operation(query, options);
		System.out.println("---------------------");
		for (DtoSlotOccurrence b : evetns.getEvents()) {
			System.out.println(b);
		}
		Assert.assertEquals(3, evetns.getEvents().size());
		daoPlanning.flush();
	}

	@Test
	@Transactional
	public void testShouldShowEventsShowDetails() throws Exception {
		DtoTimeSlot form1 = new DtoTimeSlot().setDateBeginHoraire(tFormat.parse("10:00"))
				.setDateBeginPlan(dtFormat.parse("01/09/2015 00:00")).setDateEndHoraire(tFormat.parse("12:00"))
				.setHasNoLimit(true).withPlanningDays(PlanningDays.Wednesday).setType(SlotRepeatKind.Recurrent)
				.setTypeOfSlot(SlotTypeDefault.Open);
		Collection<TimeSlot> slots = soaPlanning.saveOrUpdateSlot(new DtoPlanning(planning.getId()), form1, options);
		daoPlanning.flush();
		DtoPlanningQuery query = new DtoPlanningQuery().setFrom(dtFormat.parse("01/09/2015 00:00"))
				.setTo(dtFormat.parse("30/09/2015 23:00"))
				.setSlotFilter(new DtoSlotFilter().setIds(Arrays.asList(slots.iterator().next().getId())))
				.setType(OperationType.ComputeOccurrence).withStronger(SlotTypeDefault.Close);
		DtoPlanningResult evetns = soaPlanning.operation(query, options);
		System.out.println("---------------------");
		for (DtoSlotOccurrence b : evetns.getEvents()) {
			System.out.println(b);
		}
		Assert.assertEquals(5, evetns.getEvents().size());
		daoPlanning.flush();
	}

	@Test
	@Transactional
	public void testShouldShowEventsViewAllDaysExclude() throws Exception {
		DtoTimeSlot form1 = new DtoTimeSlot().setDateBeginHoraire(tFormat.parse("10:00"))
				.setDateBeginPlan(dtFormat.parse("01/09/2015 00:00")).setDateEndHoraire(tFormat.parse("12:00"))
				.setHasNoLimit(true).withPlanningDays(PlanningDays.AllDays).setType(SlotRepeatKind.Recurrent)
				.setTypeOfSlot(SlotTypeDefault.Open);
		DtoTimeSlot form2 = new DtoTimeSlot().setDateBeginPlan(dtFormat.parse("01/09/2015 00:00"))
				.setDateEndPlan(dtFormat.parse("31/09/2015 00:00")).setType(SlotRepeatKind.Exceptionnal)
				.setTypeOfSlot(SlotTypeDefault.Close);
		soaPlanning.saveOrUpdateSlot(new DtoPlanning(planning.getId()), form1, options);
		soaPlanning.saveOrUpdateSlot(new DtoPlanning(planning.getId()), form2, options);
		daoPlanning.flush();
		DtoPlanningQuery query = new DtoPlanningQuery().setFrom(dtFormat.parse("01/09/2015 00:00"))
				.setTo(dtFormat.parse("02/10/2015 11:00")).setPlanning(new DtoPlanning(planning.getId()))
				.setType(OperationType.ComputeView).withStronger(SlotTypeDefault.Close);
		DtoPlanningResult evetns = soaPlanning.operation(query, options);
		System.out.println("---------------------");
		for (DtoSlotOccurrence b : evetns.getEvents()) {
			System.out.println(b);
		}
		Assert.assertEquals(3, evetns.getEvents().size());
		daoPlanning.flush();
	}

	@Test
	@Transactional
	public void testShouldEditSlotExceptional() throws Exception {
		DtoTimeSlot form = new DtoTimeSlot().setDateBeginPlan(dtFormat.parse("01/09/2015 00:00"))
				.setDateEndPlan(dtFormat.parse("09/09/2015 00:00")).setType(SlotRepeatKind.Exceptionnal)
				.setTypeOfSlot(SlotTypeDefault.Open);
		soaPlanning.saveOrUpdateSlot(new DtoPlanning(planning.getId()), form, options);
		daoPlanning.flush();
		daoPlanning.refresh(planning);
		form = soaPlanning
				.fetch(TimeSlotQueryBuilder.get().withId(planning.getSlots().iterator().next().getId()), options)
				.iterator().next();
		Assert.assertEquals(dtFormat.parse("01/09/2015 00:00"), form.getDateBeginPlan());
		Assert.assertEquals(dtFormat.parse("09/09/2015 00:00"), form.getDateEndPlan());
		Assert.assertEquals(0, form.getPlanningDays().size());
		Assert.assertEquals(SlotRepeatKind.Exceptionnal, form.getType());
		Assert.assertEquals(SlotTypeDefault.Open, form.getTypeOfSlot());
		daoPlanning.flush();
	}

	@Transactional
	@Test(expected = Exception.class)
	public void testShouldSaveSlotRecurrentPlanFail() throws Exception {
		DtoTimeSlot form = new DtoTimeSlot().setDateBeginHoraire(tFormat.parse("10:00"))
				.setDateBeginPlan(dtFormat.parse("10/09/2015 00:00")).setDateEndHoraire(tFormat.parse("12:00"))
				.setDateEndPlan(dtFormat.parse("09/09/2015 00:00")).withPlanningDays(PlanningDays.Friday)
				.setType(SlotRepeatKind.Recurrent).setTypeOfSlot(SlotTypeDefault.Open);
		soaPlanning.saveOrUpdateSlot(new DtoPlanning(planning.getId()), form, options);
		daoPlanning.flush();
	}

	@Transactional
	@Test(expected = Exception.class)
	public void testShouldSaveSlotRecurrentHoraireFailed() throws Exception {
		DtoTimeSlot form = new DtoTimeSlot().setDateBeginHoraire(tFormat.parse("16:00"))
				.setDateBeginPlan(dtFormat.parse("10/09/2015 00:00")).setDateEndHoraire(tFormat.parse("12:00"))
				.setDateEndPlan(dtFormat.parse("09/09/2015 00:00")).withPlanningDays(PlanningDays.Friday)
				.setType(SlotRepeatKind.Recurrent).setTypeOfSlot(SlotTypeDefault.Open);
		soaPlanning.saveOrUpdateSlot(new DtoPlanning(planning.getId()), form, options);
		daoPlanning.flush();
	}

	@Test
	@Transactional
	public void testShouldSaveSlotRecurrentSingleWithAllDays() throws Exception {
		DtoTimeSlot form = new DtoTimeSlot().setDateBeginHoraire(tFormat.parse("10:00"))
				.setDateBeginPlan(dtFormat.parse("01/09/2015 00:00")).setDateEndHoraire(tFormat.parse("12:00"))
				.setDateEndPlan(dtFormat.parse("09/09/2015 00:00")).withPlanningDays(PlanningDays.AllDays)
				.withPlanningDays(PlanningDays.Friday).setType(SlotRepeatKind.Recurrent).setTypeOfSlot(SlotTypeDefault.Open);
		soaPlanning.saveOrUpdateSlot(new DtoPlanning(planning.getId()), form, options);
		daoPlanning.flush();
		daoPlanning.refresh(planning);
		assertEquals(1, planning.getSlots().size());
	}

	@Test
	@Transactional
	public void testShouldSaveSlotRecurrentMultipleWithoutAllDays() throws Exception {
		DtoTimeSlot form = new DtoTimeSlot().setDateBeginHoraire(tFormat.parse("10:00"))
				.setDateBeginPlan(dtFormat.parse("01/09/2015 00:00")).setDateEndHoraire(tFormat.parse("12:00"))
				.setDateEndPlan(dtFormat.parse("09/09/2015 00:00")).withPlanningDays(PlanningDays.Monday)
				.withPlanningDays(PlanningDays.Friday).setType(SlotRepeatKind.Recurrent).setTypeOfSlot(SlotTypeDefault.Open);
		soaPlanning.saveOrUpdateSlot(new DtoPlanning(planning.getId()), form, options);
		daoPlanning.flush();
		daoPlanning.refresh(planning);
		assertEquals(2, planning.getSlots().size());
	}

	@Test
	@Transactional
	public void testShouldSaveSlotExceptionnal() throws Exception {
		DtoTimeSlot form = new DtoTimeSlot().setDateBeginPlan(dtFormat.parse("01/09/2015 00:00"))
				.setDateEndPlan(dtFormat.parse("09/09/2015 00:00")).setType(SlotRepeatKind.Exceptionnal)
				.setTypeOfSlot(SlotTypeDefault.Open);
		soaPlanning.saveOrUpdateSlot(new DtoPlanning(planning.getId()), form, options);
		daoPlanning.flush();
		daoPlanning.refresh(planning);
		assertEquals(1, planning.getSlots().size());
	}

	@Transactional
	@Test(expected = ConstraintViolationException.class)
	public void testShouldFailedSaveSlotExceptionnalWithBadDates() throws Exception {
		DtoTimeSlot form = new DtoTimeSlot().setDateBeginPlan(dtFormat.parse("10/09/2015 00:00"))
				.setDateEndPlan(dtFormat.parse("09/09/2015 00:00")).setType(SlotRepeatKind.Exceptionnal)
				.setTypeOfSlot(SlotTypeDefault.Open);
		soaPlanning.saveOrUpdateSlot(new DtoPlanning(planning.getId()), form, options);
		daoPlanning.flush();
	}

	@Test
	@Transactional
	public void testShouldRecurrentWithNoNoEndOkAndHasLongDuration() throws Exception {
		DtoTimeSlot form = new DtoTimeSlot().setDateBeginHoraire(tFormat.parse("10:00"))
				.setDateBeginPlan(dtFormat.parse("01/09/2015 00:00")).setDateEndHoraire(tFormat.parse("12:00"))
				.setHasNoLimit(true).withPlanningDays(PlanningDays.AllDays).withPlanningDays(PlanningDays.Friday)
				.setType(SlotRepeatKind.Recurrent).setTypeOfSlot(SlotTypeDefault.Open);
		soaPlanning.saveOrUpdateSlot(new DtoPlanning(planning.getId()), form, options);
		daoPlanning.flush();
		daoPlanning.refresh(planning);
		assertEquals(1, planning.getSlots().size());
		TimeSlot slot = planning.getSlots().iterator().next();
		MutableDateTime year = new MutableDateTime();
		year.addYears(100);
		Assert.assertTrue(slot.toIntervalPlan().getEnd().isAfter(year));
	}

	@Test
	@Transactional
	public void testShouldFetchByIntervalRecurrentInclusive() throws Exception {
		DtoTimeSlot slot1 = new DtoTimeSlot().setDateBeginHoraire(tFormat.parse("10:00"))
				.setDateBeginPlan(dtFormat.parse("01/09/2015 12:00")).setDateEndHoraire(tFormat.parse("12:00"))
				.setDateEndPlan(dtFormat.parse("10/09/2015 12:00")).withPlanningDays(PlanningDays.AllDays)
				.withPlanningDays(PlanningDays.Friday).setType(SlotRepeatKind.Recurrent).setTypeOfSlot(SlotTypeDefault.Open);
		soaPlanning.saveOrUpdateSlot(new DtoPlanning(planning.getId()), slot1, options);
		daoTimeSlot.flush();
		//
		DetachedCriteria criteria = TimeSlotQueryBuilder.get()
				.withIntersectInterval(dtFormat.parse("02/09/2015 12:00"), dtFormat.parse("08/09/2015 12:00"))
				.withPlanning(planning).getQuery();
		assertEquals(1, daoTimeSlot.find(criteria).size());
	}

	@Test
	@Transactional
	public void testShouldFetchByIntervalRecurrentNoEndInclusive() throws Exception {
		DtoTimeSlot slot1 = new DtoTimeSlot().setDateBeginHoraire(tFormat.parse("10:00"))
				.setDateBeginPlan(dtFormat.parse("01/09/2015 12:00")).setDateEndHoraire(tFormat.parse("12:00"))
				.setHasNoLimit(true).withPlanningDays(PlanningDays.AllDays).withPlanningDays(PlanningDays.Friday)
				.setType(SlotRepeatKind.Recurrent).setTypeOfSlot(SlotTypeDefault.Open);
		soaPlanning.saveOrUpdateSlot(new DtoPlanning(planning.getId()), slot1, options);
		daoTimeSlot.flush();
		//
		DetachedCriteria criteria = TimeSlotQueryBuilder.get()
				.withIntersectInterval(dtFormat.parse("02/09/2015 12:00"), dtFormat.parse("08/09/2015 12:00"))
				.withPlanning(planning).getQuery();
		assertEquals(1, daoTimeSlot.find(criteria).size());
	}

	@Test
	@Transactional
	public void testShouldFetchByIntervalRecurrentInclusiveInverse() throws Exception {
		DtoTimeSlot slot1 = new DtoTimeSlot().setDateBeginHoraire(tFormat.parse("10:00"))
				.setDateBeginPlan(dtFormat.parse("02/09/2015 12:00")).setDateEndHoraire(tFormat.parse("12:00"))
				.setDateEndPlan(dtFormat.parse("08/09/2015 12:00")).withPlanningDays(PlanningDays.AllDays)
				.withPlanningDays(PlanningDays.Friday).setType(SlotRepeatKind.Recurrent).setTypeOfSlot(SlotTypeDefault.Open);
		soaPlanning.saveOrUpdateSlot(new DtoPlanning(planning.getId()), slot1, options);
		daoTimeSlot.flush();
		//
		DetachedCriteria criteria = TimeSlotQueryBuilder.get()
				.withIntersectInterval(dtFormat.parse("01/09/2015 12:00"), dtFormat.parse("10/09/2015 12:00"))
				.withPlanning(planning).getQuery();
		assertEquals(1, daoTimeSlot.find(criteria).size());
	}

	@Test
	@Transactional
	public void testShouldFetchByIntervalRecurrentOverlaps() throws Exception {
		DtoTimeSlot slot1 = new DtoTimeSlot().setDateBeginHoraire(tFormat.parse("10:00"))
				.setDateBeginPlan(dtFormat.parse("01/09/2015 12:00")).setDateEndHoraire(tFormat.parse("12:00"))
				.setDateEndPlan(dtFormat.parse("10/09/2015 12:00")).withPlanningDays(PlanningDays.AllDays)
				.withPlanningDays(PlanningDays.Friday).setType(SlotRepeatKind.Recurrent).setTypeOfSlot(SlotTypeDefault.Open);
		soaPlanning.saveOrUpdateSlot(new DtoPlanning(planning.getId()), slot1, options);
		daoTimeSlot.flush();
		//
		DetachedCriteria criteria = TimeSlotQueryBuilder.get()
				.withIntersectInterval(dtFormat.parse("01/08/2015 12:00"), dtFormat.parse("02/09/2015 12:00"))
				.withPlanning(planning).getQuery();
		assertEquals(1, daoTimeSlot.find(criteria).size());
	}

	@Test
	@Transactional
	public void testShouldFetchByIntervalRecurrentAbuts() throws Exception {
		DtoTimeSlot slot1 = new DtoTimeSlot().setDateBeginHoraire(tFormat.parse("10:00"))
				.setDateBeginPlan(dtFormat.parse("01/09/2015 12:00")).setDateEndHoraire(tFormat.parse("12:00"))
				.setDateEndPlan(dtFormat.parse("10/09/2015 12:00")).withPlanningDays(PlanningDays.AllDays)
				.withPlanningDays(PlanningDays.Friday).setType(SlotRepeatKind.Recurrent).setTypeOfSlot(SlotTypeDefault.Open);
		soaPlanning.saveOrUpdateSlot(new DtoPlanning(planning.getId()), slot1, options);
		daoTimeSlot.flush();
		//
		DetachedCriteria criteria = TimeSlotQueryBuilder.get()
				.withIntersectInterval(dtFormat.parse("10/09/2015 12:00"), dtFormat.parse("20/09/2015 12:00"))
				.withPlanning(planning).getQuery();
		assertEquals(1, daoTimeSlot.find(criteria).size());
	}

	@Test
	@Transactional
	public void testShouldFetchByIntervalRecurrentEquals() throws Exception {
		DtoTimeSlot slot1 = new DtoTimeSlot().setDateBeginHoraire(tFormat.parse("10:00"))
				.setDateBeginPlan(dtFormat.parse("01/09/2015 12:00")).setDateEndHoraire(tFormat.parse("12:00"))
				.setDateEndPlan(dtFormat.parse("10/09/2015 12:00")).withPlanningDays(PlanningDays.AllDays)
				.withPlanningDays(PlanningDays.Friday).setType(SlotRepeatKind.Recurrent).setTypeOfSlot(SlotTypeDefault.Open);
		soaPlanning.saveOrUpdateSlot(new DtoPlanning(planning.getId()), slot1, options);
		daoTimeSlot.flush();
		//
		DetachedCriteria criteria = TimeSlotQueryBuilder.get()
				.withIntersectInterval(dtFormat.parse("01/09/2015 12:00"), dtFormat.parse("10/09/2015 12:00"))
				.withPlanning(planning).getQuery();
		assertEquals(1, daoTimeSlot.find(criteria).size());
	}

	@Test
	@Transactional
	public void testShouldFetchByIntervalRecurrentExclusive() throws Exception {
		DtoTimeSlot slot1 = new DtoTimeSlot().setDateBeginHoraire(tFormat.parse("10:00"))
				.setDateBeginPlan(dtFormat.parse("01/09/2015 12:00")).setDateEndHoraire(tFormat.parse("12:00"))
				.setDateEndPlan(dtFormat.parse("10/09/2015 12:00")).withPlanningDays(PlanningDays.AllDays)
				.withPlanningDays(PlanningDays.Friday).setType(SlotRepeatKind.Recurrent).setTypeOfSlot(SlotTypeDefault.Open);
		soaPlanning.saveOrUpdateSlot(new DtoPlanning(planning.getId()), slot1, options);
		daoTimeSlot.flush();
		//
		DetachedCriteria criteria = TimeSlotQueryBuilder.get()
				.withIntersectInterval(dtFormat.parse("15/09/2015 12:00"), dtFormat.parse("20/09/2015 12:00"))
				.withPlanning(planning).getQuery();
		assertEquals(0, daoTimeSlot.find(criteria).size());
	}

	@Test
	@Transactional
	public void testShouldFetchByIntervalRecurrentByType() throws Exception {
		DtoTimeSlot slot1 = new DtoTimeSlot().setDateBeginHoraire(tFormat.parse("10:00"))
				.setDateBeginPlan(dtFormat.parse("01/09/2015 12:00")).setDateEndHoraire(tFormat.parse("12:00"))
				.setDateEndPlan(dtFormat.parse("10/09/2015 12:00")).withPlanningDays(PlanningDays.AllDays)
				.withPlanningDays(PlanningDays.Friday).setType(SlotRepeatKind.Recurrent).setTypeOfSlot(SlotTypeDefault.Open);
		soaPlanning.saveOrUpdateSlot(new DtoPlanning(planning.getId()), slot1, options);
		daoTimeSlot.flush();
		//
		TimeSlotQueryBuilder query = TimeSlotQueryBuilder.get().withType(SlotRepeatKind.Recurrent)
				.withType(SlotTypeDefault.Open).withPlanning(planning);
		assertEquals(1, daoTimeSlot.find(query.getQuery()).size());
		//
		query = TimeSlotQueryBuilder.get().withType(SlotRepeatKind.Exceptionnal).withType(SlotTypeDefault.Open)
				.withPlanning(planning);
		assertEquals(0, daoTimeSlot.find(query.getQuery()).size());
		//
		query = TimeSlotQueryBuilder.get().withType(SlotRepeatKind.Recurrent).withType(SlotTypeDefault.Close)
				.withPlanning(planning);
		assertEquals(0, daoTimeSlot.find(query.getQuery()).size());
	}
}
