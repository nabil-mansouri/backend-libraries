package com.rm.buiseness.statistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

import com.nm.app.stats.DimensionType;
import com.nm.app.stats.MeasureType;
import com.rm.buiseness.commons.TestScenarios;
import com.rm.buiseness.commons.TestUrlUtils;
import com.rm.contract.products.views.ProductViewDto;
import com.rm.contract.statistics.beans.StatisticOrderBean;
import com.rm.contract.statistics.beans.StatisticResultNodeBean;
import com.rm.contract.statistics.beans.StatisticsFilterBean;
import com.rm.contract.statistics.constants.DimensionProductValue;
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
public class TestStatisticsProducts {

	@Autowired
	private SoaStatistics soaStatistics;
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
		// menu = test.getAllProducts().get(TestScenarios.MENU_MOYEN);
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
	public void testBestCountProducts() throws Exception {
		ProductViewDto tacos = test.getAllProductsView().get(TestScenarios.TACOS);
		ProductViewDto menuEt = test.getAllProductsView().get(TestScenarios.MENUS_ETUDIANTS);
//		for (ProductPartViewDto part : menuEt.parts()) {
//			part.setSelected(part.getProducts().get(0));
//		}
		helper.addOrder(Arrays.asList(menu, tacos), test.getAllRestaurants().get(TestScenarios.RESTO1),
				Arrays.asList(1, 2));
		helper.addOrder(Arrays.asList(menuEt, tacos), test.getAllRestaurants().get(TestScenarios.RESTO1),
				Arrays.asList(2, 2));
		//
		{
			StatisticsFilterBean filter = new StatisticsFilterBean();
			filter.getMeasureTypes().add(MeasureType.CountProduct);
			filter.getDimensions().put(DimensionType.Product, DimensionProductValue.Product);
			filter.getOrders()
					.add(new StatisticOrderBean(StatisticOrderDirection.Desc, StatisticOrderType.ProductTotalCount));
			List<StatisticResultNodeBean> stats = new ArrayList<StatisticResultNodeBean>(
					soaStatistics.productStat(filter));
			Assert.assertEquals(3, stats.size());
			List<ProductViewDto> counts = Arrays.asList(tacos, menuEt, menu);
			System.out.println(stats);
			for (int index = 0; index < stats.size(); index++) {
				Assert.assertEquals(counts.get(index).getId(),
						stats.get(index).getDimensions().iterator().next().getValue());
			}
		}
	}

	@Test
	/**
	 * No transaction here (inside processors)
	 * 
	 * @throws Exception
	 */
	public void testBestSumProducts() throws Exception {
		ProductViewDto tacos = test.getAllProductsView().get(TestScenarios.TACOS);
		ProductViewDto menuEt = test.getAllProductsView().get(TestScenarios.MENUS_ETUDIANTS);
		// for (ProductPartViewDto part : menuEt.parts()) {
		// part.setSelected(part.getProducts().get(0));
		// }
		helper.addOrder(Arrays.asList(menu, tacos), test.getAllRestaurants().get(TestScenarios.RESTO1),
				Arrays.asList(1, 2));
		helper.addOrder(Arrays.asList(menuEt, tacos), test.getAllRestaurants().get(TestScenarios.RESTO1),
				Arrays.asList(2, 3));
		//
		{
			StatisticsFilterBean filter = new StatisticsFilterBean();
			filter.getMeasureTypes().add(MeasureType.TotalAmountProduct);
			filter.getDimensions().put(DimensionType.Product, DimensionProductValue.Product);
			filter.getOrders()
					.add(new StatisticOrderBean(StatisticOrderDirection.Desc, StatisticOrderType.ProductTotalPrice));
			List<StatisticResultNodeBean> stats = new ArrayList<StatisticResultNodeBean>(
					soaStatistics.productStat(filter));
			Assert.assertEquals(3, stats.size());
			List<ProductViewDto> counts = Arrays.asList(tacos, menuEt, menu);
			System.out.println(stats);
			System.out.println(counts);
			for (int index = 0; index < stats.size(); index++) {
				Assert.assertEquals(counts.get(index).getId(),
						stats.get(index).getDimensions().iterator().next().getValue());
			}
		}
	}
}
