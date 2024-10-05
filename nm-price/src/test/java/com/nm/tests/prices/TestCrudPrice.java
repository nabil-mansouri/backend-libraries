package com.nm.tests.prices;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

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

import com.nm.app.currency.CurrencyDto;
import com.nm.app.currency.SoaDevise;
import com.nm.app.locale.LocaleFormDto;
import com.nm.app.locale.SoaLocale;
import com.nm.prices.dao.DaoPrice;
import com.nm.prices.dao.impl.PriceFilterQueryBuilder;
import com.nm.prices.dtos.constants.PriceFilterEnum.PriceFilterEnumDefault;
import com.nm.prices.dtos.constants.PriceFormOptions;
import com.nm.prices.dtos.forms.PriceFormDto;
import com.nm.prices.model.Price;
import com.nm.prices.soa.SoaPrice;
import com.nm.tests.bridges.ConfigurationTestPrice;
import com.nm.tests.bridges.PriceAdapterImpl.CustomPriceQueryBuilder;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.NoDataFoundException;
import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(classes = ConfigurationTestPrice.class)
public class TestCrudPrice {

	@Autowired
	private SoaPrice soaPrice;
	@Autowired
	protected SoaLocale soaLocale;
	@Autowired
	private DaoPrice daoPrice;
	//
	@Autowired
	private SoaDevise soaDevise;

	@Autowired
	public void setAppliContent(ConfigurableApplicationContext applicationContext) {
	}

	//
	@Before
	public void setUp() throws Exception {
		soaLocale.setSelectedLocales(Arrays.asList(new LocaleFormDto("fr"), new LocaleFormDto("en")));
		soaLocale.setDefaultLocale(new LocaleFormDto("fr"));
	}

	@Test
	@Transactional
	public void testShouldFindByCurrent() throws NoDataFoundException {
		MutableDateTime from = new MutableDateTime();
		MutableDateTime to = new MutableDateTime();
		from.addWeeks(-1);
		to.addWeeks(2);
		//
		PriceFormDto form = soaPrice.createPrice();
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		form.getFilter().setHasFrom(true).setFrom(from.toDate());
		form.getFilter().setHasTo(true).setTo(to.toDate());
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		daoPrice.clear();
		// from -1 and to +2
		Assert.assertEquals(1,
				daoPrice.count(CustomPriceQueryBuilder.get().withOnlyCurrent(true).getQuery()).intValue());
		Assert.assertEquals(0,
				daoPrice.count(CustomPriceQueryBuilder.get().withOnlyCurrent(false).getQuery()).intValue());
		// from +1 and to +2
		from.addWeeks(2);
		form.getFilter().setHasFrom(true).setFrom(from.toDate());
		form.getFilter().setHasTo(true).setTo(to.toDate());
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		daoPrice.clear();
		//
		Assert.assertEquals(0,
				daoPrice.count(CustomPriceQueryBuilder.get().withOnlyCurrent(true).getQuery()).intValue());
		Assert.assertEquals(1,
				daoPrice.count(CustomPriceQueryBuilder.get().withOnlyCurrent(false).getQuery()).intValue());
		// from undefined and to +2
		form.getFilter().setHasFrom(false).setFrom(from.toDate());
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		daoPrice.clear();
		//
		Assert.assertEquals(1,
				daoPrice.count(CustomPriceQueryBuilder.get().withOnlyCurrent(true).getQuery()).intValue());
		Assert.assertEquals(0,
				daoPrice.count(CustomPriceQueryBuilder.get().withOnlyCurrent(false).getQuery()).intValue());
		// from undefined and to 0
		to.addWeeks(-2);
		form.getFilter().setHasTo(true).setTo(to.toDate());
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		daoPrice.clear();
		//
		System.out.println(daoPrice.count(CustomPriceQueryBuilder.get().withOnlyCurrent(true).getQuery()).intValue());
		Assert.assertEquals(0,
				daoPrice.count(CustomPriceQueryBuilder.get().withOnlyCurrent(true).getQuery()).intValue());
		Assert.assertEquals(1,
				daoPrice.count(CustomPriceQueryBuilder.get().withOnlyCurrent(false).getQuery()).intValue());
		// from undefined and to undefined
		form.getFilter().setHasTo(false);
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		daoPrice.clear();
		//
		Assert.assertEquals(1,
				daoPrice.count(CustomPriceQueryBuilder.get().withOnlyCurrent(true).getQuery()).intValue());
		Assert.assertEquals(0,
				daoPrice.count(CustomPriceQueryBuilder.get().withOnlyCurrent(false).getQuery()).intValue());

	}

