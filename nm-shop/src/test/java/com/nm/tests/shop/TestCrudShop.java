package com.nm.tests.shop;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.orm.hibernate5.HibernateObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nm.app.locale.LocaleFormDto;
import com.nm.app.locale.SoaLocale;
import com.nm.plannings.constants.EventType;
import com.nm.plannings.dtos.TimeSlotDto;
import com.nm.plannings.model.Planning;
import com.nm.plannings.soa.SoaPlanning;
import com.nm.shop.bridge.PlanningTypeShop;
import com.nm.shop.bridge.ShopSlotType;
import com.nm.shop.dao.DaoShop;
import com.nm.shop.dtos.ShopFormDto;
import com.nm.shop.soa.SoaShop;
import com.nm.shop.tests.ShopDataFactory;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.NoDataFoundException;
import com.nm.utils.hibernate.impl.AbstractGenericDao;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestShop.class)
public class TestCrudShop {
	@Autowired
	private ShopDataFactory dataFactory;
	@Autowired
	protected SoaShop soaRestaurant;
	@Autowired
	protected SoaLocale soaLocale;
	@Autowired
	private DaoShop daoRestaurant;
	@Autowired
	private SoaPlanning soaPlanning;
	private IGenericDao<Planning, Long> daoPlanning;
	//
	private ObjectMapper mapper = new ObjectMapper();
	private ShopFormDto form;
	protected SimpleDateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	protected SimpleDateFormat tFormat = new SimpleDateFormat("HH:mm");

	@Autowired
	public void setAppliContent(ConfigurableApplicationContext applicationContext) {
	}

	//
	@Before
	public void setUp() throws Exception {
		daoPlanning = AbstractGenericDao.get(Planning.class);
		soaLocale.setSelectedLocales(Arrays.asList(new LocaleFormDto("fr"), new LocaleFormDto("en")));
		soaLocale.setDefaultLocale(new LocaleFormDto("fr"));
		this.form = dataFactory.createShop();
	}

	@Test
	@Transactional
	public void testShouldCreateRestaurant() throws Exception {
		form = soaRestaurant.createShop();
		Assert.assertEquals(2, form.getCms().getContents().size());
	}

	@Test(expected = NoDataFoundException.class)
	@Transactional
	public void testShouldDeleteRestaurants() throws Exception {
		soaRestaurant.saveOrUpdate(form, new OptionsList());
		Assert.assertNotNull(daoRestaurant.get(form.getId()));
		//
		soaRestaurant.removeShop(form.getId());
		daoRestaurant.get(form.getId());
	}

	@Test
	@Transactional
	public void testShouldSaveRestaurant() throws Exception {
		ShopFormDto view = soaRestaurant.saveOrUpdate(form, new OptionsList());
		Assert.assertNotNull(view.getId());
	}

	@Test(expected = HibernateObjectRetrievalFailureException.class)
	@Transactional
	public void testShouldDeletePlanningWith() throws Exception {
		form.getCms().getImgs().clear();
		form.getCms().add("IMG");
		soaRestaurant.saveOrUpdate(form, new OptionsList());
		form = soaRestaurant.editShop(form.getId());
		//
		Planning planning = daoPlanning.get(soaPlanning.getOrCreate(form.getId(), new PlanningTypeShop()).getId());
		Long id = planning.getId();
		daoRestaurant.flush();
		soaRestaurant.removeShop(form.getId());
		daoPlanning.flush();
		planning = daoPlanning.get(id);
		daoPlanning.refresh(planning);
	}

	@Test
	@Transactional
	public void testShouldCrudSlotOpenForRestaurant() throws Exception {
		soaRestaurant.saveOrUpdate(form, new OptionsList());
		form = soaRestaurant.editShop(form.getId());
		//
		Planning planning = daoPlanning.get(soaPlanning.getOrCreate(form.getId(), new PlanningTypeShop()).getId());
		TimeSlotDto form = new TimeSlotDto().setDateBeginPlan(dtFormat.parse("01/09/2015 00:00"))
				.setDateEndPlan(dtFormat.parse("09/09/2015 00:00")).setType(EventType.Exceptionnal)
				.setTypeOfSlot(ShopSlotType.ShopOpen);
		soaPlanning.saveOrUpdate(planning.getId(), form);
		daoPlanning.flush();
		daoPlanning.refresh(planning);
		form = soaPlanning.editSlot(planning.getSlots().iterator().next().getId());
		Assert.assertEquals(dtFormat.parse("01/09/2015 00:00"), form.getDateBeginPlan());
		Assert.assertEquals(EventType.Exceptionnal, form.getType());
		Assert.assertEquals(ShopSlotType.ShopOpen, form.getTypeOfSlot());
		Assert.assertFalse(form.isHasNoLimit());
	}

	@Test
	@Transactional
	public void testShouldCrudSlotCloseForRestaurant() throws Exception {
		soaRestaurant.saveOrUpdate(form, new OptionsList());
		form = soaRestaurant.editShop(form.getId());
		//
		Planning planning = daoPlanning.get(soaPlanning.getOrCreate(form.getId(), new PlanningTypeShop()).getId());
		TimeSlotDto form = new TimeSlotDto().setDateBeginPlan(dtFormat.parse("01/09/2015 00:00"))
				.setDateEndPlan(dtFormat.parse("09/09/2015 00:00")).setType(EventType.Exceptionnal)
				.setTypeOfSlot(ShopSlotType.ShopClose);
		soaPlanning.saveOrUpdate(planning.getId(), form);
		daoPlanning.flush();
		daoPlanning.refresh(planning);
		form = soaPlanning.editSlot(planning.getSlots().iterator().next().getId());
		Assert.assertEquals(dtFormat.parse("01/09/2015 00:00"), form.getDateBeginPlan());
		Assert.assertEquals(EventType.Exceptionnal, form.getType());
		Assert.assertEquals(ShopSlotType.ShopClose, form.getTypeOfSlot());
		Assert.assertFalse(form.isHasNoLimit());
	}

	@Test
	@Transactional
	public void testShouldEditRestaurantWithImgs() throws Exception {
		form.getCms().getImgs().clear();
		form.getCms().add("IMG");
		soaRestaurant.saveOrUpdate(form, new OptionsList());
		form = soaRestaurant.editShop(form.getId());
		Assert.assertEquals(1, form.getCms().getImgs().size());
		soaRestaurant.saveOrUpdate(form, new OptionsList());
		form = soaRestaurant.editShop(form.getId());
		Assert.assertEquals(1, form.getCms().getImgs().size());
	}

	@Test
	@Transactional
	public void testShouldTransformJson() throws Exception {
		form.getCms().getImgs().clear();
		form.getCms().add("IMG");
		soaRestaurant.saveOrUpdate(form, new OptionsList());
		String value = mapper.writeValueAsString(form);
		ShopFormDto clone = mapper.readValue(value, ShopFormDto.class);
		Assert.assertEquals(form.getCms().getImgs().size(), clone.getCms().getImgs().size());
	}
}
