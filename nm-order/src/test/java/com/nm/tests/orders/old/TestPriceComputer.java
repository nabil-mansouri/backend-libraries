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
public class TestPriceComputer {
	// TODO
	// @Autowired
	// private OldPriceComputerProcessor processor;
	// @Autowired
	// private DaoPrice daoPrice;
	// @Autowired
	// private ApplicationContext applicationContext;
	// private TestScenarios test;
	//
	// //
	// @Before
	// public void setUp() throws Exception {
	// ConfigurableListableBeanFactory beanFactory =
	// ((ConfigurableApplicationContext) applicationContext)
	// .getBeanFactory();
	// test = beanFactory.createBean(TestScenarios.class);
	// test.testCreate();
	// }
	//
	// //@Test
	// @Transactional
	// public void testCOmputeAvailable() throws NoDataFoundException {
	// ProductViewDto menu =
	// test.getAllProductsView().get(TestScenarios.MENU_MOYEN);
	// //
	// Price price = daoPrice
	// .find(PriceQueryBuilder.get().withOnlyCurrent()
	// .withSubject(PriceSubjectQueryBuilder.getProduct().withProduct(menu.getId())))
	// .iterator().next();
	// //
	// PriceComputeBean bean = new PriceComputeBean();
	// bean.setPriceType(OrderType.InPlace);
	// processor.compute(bean, price);
	// assertEquals(10d, bean.getValue().doubleValue(), 0);
	// //
	// bean = new PriceComputeBean();
	// bean.setPriceType(OrderType.InPlace);
	// processor.computeFlexible(bean, price);
	// assertEquals(10d, bean.getValue().doubleValue(), 0);
	// }
	//
	// //@Test
	// @Transactional
	// public void testComputeFlexibleUnavailable() throws NoDataFoundException
	// {
	// ProductViewDto potatoes =
	// test.getAllProductsView().get(TestScenarios.CHOCOLAT);
	// //
	// Price price = daoPrice
	// .find(PriceQueryBuilder.get().withOnlyCurrent()
	// .withSubject(PriceSubjectQueryBuilder.getProduct().withProduct(potatoes.getId())))
	// .iterator().next();
	// //
	// PriceComputeBean bean = new PriceComputeBean();
	// processor.compute(bean, price);
	// assertTrue(bean.getErrors().contains(PriceComputeError.NoPriceForAnyType));
	// //
	// bean = new PriceComputeBean();
	// processor.computeFlexible(bean, price);
	// assertTrue(bean.getErrors().isEmpty());
	// assertEquals(1.95d, bean.getValue().doubleValue(), 0);
	// }
}