	@Test
	@Transactional
	public void testShouldFindByDateFrom() throws NoDataFoundException {
		Date from = new Date();
		PriceFormDto form = soaPrice.createPrice();
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		//
		Collection<Price> prices = daoPrice.find(CustomPriceQueryBuilder.get().withFilterOrNotExists(
				PriceFilterQueryBuilder.getDate().withDateLessT(from), PriceFilterEnumDefault.LimitInTimeFrom));
		Assert.assertEquals(1, prices.size());
		//
		form = soaPrice.editPrice(form.getId());
		form.getFilter().setHasFrom(true).setFrom(from);
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		//
		prices = daoPrice.find(CustomPriceQueryBuilder.get().withFilterOrNotExists(
				PriceFilterQueryBuilder.getDate().withDateLessT(from), PriceFilterEnumDefault.LimitInTimeFrom));
		Assert.assertTrue(prices.isEmpty());
		prices = daoPrice.find(CustomPriceQueryBuilder.get().withFilterOrNotExists(
				PriceFilterQueryBuilder.getDate().withDateLessTE(from), PriceFilterEnumDefault.LimitInTimeFrom));
		Assert.assertEquals(1, prices.size());
	}

	@Test
	@Transactional
	public void testShouldFindByDateTo() throws NoDataFoundException {
		Date from = new Date();
		PriceFormDto form = soaPrice.createPrice();
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		//
		Collection<Price> prices = daoPrice.find(CustomPriceQueryBuilder.get().withFilterOrNotExists(
				PriceFilterQueryBuilder.getDate().withDateLessT(from), PriceFilterEnumDefault.LimitInTimeTo));
		Assert.assertEquals(1, prices.size());
		//
		form = soaPrice.editPrice(form.getId());
		form.getFilter().setHasTo(true).setTo(from);
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		//
		prices = daoPrice.find(CustomPriceQueryBuilder.get().withFilterOrNotExists(
				PriceFilterQueryBuilder.getDate().withDateGreaterT(from), PriceFilterEnumDefault.LimitInTimeTo));
		Assert.assertTrue(prices.isEmpty());
		prices = daoPrice.find(CustomPriceQueryBuilder.get().withFilterOrNotExists(
				PriceFilterQueryBuilder.getDate().withDateGreaterTE(from), PriceFilterEnumDefault.LimitInTimeTo));
		Assert.assertEquals(1, prices.size());
	}

	@Test
	@Transactional
	public void testShouldNoCurrency() throws NoDataFoundException {
		PriceFormDto form = soaPrice.createPrice();
		Assert.assertTrue(form.isNoCurrency());
		soaDevise.setDefault(new CurrencyDto("C"));
		form = soaPrice.createPrice();
		Assert.assertFalse(form.isNoCurrency());
	}

	@Test
	@Transactional
	public void testShouldRemovePriceWithDateFilter() throws NoDataFoundException {
		PriceFormDto form = soaPrice.createPrice();
		form.getFilter().setHasTo(true).setTo(new Date());
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		//
		soaPrice.removePrice(form.getId());
		daoPrice.flush();
		Assert.assertEquals(0, daoPrice.findAll().size());
	}

	@Test
	@Transactional
	public void testShouldSaveWithDateFrom() throws NoDataFoundException {
		Date from = new Date();
		PriceFormDto form = soaPrice.createPrice();
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		form = soaPrice.editPrice(form.getId());
		Assert.assertFalse(form.getFilter().isHasFrom());
		form.getFilter().setHasFrom(true).setFrom(from);
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		form = soaPrice.editPrice(form.getId());
		Assert.assertTrue(form.getFilter().isHasFrom());
		Assert.assertEquals(from, form.getFilter().getFrom());
	}

	@Test
	@Transactional
	public void testShouldSaveWithDateTo() throws NoDataFoundException {
		Date to = new Date();
		PriceFormDto form = soaPrice.createPrice();
		soaPrice.refresh(form, new OptionsList().withOption(PriceFormOptions.Subject));
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		form = soaPrice.editPrice(form.getId());
		Assert.assertFalse(form.getFilter().isHasFrom());
		form.getFilter().setHasTo(true).setTo(to);
		soaPrice.saveOrUpdate(form, new OptionsList());
		daoPrice.flush();
		form = soaPrice.editPrice(form.getId());
		Assert.assertTrue(form.getFilter().isHasTo());
		Assert.assertEquals(to, form.getFilter().getTo());
	}

}
