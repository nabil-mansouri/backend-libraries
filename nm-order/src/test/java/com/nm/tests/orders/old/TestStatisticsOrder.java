package com.nm.tests.orders.old;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nm.utils.tests.TestUrlUtils;

/**
 * 
 * @author Nabil
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TestUrlUtils.PROFILE_TEST)
//@ContextConfiguration(locations = { TestUrlUtils.CONTEXT_PATH_TEST })
public class TestStatisticsOrder {
	// TODO
	// @Autowired
	// private DaoOrder daoOrder;
	// @Autowired
	// private SoaStatistics soaStatistics;
	// @Autowired
	// private DaoDates daoDates;
	// @Autowired
	// private DateComputer dateSetter;
	// private TestScenarios test;
	// private TestStatisticHelper helper;
	// @Autowired
	// private ApplicationContext applicationContext;
	// private ProductViewDto menu;
	// private ProductViewDto menuEt;
	//
	// //
	// @Before
	// public void setUp() throws Exception {
	// ConfigurableListableBeanFactory beanFactory =
	// ((ConfigurableApplicationContext) applicationContext)
	// .getBeanFactory();
	// test = beanFactory.createBean(TestScenarios.class);
	// helper = beanFactory.createBean(TestStatisticHelper.class);
	// //
	// helper.deleteAllOrders();
	// test.testCreate();
	// menu = test.getAllProductsView().get(TestScenarios.MENU_MOYEN);
	// // for (ProductPartViewDto part : menu.parts()) {
	// // part.setSelected(part.getProducts().get(0));
	// // }
	// // menuEt = test.getAllProducts().get(TestScenarios.MENUS_ETUDIANTS);
	// // for (ProductPartViewDto part : menuEt.parts()) {
	// // part.setSelected(part.getProducts().get(0));
	// // }
	// }
	//
	// @After
	// public void tearDown() throws NoDataFoundException {
	// helper.deleteAllOrders();
	// }
	//
	// //@Test
	// /**
	// * No transaction here (inside processors)
	// *
	// * @throws Exception
	// */
	// public void testCountOrders() throws Exception {
	// List<MutableDateTime> dates = new ArrayList<MutableDateTime>();
	// // 2 today and 1 after tomorrow
	// {
	// MutableDateTime date = new MutableDateTime();
	// dates.add(date);
	// }
	// {
	// MutableDateTime date = new MutableDateTime();
	// dates.add(date);
	// }
	// {
	// MutableDateTime date = new MutableDateTime();
	// date.addDays(2);
	// dates.add(date);
	// }
	// // Not take care of that
	// {
	// MutableDateTime date = new MutableDateTime();
	// date.addDays(10);
	// dates.add(date);
	// }
	// helper.addOrder(menu, test.getAllRestaurants().get(TestScenarios.RESTO1),
	// dates, 2);
	// //
	// {
	// StatisticsFilterBean filter = new StatisticsFilterBean();
	// {
	// MutableDateTime begin = new MutableDateTime();
	// filter.setFrom(begin.toDate());
	// }
	// {
	// MutableDateTime end = new MutableDateTime();
	// end.addDays(3);
	// filter.setTo(end.toDate());
	// }
	// filter.getMeasureTypes().add(MeasureType.CountOrder);
	// filter.getDimensions().put(DimensionType.Period,
	// DimensionPeriodValue.Day);
	// filter.getOrders().add(new
	// StatisticOrderBean(StatisticOrderDirection.Asc,
	// StatisticOrderType.Days));
	// int beforeDates = daoDates.count().intValue();
	// List<StatisticResultNodeBean> stats = new
	// ArrayList<StatisticResultNodeBean>(
	// soaStatistics.orderStat(filter));
	// Assert.assertEquals(4, stats.size());
	// List<Integer> counts = Arrays.asList(2, 0, 1, 0);
	// for (int index = 0; index < stats.size(); index++) {
	// Assert.assertEquals(1, stats.get(index).getMeasures().size());
	// Assert.assertEquals(Long.valueOf(counts.get(index).intValue()),
	// stats.get(index).getMeasures().iterator().next().getValue());
	// }
	// Assert.assertEquals(beforeDates, daoDates.count().intValue());
	// }
	// }
	//
	// //@Test
	// /**
	// * No transaction here (inside processors)
	// *
	// * @throws Exception
	// */
	// public void testSumOrders() throws Exception {
	// List<MutableDateTime> dates = new ArrayList<MutableDateTime>();
	// // 2 today and 1 after tomorrow
	// {
	// MutableDateTime date = new MutableDateTime();
	// dates.add(date);
	// }
	// {
	// MutableDateTime date = new MutableDateTime();
	// dates.add(date);
	// }
	// {
	// MutableDateTime date = new MutableDateTime();
	// date.addDays(2);
	// dates.add(date);
	// }
	// // Not take care of that
	// {
	// MutableDateTime date = new MutableDateTime();
	// date.addDays(10);
	// dates.add(date);
	// }
	// helper.addOrder(menuEt,
	// test.getAllRestaurants().get(TestScenarios.RESTO1), dates, 2);
	// //
	// {
	// StatisticsFilterBean filter = new StatisticsFilterBean();
	// {
	// MutableDateTime begin = new MutableDateTime();
	// filter.setFrom(begin.toDate());
	// }
	// {
	// MutableDateTime end = new MutableDateTime();
	// end.addDays(3);
	// filter.setTo(end.toDate());
	// }
	// filter.getMeasureTypes().add(MeasureType.TotalAmountOrder);
	// filter.getDimensions().put(DimensionType.Period,
	// DimensionPeriodValue.Day);
	// filter.getOrders().add(new
	// StatisticOrderBean(StatisticOrderDirection.Asc,
	// StatisticOrderType.Days));
	// int beforeDates = daoDates.count().intValue();
	// List<StatisticResultNodeBean> stats = new
	// ArrayList<StatisticResultNodeBean>(
	// soaStatistics.orderStat(filter));
	// Assert.assertEquals(4, stats.size());
	// List<Integer> counts = Arrays.asList(40, null, 20, null);
	// Assert.assertEquals(20d,
	// daoOrder.findAll().iterator().next().getTotal().doubleValue(), 0d);
	// System.out.println(stats);
	// for (int index = 0; index < stats.size(); index++) {
	// Assert.assertEquals(1, stats.get(index).getMeasures().size());
	// if (counts.get(index) == null) {
	// Assert.assertNull(stats.get(index).getMeasures().iterator().next().getValue());
	// } else {
	// Double d = (Double)
	// stats.get(index).getMeasures().iterator().next().getValue();
	// Assert.assertEquals(Double.valueOf(counts.get(index).doubleValue()), d,
	// 0);
	// }
	// }
	// Assert.assertEquals(beforeDates, daoDates.count().intValue());
	// }
	// }
	//
	// //@Test
	// /**
	// * No transaction here (inside processors)
	// *
	// * @throws Exception
	// */
	// public void testBestDaySum() throws Exception {
	// List<MutableDateTime> dates = new ArrayList<MutableDateTime>();
	// // 2 today and 1 after tomorrow
	// {
	// MutableDateTime date = new MutableDateTime();
	// dates.add(date);
	// }
	// {
	// MutableDateTime date = new MutableDateTime();
	// date.addDays(1);
	// dates.add(date);
	// }
	// {
	// MutableDateTime date = new MutableDateTime();
	// date.addDays(2);
	// dates.add(date);
	// }
	// // Not take care of that
	// {
	// MutableDateTime date = new MutableDateTime();
	// date.addDays(10);
	// dates.add(date);
	// }
	// List<Integer> times = Arrays.asList(2, 3, 4, 5);
	// helper.addOrder(menu, test.getAllRestaurants().get(TestScenarios.RESTO1),
	// dates, times);
	// //
	// {
	// StatisticsFilterBean filter = new StatisticsFilterBean();
	// {
	// MutableDateTime begin = new MutableDateTime();
	// filter.setFrom(begin.toDate());
	// }
	// {
	// MutableDateTime end = new MutableDateTime();
	// end.addDays(3);
	// filter.setTo(end.toDate());
	// }
	// filter.getMeasureTypes().add(MeasureType.TotalAmountOrder);
	// filter.getDimensions().put(DimensionType.PeriodTransaction,
	// DimensionPeriodTransationValue.Day);
	// filter.getOrders()
	// .add(new StatisticOrderBean(StatisticOrderDirection.Desc,
	// StatisticOrderType.OrderTotalPrice));
	// int beforeDates = daoDates.count().intValue();
	// List<StatisticResultNodeBean> stats = new
	// ArrayList<StatisticResultNodeBean>(
	// soaStatistics.orderStat(filter));
	// Assert.assertEquals(3, stats.size());
	// List<MutableDateTime> counts = Arrays.asList(dates.get(2), dates.get(1),
	// dates.get(0));
	// System.out.println(stats);
	// for (int index = 0; index < stats.size(); index++) {
	// dateSetter.startDays(counts.get(index));
	// Assert.assertEquals(counts.get(index).toDate(),
	// stats.get(index).getDimensions().iterator().next().getValue());
	// }
	// Assert.assertEquals(beforeDates, daoDates.count().intValue());
	// }
	// }
	//
	// //@Test
	// /**
	// * No transaction here (inside processors)
	// *
	// * @throws Exception
	// */
	// public void testBestCount() throws Exception {
	// List<MutableDateTime> dates = new ArrayList<MutableDateTime>();
	// // 3 today and 1 tomorrow,2 next tomorrow
	// {
	// MutableDateTime date = new MutableDateTime();
	// dates.add(date);
	// }
	// {
	// MutableDateTime date = new MutableDateTime();
	// dates.add(date);
	// }
	// {
	// MutableDateTime date = new MutableDateTime();
	// dates.add(date);
	// }
	// //
	// {
	// MutableDateTime date = new MutableDateTime();
	// date.addDays(1);
	// dates.add(date);
	// }
	// //
	// {
	// MutableDateTime date = new MutableDateTime();
	// date.addDays(2);
	// dates.add(date);
	// }
	// {
	// MutableDateTime date = new MutableDateTime();
	// date.addDays(2);
	// dates.add(date);
	// }
	// // Not take care of that
	// {
	// MutableDateTime date = new MutableDateTime();
	// date.addDays(10);
	// dates.add(date);
	// }
	// List<Integer> times = Arrays.asList(2, 1, 1, 5, 1, 1, 1);
	// helper.addOrder(menu, test.getAllRestaurants().get(TestScenarios.RESTO1),
	// dates, times);
	// //
	// {
	// StatisticsFilterBean filter = new StatisticsFilterBean();
	// {
	// MutableDateTime begin = new MutableDateTime();
	// filter.setFrom(begin.toDate());
	// }
	// {
	// MutableDateTime end = new MutableDateTime();
	// end.addDays(3);
	// filter.setTo(end.toDate());
	// }
	// filter.getMeasureTypes().add(MeasureType.CountOrder);
	// filter.getDimensions().put(DimensionType.PeriodTransaction,
	// DimensionPeriodTransationValue.Day);
	// filter.getOrders()
	// .add(new StatisticOrderBean(StatisticOrderDirection.Desc,
	// StatisticOrderType.OrderTotalCount));
	// int beforeDates = daoDates.count().intValue();
	// List<StatisticResultNodeBean> stats = new
	// ArrayList<StatisticResultNodeBean>(
	// soaStatistics.orderStat(filter));
	// Assert.assertEquals(3, stats.size());
	// List<MutableDateTime> counts = Arrays.asList(dates.get(0), dates.get(4),
	// dates.get(3));
	// System.out.println("----------------------");
	// System.out.println(stats);
	// for (int index = 0; index < stats.size(); index++) {
	// dateSetter.startDays(counts.get(index));
	// Assert.assertEquals(counts.get(index).toDate(),
	// stats.get(index).getDimensions().iterator().next().getValue());
	// }
	// Assert.assertEquals(beforeDates, daoDates.count().intValue());
	// }
	// }
}
