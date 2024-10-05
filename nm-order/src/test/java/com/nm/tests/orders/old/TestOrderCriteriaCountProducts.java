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
public class TestOrderCriteriaCountProducts {

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
	// private ProductViewDto menuEt;
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
	// // menu = test.getAllProducts().get(TestScenarios.MENU_MOYEN);
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
	// public void testTotalBetween() throws Exception {
	// OrderCriteriaRulesBean rules = new
	// OrderCriteriaRulesBean().put(OrderCriteriaType.CountProducts,
	// new
	// OrderCriteriaRuleBean().setEnable(true).setHasFromQty(true).setFromQty(2d).setHasToQty(true)
	// .setToQty(5d));
	// rules = soaOrder.save(rules);
	// OrderCriterias c = daoOrderCriterias.loadById(rules.getId());
	// //
	// CartBean cart = new CartBean();
	// cart.setType(OrderType.InPlace);
	// cart.setRestaurant(test.getAllRestaurants().get(TestScenarios.RESTO1));
	// // Total => 5
	// for (int i = 0; i < 5; i++) {
	// CartProductBean cartP = new CartProductBean();
	// cartP.setProduct(menuEt);
	// cart.getDetails().add(cartP);
	// }
	// OrderCriteriaContext context = orderCriteriaChecker.check(c, cart);
	// Assert.assertTrue(context.isValid());
	// // Total => 0 => nok
	// cart.getDetails().clear();
	// context = orderCriteriaChecker.check(c, cart);
	// Assert.assertFalse(context.isValid());
	// // Total => 6
	// for (int i = 0; i < 6; i++) {
	// CartProductBean cartP = new CartProductBean();
	// cartP.setProduct(menuEt);
	// cart.getDetails().add(cartP);
	// }
	// context = orderCriteriaChecker.check(c, cart);
	// Assert.assertFalse(context.isValid());
	// }
	//
	// //@Test
	// @Transactional
	// public void testTotalFrom() throws Exception {
	// OrderCriteriaRulesBean rules = new
	// OrderCriteriaRulesBean().put(OrderCriteriaType.CountProducts,
	// new
	// OrderCriteriaRuleBean().setEnable(true).setHasFromQty(true).setFromQty(5d));
	// rules = soaOrder.save(rules);
	// OrderCriterias c = daoOrderCriterias.loadById(rules.getId());
	// //
	// CartBean cart = new CartBean();
	// cart.setType(OrderType.InPlace);
	// cart.setRestaurant(test.getAllRestaurants().get(TestScenarios.RESTO1));
	// // Total => 5 => ok
	// for (int i = 0; i < 5; i++) {
	// CartProductBean cartP = new CartProductBean();
	// cartP.setProduct(menuEt);
	// cart.getDetails().add(cartP);
	// }
	// OrderCriteriaContext context = orderCriteriaChecker.check(c, cart);
	// Assert.assertTrue(context.isValid());
	// // Total =>0 => nok
	// cart.getDetails().clear();
	// context = orderCriteriaChecker.check(c, cart);
	// Assert.assertFalse(context.isValid());
	// }
	//
	// //@Test
	// @Transactional
	// public void testTotalTo() throws Exception {
	// OrderCriteriaRulesBean rules = new
	// OrderCriteriaRulesBean().put(OrderCriteriaType.CountProducts,
	// new
	// OrderCriteriaRuleBean().setEnable(true).setHasToQty(true).setToQty(5d));
	// rules = soaOrder.save(rules);
	// OrderCriterias c = daoOrderCriterias.loadById(rules.getId());
	// //
	// CartBean cart = new CartBean();
	// cart.setType(OrderType.InPlace);
	// cart.setRestaurant(test.getAllRestaurants().get(TestScenarios.RESTO1));
	// // Total => 5=>ok
	// for (int i = 0; i < 5d; i++) {
	// CartProductBean cartP = new CartProductBean();
	// cartP.setProduct(menuEt);
	// cart.getDetails().add(cartP);
	// }
	// OrderCriteriaContext context = orderCriteriaChecker.check(c, cart);
	// Assert.assertTrue(context.isValid());
	// // Total => 6
	// for (int i = 0; i < 6; i++) {
	// CartProductBean cartP = new CartProductBean();
	// cartP.setProduct(menuEt);
	// cart.getDetails().add(cartP);
	// }
	// context = orderCriteriaChecker.check(c, cart);
	// Assert.assertFalse(context.isValid());
	// }
}
