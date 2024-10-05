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
public class TestOrderCriteriaCumulable {

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
	// private SoaDiscount soaDiscount;
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
	// public void testIsCumulable() throws Exception {
	// DiscountFormBean discountBean = new DiscountFormBean().setName("PROMO1")
	// .addRule(new
	// DiscountRuleBean().setOperation(true).setOperationType(DiscountOperationType.Fixe)
	// .setOperationValue(10d).setSubject(new
	// DiscountRuleSubjectBean().setAllProducts(true)));
	// discountBean = soaDiscount.save(discountBean, "fr");
	// DiscountFormBean discountBean1 = new DiscountFormBean().setName("PROMO1")
	// .addRule(new
	// DiscountRuleBean().setOperation(true).setOperationType(DiscountOperationType.Fixe)
	// .setOperationValue(10d).setSubject(new
	// DiscountRuleSubjectBean().setAllProducts(true)));
	// discountBean1 = soaDiscount.save(discountBean1, "fr");
	// //
	// assertNotNull(discountBean.getId());
	// //
	// OrderCriteriaRulesBean rules = new
	// OrderCriteriaRulesBean().put(OrderCriteriaType.Cumulable,
	// new OrderCriteriaRuleBean().setEnable(true).setHasCumulable(true)
	// .setCumulable(OrderCriteriaCumulable.Cumulable));
	// rules = soaOrder.save(rules);
	// OrderCriterias c = daoOrderCriterias.loadById(rules.getId());
	// //
	// CartBean cart = new CartBean();
	// cart.setType(OrderType.InPlace);
	// cart.getDiscounts().add(discountBean);
	// cart.getDiscounts().add(discountBean1);
	// OrderCriteriaContext context = orderCriteriaChecker.check(c, cart);
	// Assert.assertTrue(context.isValid());
	// }
	//
	// //@Test
	// @Transactional
	// public void testIsNotCumulable() throws Exception {
	// DiscountFormBean discountBean = new DiscountFormBean().setName("PROMO1")
	// .addRule(new
	// DiscountRuleBean().setOperation(true).setOperationType(DiscountOperationType.Fixe)
	// .setOperationValue(10d).setSubject(new
	// DiscountRuleSubjectBean().setAllProducts(true)));
	// discountBean = soaDiscount.save(discountBean, "fr");
	// DiscountFormBean discountBean1 = new DiscountFormBean().setName("PROMO1")
	// .addRule(new
	// DiscountRuleBean().setOperation(true).setOperationType(DiscountOperationType.Fixe)
	// .setOperationValue(10d).setSubject(new
	// DiscountRuleSubjectBean().setAllProducts(true)));
	// discountBean1 = soaDiscount.save(discountBean1, "fr");
	// //
	// assertNotNull(discountBean.getId());
	// //
	// OrderCriteriaRulesBean rules = new
	// OrderCriteriaRulesBean().put(OrderCriteriaType.Cumulable,
	// new OrderCriteriaRuleBean().setEnable(true).setHasCumulable(true)
	// .setCumulable(OrderCriteriaCumulable.NotCumulable));
	// rules = soaOrder.save(rules);
	// OrderCriterias c = daoOrderCriterias.loadById(rules.getId());
	// //
	// CartBean cart = new CartBean();
	// cart.setType(OrderType.InPlace);
	// cart.getDiscounts().add(discountBean);
	// cart.getDiscounts().add(discountBean1);
	// OrderCriteriaContext context = orderCriteriaChecker.check(c, cart);
	// Assert.assertFalse(context.isValid());
	// }

}
