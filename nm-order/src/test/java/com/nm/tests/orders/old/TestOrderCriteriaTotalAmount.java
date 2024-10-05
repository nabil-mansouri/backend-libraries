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
public class TestOrderCriteriaTotalAmount {

	// @Autowired
	// private OrderCriteriaCheckerProcessor orderCriteriaChecker;
	// private TestScenarios test;
	// @Autowired
	// private ApplicationContext applicationContext;
	// @Autowired
	// private SoaOrder soaOrder;
	// @Autowired
	// private DaoOrderCriterias daoOrderCriterias;
	// @Autowired
	// private CartCheckerProcessor processor;
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
	// public void testTotalBetween() throws Exception {
	// OrderCriteriaRulesBean rules = new
	// OrderCriteriaRulesBean().put(OrderCriteriaType.TotalAmount,
	// new
	// OrderCriteriaRuleBean().setEnable(true).setHasFromAmount(true).setFromAmount(5d)
	// .setHasToAmount(true).setToAmount(10d));
	// rules = soaOrder.save(rules);
	// OrderCriterias c = daoOrderCriterias.loadById(rules.getId());
	// //
	// CartBean cart = new CartBean();
	// cart.setType(OrderType.InPlace);
	// cart.setRestaurant(test.getAllRestaurants().get(TestScenarios.RESTO1));
	// // Total => 10
	// for (int i = 0; i < 1; i++) {
	// CartProductBean cartP = new CartProductBean();
	// cartP.setProduct(menuEt);
	// cart.getDetails().add(cartP);
	// }
	// processor.process(cart, "fr");
	// System.out.println("-----------------------------");
	// System.out.println(cart.getTotal());
	// //
	// OrderCriteriaContext context = orderCriteriaChecker.check(c, cart);
	// Assert.assertTrue(context.isValid());
	// // Total =>0
	// cart.getDetails().clear();
	// processor.process(cart, "fr");
	// System.out.println("-----------------------------");
	// System.out.println(cart.getTotal());
	// context = orderCriteriaChecker.check(c, cart);
	// Assert.assertFalse(context.isValid());
	// // Total => 20
	// for (int i = 0; i < 2; i++) {
	// CartProductBean cartP = new CartProductBean();
	// cartP.setProduct(menuEt);
	// cart.getDetails().add(cartP);
	// }
	// processor.process(cart, "fr");
	// System.out.println("-----------------------------");
	// System.out.println(cart.getTotal());
	// context = orderCriteriaChecker.check(c, cart);
	// Assert.assertFalse(context.isValid());
	// }
	//
	// //@Test
	// @Transactional
	// public void testTotalFrom() throws Exception {
	// OrderCriteriaRulesBean rules = new
	// OrderCriteriaRulesBean().put(OrderCriteriaType.TotalAmount,
	// new
	// OrderCriteriaRuleBean().setEnable(true).setHasFromAmount(true).setFromAmount(5d));
	// rules = soaOrder.save(rules);
	// OrderCriterias c = daoOrderCriterias.loadById(rules.getId());
	// //
	// CartBean cart = new CartBean();
	// cart.setType(OrderType.InPlace);
	// cart.setRestaurant(test.getAllRestaurants().get(TestScenarios.RESTO1));
	// // Total => 10
	// for (int i = 0; i < 1; i++) {
	// CartProductBean cartP = new CartProductBean();
	// cartP.setProduct(menuEt);
	// cart.getDetails().add(cartP);
	// }
	// processor.process(cart, "fr");
	// System.out.println("-----------------------------");
	// System.out.println(cart.getTotal());
	// //
	// OrderCriteriaContext context = orderCriteriaChecker.check(c, cart);
	// Assert.assertTrue(context.isValid());
	// // Total =>0
	// cart.getDetails().clear();
	// processor.process(cart, "fr");
	// System.out.println("-----------------------------");
	// System.out.println(cart.getTotal());
	// context = orderCriteriaChecker.check(c, cart);
	// Assert.assertFalse(context.isValid());
	// // Total => 20
	// for (int i = 0; i < 2; i++) {
	// CartProductBean cartP = new CartProductBean();
	// cartP.setProduct(menuEt);
	// cart.getDetails().add(cartP);
	// }
	// processor.process(cart, "fr");
	// System.out.println("-----------------------------");
	// System.out.println(cart.getTotal());
	// context = orderCriteriaChecker.check(c, cart);
	// Assert.assertTrue(context.isValid());
	// }
	//
	// //@Test
	// @Transactional
	// public void testTotalTo() throws Exception {
	// OrderCriteriaRulesBean rules = new
	// OrderCriteriaRulesBean().put(OrderCriteriaType.TotalAmount,
	// new
	// OrderCriteriaRuleBean().setEnable(true).setHasToAmount(true).setToAmount(10d));
	// rules = soaOrder.save(rules);
	// OrderCriterias c = daoOrderCriterias.loadById(rules.getId());
	// //
	// CartBean cart = new CartBean();
	// cart.setType(OrderType.InPlace);
	// cart.setRestaurant(test.getAllRestaurants().get(TestScenarios.RESTO1));
	// // Total => 10
	// for (int i = 0; i < 1; i++) {
	// CartProductBean cartP = new CartProductBean();
	// cartP.setProduct(menuEt);
	// cart.getDetails().add(cartP);
	// }
	// processor.process(cart, "fr");
	// System.out.println("-----------------------------");
	// System.out.println(cart.getTotal());
	// //
	// OrderCriteriaContext context = orderCriteriaChecker.check(c, cart);
	// Assert.assertTrue(context.isValid());
	// // Total =>0
	// cart.getDetails().clear();
	// processor.process(cart, "fr");
	// System.out.println("-----------------------------");
	// System.out.println(cart.getTotal());
	// context = orderCriteriaChecker.check(c, cart);
	// Assert.assertTrue(context.isValid());
	// // Total => 20
	// for (int i = 0; i < 2; i++) {
	// CartProductBean cartP = new CartProductBean();
	// cartP.setProduct(menuEt);
	// cart.getDetails().add(cartP);
	// }
	// processor.process(cart, "fr");
	// System.out.println("-----------------------------");
	// System.out.println(cart.getTotal());
	// context = orderCriteriaChecker.check(c, cart);
	// Assert.assertFalse(context.isValid());
	// }
}
