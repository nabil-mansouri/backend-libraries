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
public class TestOrderCriteriaType {

	// @Autowired
	// private OrderCriteriaCheckerProcessor orderCriteriaChecker;
	// private TestScenarios test;
	// @Autowired
	// private ApplicationContext applicationContext;
	// @Autowired
	// private SoaOrder soaOrder;
	// @Autowired
	// private DaoOrderCriterias daoOrderCriterias;
	// //
	// protected Log log = LogFactory.getLog(getClass());
	// protected ProductViewDto menu;
	// protected ProductViewDto menuEt;
	//
	// @Before
	// public void globalSetUp() throws Exception {
	// //
	// //
	// ConfigurableListableBeanFactory beanFactory =
	// ((ConfigurableApplicationContext) applicationContext)
	// .getBeanFactory();
	// test = beanFactory.createBean(TestScenarios.class);
	// //
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
	// //@Test
	// @Transactional
	// public void testNotOrderedSinceEmptyCriteria() throws Exception {
	// OrderCriterias crit = new OrderCriterias();
	// OrderCriteriaContext context = orderCriteriaChecker.check(crit, new
	// CartBean());
	// Assert.assertTrue(context.isValid());
	// }
	//
	// //@Test
	// @Transactional
	// public void testOrderTypeIn() throws Exception {
	// OrderCriteriaRulesBean rules = new
	// OrderCriteriaRulesBean().put(OrderCriteriaType.OrderType,
	// new
	// OrderCriteriaRuleBean().setEnable(true).setHasType(true).addType(OrderType.InPlace)
	// .setTypeOp(LogicalOperatorType.In));
	// rules = soaOrder.save(rules);
	// OrderCriterias c = daoOrderCriterias.loadById(rules.getId());
	// //
	// CartBean cart = new CartBean();
	// cart.setType(OrderType.InPlace);
	// OrderCriteriaContext context = orderCriteriaChecker.check(c, cart);
	// Assert.assertTrue(context.isValid());
	// cart.setType(OrderType.Delivered);
	// context = orderCriteriaChecker.check(c, cart);
	// Assert.assertFalse(context.isValid());
	// }
	//
	// //@Test
	// @Transactional
	// public void testOrderTypeNotIn() throws Exception {
	// OrderCriteriaRulesBean rules = new
	// OrderCriteriaRulesBean().put(OrderCriteriaType.OrderType,
	// new
	// OrderCriteriaRuleBean().setEnable(true).setHasType(true).addType(OrderType.InPlace)
	// .setTypeOp(LogicalOperatorType.NotIn));
	// rules = soaOrder.save(rules);
	// OrderCriterias c = daoOrderCriterias.loadById(rules.getId());
	// //
	// CartBean cart = new CartBean();
	// cart.setType(OrderType.InPlace);
	// OrderCriteriaContext context = orderCriteriaChecker.check(c, cart);
	// Assert.assertFalse(context.isValid());
	// cart.setType(OrderType.Delivered);
	// context = orderCriteriaChecker.check(c, cart);
	// Assert.assertTrue(context.isValid());
	// }
}
