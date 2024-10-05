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
public class TestOrderCriteriaHavingProduct {

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
	// private ProductViewDto menu;
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
	// public void testProductBetween() throws Exception {
	// OrderCriteriaRulesBean rules = new
	// OrderCriteriaRulesBean().put(OrderCriteriaType.HavingProducts,
	// new OrderCriteriaRuleBean().setEnable(true).setHasProductRule(true)
	// .addProductRule(new
	// OrderCriteriaProductRuleBean().addProductIds(menuEt.getId())
	// .setHasFromQty(true).setFromQty(1d).setHasToQty(true).setToQty(2d)));
	// rules = soaOrder.save(rules);
	// OrderCriterias c = daoOrderCriterias.loadById(rules.getId());
	// //
	// CartBean cart = new CartBean();
	// cart.setType(OrderType.InPlace);
	// cart.setRestaurant(test.getAllRestaurants().get(TestScenarios.RESTO1));
	// // 1 product => ok
	// for (int i = 0; i < 1; i++) {
	// CartProductBean cartP = new CartProductBean();
	// cartP.setProduct(menuEt);
	// cart.getDetails().add(cartP);
	// CartProductBean cartP1 = new CartProductBean();
	// cartP1.setProduct(menu);
	// cart.getDetails().add(cartP1);
	// }
	// //
	// OrderCriteriaContext context = orderCriteriaChecker.check(c, cart);
	// Assert.assertTrue(context.isValid());
	// // 0 product => nok
	// cart.getDetails().clear();
	// context = orderCriteriaChecker.check(c, cart);
	// Assert.assertFalse(context.isValid());
	// // 3 product => nok
	// for (int i = 0; i < 3; i++) {
	// CartProductBean cartP = new CartProductBean();
	// cartP.setProduct(menuEt);
	// cart.getDetails().add(cartP);
	// CartProductBean cartP1 = new CartProductBean();
	// cartP1.setProduct(menu);
	// cart.getDetails().add(cartP1);
	// }
	// context = orderCriteriaChecker.check(c, cart);
	// Assert.assertFalse(context.isValid());
	// }
	//
	// //@Test
	// @Transactional
	// public void testTotalFrom() throws Exception {
	// OrderCriteriaRulesBean rules = new
	// OrderCriteriaRulesBean().put(OrderCriteriaType.HavingProducts,
	// new OrderCriteriaRuleBean().setEnable(true).setHasProductRule(true)
	// .addProductRule(new
	// OrderCriteriaProductRuleBean().addProductIds(menuEt.getId())
	// .setHasFromQty(true).setFromQty(1d)));
	// rules = soaOrder.save(rules);
	// OrderCriterias c = daoOrderCriterias.loadById(rules.getId());
	// //
	// CartBean cart = new CartBean();
	// cart.setType(OrderType.InPlace);
	// cart.setRestaurant(test.getAllRestaurants().get(TestScenarios.RESTO1));
	// // 1 product => ok
	// for (int i = 0; i < 1; i++) {
	// CartProductBean cartP = new CartProductBean();
	// cartP.setProduct(menuEt);
	// cart.getDetails().add(cartP);
	// CartProductBean cartP1 = new CartProductBean();
	// cartP1.setProduct(menu);
	// cart.getDetails().add(cartP1);
	// }
	// //
	// OrderCriteriaContext context = orderCriteriaChecker.check(c, cart);
	// Assert.assertTrue(context.isValid());
	// // 0 product => nok
	// cart.getDetails().clear();
	// context = orderCriteriaChecker.check(c, cart);
	// Assert.assertFalse(context.isValid());
	// // 3 product => ok
	// for (int i = 0; i < 3; i++) {
	// CartProductBean cartP = new CartProductBean();
	// cartP.setProduct(menuEt);
	// cart.getDetails().add(cartP);
	// CartProductBean cartP1 = new CartProductBean();
	// cartP1.setProduct(menu);
	// cart.getDetails().add(cartP1);
	// }
	// context = orderCriteriaChecker.check(c, cart);
	// Assert.assertTrue(context.isValid());
	// }
	//
	// //@Test
	// @Transactional
	// public void testTotalTo() throws Exception {
	// OrderCriteriaRulesBean rules = new
	// OrderCriteriaRulesBean().put(OrderCriteriaType.HavingProducts,
	// new OrderCriteriaRuleBean().setEnable(true).setHasProductRule(true)
	// .addProductRule(new
	// OrderCriteriaProductRuleBean().addProductIds(menuEt.getId())
	// .setHasToQty(true).setToQty(1d)));
	// rules = soaOrder.save(rules);
	// OrderCriterias c = daoOrderCriterias.loadById(rules.getId());
	// //
	// CartBean cart = new CartBean();
	// cart.setType(OrderType.InPlace);
	// cart.setRestaurant(test.getAllRestaurants().get(TestScenarios.RESTO1));
	// // 1 product => ok
	// for (int i = 0; i < 1; i++) {
	// CartProductBean cartP = new CartProductBean();
	// cartP.setProduct(menuEt);
	// cart.getDetails().add(cartP);
	// CartProductBean cartP1 = new CartProductBean();
	// cartP1.setProduct(menu);
	// cart.getDetails().add(cartP1);
	// }
	// //
	// OrderCriteriaContext context = orderCriteriaChecker.check(c, cart);
	// Assert.assertTrue(context.isValid());
	// // 0 product => ok
	// cart.getDetails().clear();
	// context = orderCriteriaChecker.check(c, cart);
	// Assert.assertTrue(context.isValid());
	// // 3 product => nok
	// for (int i = 0; i < 3; i++) {
	// CartProductBean cartP = new CartProductBean();
	// cartP.setProduct(menuEt);
	// cart.getDetails().add(cartP);
	// CartProductBean cartP1 = new CartProductBean();
	// cartP1.setProduct(menu);
	// cart.getDetails().add(cartP1);
	// }
	// context = orderCriteriaChecker.check(c, cart);
	// Assert.assertFalse(context.isValid());
	// }
	//
	// //@Test
	// @Transactional
	// public void testMultipleRules() throws Exception {
	// OrderCriteriaRulesBean rules = new
	// OrderCriteriaRulesBean().put(OrderCriteriaType.HavingProducts,
	// new OrderCriteriaRuleBean().setEnable(true).setHasProductRule(true)
	// .addProductRule(new
	// OrderCriteriaProductRuleBean().addProductIds(menu.getId()).setHasToQty(true)
	// .setToQty(1d))
	// .addProductRule(new
	// OrderCriteriaProductRuleBean().addProductIds(menuEt.getId())
	// .setHasFromQty(true).setFromQty(1d)));
	// rules = soaOrder.save(rules);
	// OrderCriterias c = daoOrderCriterias.loadById(rules.getId());
	// //
	// CartBean cart = new CartBean();
	// cart.setType(OrderType.InPlace);
	// cart.setRestaurant(test.getAllRestaurants().get(TestScenarios.RESTO1));
	// // 1 product => ok
	// for (int i = 0; i < 1; i++) {
	// CartProductBean cartP = new CartProductBean();
	// cartP.setProduct(menuEt);
	// cart.getDetails().add(cartP);
	// CartProductBean cartP1 = new CartProductBean();
	// cartP1.setProduct(menu);
	// cart.getDetails().add(cartP1);
	// }
	// //
	// OrderCriteriaContext context = orderCriteriaChecker.check(c, cart);
	// Assert.assertTrue(context.isValid());
	// // 0 product => ok
	// cart.getDetails().clear();
	// context = orderCriteriaChecker.check(c, cart);
	// Assert.assertFalse(context.isValid());
	// //
	// for (int i = 0; i < 1; i++) {
	// CartProductBean cartP = new CartProductBean();
	// cartP.setProduct(menuEt);
	// cart.getDetails().add(cartP);
	// }
	// for (int i = 0; i < 2; i++) {
	// CartProductBean cartP1 = new CartProductBean();
	// cartP1.setProduct(menu);
	// cart.getDetails().add(cartP1);
	// }
	// context = orderCriteriaChecker.check(c, cart);
	// Assert.assertFalse(context.isValid());
	// }
}
