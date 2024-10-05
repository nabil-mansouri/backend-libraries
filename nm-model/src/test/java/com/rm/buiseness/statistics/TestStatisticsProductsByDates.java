package com.rm.buiseness.statistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.MutableDateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nm.app.stats.DaoDates;
import com.nm.app.stats.DimensionType;
import com.nm.app.stats.MeasureType;
import com.rm.buiseness.commons.TestScenarios;
import com.rm.buiseness.commons.TestUrlUtils;
import com.rm.contract.products.views.ProductViewDto;
import com.rm.contract.statistics.beans.StatisticOrderBean;
import com.rm.contract.statistics.beans.StatisticResultNodeBean;
import com.rm.contract.statistics.beans.StatisticsFilterBean;
import com.rm.contract.statistics.constants.DimensionPeriodValue;
import com.rm.contract.statistics.constants.StatisticOrderDirection;
import com.rm.contract.statistics.constants.StatisticOrderType;
import com.rm.soa.statistics.SoaStatistics;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class) @ActiveProfiles(TestUrlUtils.PROFILE_TEST)
@ContextConfiguration(locations = { TestUrlUtils.CONTEXT_PATH })
public class TestStatisticsProductsByDates {

	@Autowired
	private SoaStatistics soaStatistics;
	@Autowired
	private DaoDates daoDates;
	private TestScenarios test;
	private TestStatisticHelper helper;
	@Autowired
	private ApplicationContext applicationContext;
	private ProductViewDto menu;

	//
	@Before
	public void setUp() throws Exception {
		ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) applicationContext)
				.getBeanFactory();
		test = beanFactory.createBean(TestScenarios.class);
		helper = beanFactory.createBean(TestStatisticHelper.class);
		//
		helper.deleteAllOrders();
		test.testCreate();
		menu = test.getAllProductsView().get(TestScenarios.MENU_MOYEN);
		// for (ProductPartViewDto part : menu.parts()) {
		// part.setSelected(part.getProducts().get(0));
		// }
	}

	@After
	public void tearDown() throws NoDataFoundException {
		helper.deleteAllOrders();
	}

	@Test
	/**
	 * No transaction here (inside processors)
	 * 
	 * @throws Exception
	 */
	public void testNbProductsSoldMinutes() throws Exception {
		List<MutableDateTime> dates = new ArrayList<MutableDateTime>();
		// 2 today and 1 after tomorrow
		{
			MutableDateTime date = new MutableDateTime();
			dates.add(date);
		}
		{
			MutableDateTime date = new MutableDateTime();
			dates.add(date);
		}
		{
			MutableDateTime date = new MutableDateTime();
			date.addMinutes(2);
			dates.add(date);
		}
		// Not take care of that
		{
			MutableDateTime date = new MutableDateTime();
			date.addMinutes(10);
			dates.add(date);
		}
		helper.addOrder(menu, test.getAllRestaurants().get(TestScenarios.RESTO1), dates, 2);
		//
		{
			StatisticsFilterBean filter = new StatisticsFilterBean();
			{
				MutableDateTime begin = new MutableDateTime();
				filter.setFrom(begin.toDate());
			}
			{
				MutableDateTime end = new MutableDateTime();
				end.addMinutes(3);
				filter.setTo(end.toDate());
			}
			filter.getMeasureTypes().add(MeasureType.CountProduct);
			filter.getDimensions().put(DimensionType.Period, DimensionPeriodValue.Minute);
			filter.getOrders().add(new StatisticOrderBean(StatisticOrderDirection.Asc, StatisticOrderType.Minutes));
			filter.setIdProduct(Arrays.asList(this.menu.getId()));
			int beforeDates = daoDates.count().intValue();
			List<StatisticResultNodeBean> stats = new ArrayList<StatisticResultNodeBean>(
					soaStatistics.productStat(filter));
			Assert.assertEquals(4, stats.size());
			List<Integer> counts = Arrays.asList(4, 0, 2, 0);
			System.out.println(stats);
			for (int index = 0; index < stats.size(); index++) {
				Assert.assertEquals(1, stats.get(index).getMeasures().size());
				Assert.assertEquals(Long.valueOf(counts.get(index).intValue()),
						stats.get(index).getMeasures().iterator().next().getValue());
			}
			Assert.assertEquals(beforeDates, daoDates.count().intValue());
		}
	}

	@Test
	/**
	 * No transaction here (inside processors)
	 * 
	 * @throws Exception
	 */
	public void testNbProductsSoldHours() throws Exception {
		List<MutableDateTime> dates = new ArrayList<MutableDateTime>();
		// 2 today and 1 after tomorrow
		{
			MutableDateTime date = new MutableDateTime();
			dates.add(date);
		}
		{
			MutableDateTime date = new MutableDateTime();
			dates.add(date);
		}
		{
			MutableDateTime date = new MutableDateTime();
			date.addHours(2);
			dates.add(date);
		}
		// Not take care of that
		{
			MutableDateTime date = new MutableDateTime();
			date.addHours(10);
			dates.add(date);
		}
		helper.addOrder(menu, test.getAllRestaurants().get(TestScenarios.RESTO1), dates, 2);
		//
		{
			StatisticsFilterBean filter = new StatisticsFilterBean();
			{
				MutableDateTime begin = new MutableDateTime();
				filter.setFrom(begin.toDate());
			}
			{
				MutableDateTime end = new MutableDateTime();
				end.addHours(3);
				filter.setTo(end.toDate());
			}
			filter.getMeasureTypes().add(MeasureType.CountProduct);
			filter.getDimensions().put(DimensionType.Period, DimensionPeriodValue.Hour);
			filter.setIdProduct(Arrays.asList(this.menu.getId()));
			filter.getOrders().add(new StatisticOrderBean(StatisticOrderDirection.Asc, StatisticOrderType.Hours));
			int beforeDates = daoDates.count().intValue();
			List<StatisticResultNodeBean> stats = new ArrayList<StatisticResultNodeBean>(
					soaStatistics.productStat(filter));
			Assert.assertEquals(4, stats.size());
			List<Integer> counts = Arrays.asList(4, 0, 2, 0);
			for (int index = 0; index < stats.size(); index++) {
				Assert.assertEquals(1, stats.get(index).getMeasures().size());
				Assert.assertEquals(Long.valueOf(counts.get(index).intValue()),
						stats.get(index).getMeasures().iterator().next().getValue());
			}
			Assert.assertEquals(beforeDates, daoDates.count().intValue());
		}
	}

	@Test
	/**
	 * No transaction here (inside processors)
	 * 
	 * @throws Exception
	 */
	public void testNbProductsSoldDays() throws Exception {
		List<MutableDateTime> dates = new ArrayList<MutableDateTime>();
		// 2 today and 1 after tomorrow
		{
			MutableDateTime date = new MutableDateTime();
			dates.add(date);
		}
		{
			MutableDateTime date = new MutableDateTime();
			dates.add(date);
		}
		{
			MutableDateTime date = new MutableDateTime();
			date.addDays(2);
			dates.add(date);
		}
		// Not take care of that
		{
			MutableDateTime date = new MutableDateTime();
			date.addDays(10);
			dates.add(date);
		}
		helper.addOrder(menu, test.getAllRestaurants().get(TestScenarios.RESTO1), dates, 2);
		//
		{
			StatisticsFilterBean filter = new StatisticsFilterBean();
			{
				MutableDateTime begin = new MutableDateTime();
				filter.setFrom(begin.toDate());
			}
			{
				MutableDateTime end = new MutableDateTime();
				end.addDays(3);
				filter.setTo(end.toDate());
			}
			filter.getMeasureTypes().add(MeasureType.CountProduct);
			filter.getDimensions().put(DimensionType.Period, DimensionPeriodValue.Day);
			filter.setIdProduct(Arrays.asList(this.menu.getId()));
			filter.getOrders().add(new StatisticOrderBean(StatisticOrderDirection.Asc, StatisticOrderType.Days));
			int beforeDates = daoDates.count().intValue();
			List<StatisticResultNodeBean> stats = new ArrayList<StatisticResultNodeBean>(
					soaStatistics.productStat(filter));
			System.out.println(filter.getFrom());
			System.out.println(filter.getTo());
			System.out.println(this.menu.getId());
			System.out.println(filter.getIdDates());
			System.out.println(stats);
			Assert.assertEquals(4, stats.size());
			List<Integer> counts = Arrays.asList(4, 0, 2, 0);
			for (int index = 0; index < stats.size(); index++) {
				Assert.assertEquals(1, stats.get(index).getMeasures().size());
				Assert.assertEquals(Long.valueOf(counts.get(index).intValue()),
						stats.get(index).getMeasures().iterator().next().getValue());
			}
			Assert.assertEquals(beforeDates, daoDates.count().intValue());
		}
	}

	@Test
	/**
	 * No transaction here (inside processors)
	 * 
	 * @throws Exception
	 */
	public void testNbProductsSoldWeeks() throws Exception {
		List<MutableDateTime> dates = new ArrayList<MutableDateTime>();
		// 2 today and 1 after tomorrow
		{
			MutableDateTime date = new MutableDateTime();
			dates.add(date);
		}
		{
			MutableDateTime date = new MutableDateTime();
			dates.add(date);
		}
		{
			MutableDateTime date = new MutableDateTime();
			date.addWeeks(2);
			dates.add(date);
		}
		// Not take care of that
		{
			MutableDateTime date = new MutableDateTime();
			date.addWeeks(10);
			dates.add(date);
		}
		helper.addOrder(menu, test.getAllRestaurants().get(TestScenarios.RESTO1), dates, 2);
		//
		{
			StatisticsFilterBean filter = new StatisticsFilterBean();
			{
				MutableDateTime begin = new MutableDateTime();
				filter.setFrom(begin.toDate());
			}
			{
				MutableDateTime end = new MutableDateTime();
				end.addWeeks(3);
				filter.setTo(end.toDate());
			}
			filter.getMeasureTypes().add(MeasureType.CountProduct);
			filter.getDimensions().put(DimensionType.Period, DimensionPeriodValue.Week);
			filter.setIdProduct(Arrays.asList(this.menu.getId()));
			filter.getOrders().add(new StatisticOrderBean(StatisticOrderDirection.Asc, StatisticOrderType.Weeks));
			int beforeDates = daoDates.count().intValue();
			List<StatisticResultNodeBean> stats = new ArrayList<StatisticResultNodeBean>(
					soaStatistics.productStat(filter));
			System.out.println(filter.getFrom());
			System.out.println(filter.getTo());
			System.out.println(this.menu.getId());
			System.out.println(filter.getIdDates());
			System.out.println(stats);
			Assert.assertEquals(4, stats.size());
			List<Integer> counts = Arrays.asList(4, 0, 2, 0);
			for (int index = 0; index < stats.size(); index++) {
				Assert.assertEquals(1, stats.get(index).getMeasures().size());
				Assert.assertEquals(Long.valueOf(counts.get(index).intValue()),
						stats.get(index).getMeasures().iterator().next().getValue());
			}
			Assert.assertEquals(beforeDates, daoDates.count().intValue());
		}
	}

	@Test
	/**
	 * No transaction here (inside processors)
	 * 
	 * @throws Exception
	 */
	public void testNbProductsSoldMonths() throws Exception {
		List<MutableDateTime> dates = new ArrayList<MutableDateTime>();
		// 2 today and 1 after tomorrow
		{
			MutableDateTime date = new MutableDateTime();
			dates.add(date);
		}
		{
			MutableDateTime date = new MutableDateTime();
			dates.add(date);
		}
		{
			MutableDateTime date = new MutableDateTime();
			date.addMonths(2);
			dates.add(date);
		}
		// Not take care of that
		{
			MutableDateTime date = new MutableDateTime();
			date.addMonths(10);
			dates.add(date);
		}
		helper.addOrder(menu, test.getAllRestaurants().get(TestScenarios.RESTO1), dates, 2);
		//
		{
			StatisticsFilterBean filter = new StatisticsFilterBean();
			{
				MutableDateTime begin = new MutableDateTime();
				filter.setFrom(begin.toDate());
			}
			{
				MutableDateTime end = new MutableDateTime();
				end.addMonths(3);
				filter.setTo(end.toDate());
			}
			filter.getMeasureTypes().add(MeasureType.CountProduct);
			filter.getDimensions().put(DimensionType.Period, DimensionPeriodValue.Month);
			filter.setIdProduct(Arrays.asList(this.menu.getId()));
			filter.getOrders().add(new StatisticOrderBean(StatisticOrderDirection.Asc, StatisticOrderType.Months));
			int beforeDates = daoDates.count().intValue();
			List<StatisticResultNodeBean> stats = new ArrayList<StatisticResultNodeBean>(
					soaStatistics.productStat(filter));
			System.out.println(filter.getFrom());
			System.out.println(filter.getTo());
			System.out.println(this.menu.getId());
			System.out.println(filter.getIdDates());
			System.out.println(stats);
			Assert.assertEquals(4, stats.size());
			List<Integer> counts = Arrays.asList(4, 0, 2, 0);
			for (int index = 0; index < stats.size(); index++) {
				Assert.assertEquals(1, stats.get(index).getMeasures().size());
				Assert.assertEquals(Long.valueOf(counts.get(index).intValue()),
						stats.get(index).getMeasures().iterator().next().getValue());
			}
			Assert.assertEquals(beforeDates, daoDates.count().intValue());
		}
	}

	@Test
	/**
	 * No transaction here (inside processors)
	 * 
	 * @throws Exception
	 */
	public void testNbProductsSoldYears() throws Exception {
		List<MutableDateTime> dates = new ArrayList<MutableDateTime>();
		// 2 today and 1 after tomorrow
		{
			MutableDateTime date = new MutableDateTime();
			dates.add(date);
		}
		{
			MutableDateTime date = new MutableDateTime();
			dates.add(date);
		}
		{
			MutableDateTime date = new MutableDateTime();
			date.addYears(2);
			dates.add(date);
		}
		// Not take care of that
		{
			MutableDateTime date = new MutableDateTime();
			date.addYears(10);
			dates.add(date);
		}
		helper.addOrder(menu, test.getAllRestaurants().get(TestScenarios.RESTO1), dates, 2);
		//
		{
			StatisticsFilterBean filter = new StatisticsFilterBean();
			{
				MutableDateTime begin = new MutableDateTime();
				filter.setFrom(begin.toDate());
			}
			{
				MutableDateTime end = new MutableDateTime();
				end.addYears(3);
				filter.setTo(end.toDate());
			}
			filter.getMeasureTypes().add(MeasureType.CountProduct);
			filter.getDimensions().put(DimensionType.Period, DimensionPeriodValue.Year);
			filter.setIdProduct(Arrays.asList(this.menu.getId()));
			filter.getOrders().add(new StatisticOrderBean(StatisticOrderDirection.Asc, StatisticOrderType.Years));
			int beforeDates = daoDates.count().intValue();
			List<StatisticResultNodeBean> stats = new ArrayList<StatisticResultNodeBean>(
					soaStatistics.productStat(filter));
			System.out.println(filter.getFrom());
			System.out.println(filter.getTo());
			System.out.println(this.menu.getId());
			System.out.println(filter.getIdDates());
			System.out.println(stats);
			Assert.assertEquals(4, stats.size());
			List<Integer> counts = Arrays.asList(4, 0, 2, 0);
			for (int index = 0; index < stats.size(); index++) {
				Assert.assertEquals(1, stats.get(index).getMeasures().size());
				Assert.assertEquals(Long.valueOf(counts.get(index).intValue()),
						stats.get(index).getMeasures().iterator().next().getValue());
			}
			Assert.assertEquals(beforeDates, daoDates.count().intValue());
		}
	}

}
