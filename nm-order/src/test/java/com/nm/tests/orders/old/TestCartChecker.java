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
public class TestCartChecker {
	// TODO
	// @Autowired
	// private SoaShopConfiguration soaShopConfiguration;
	// @Autowired
	// private SoaPlanning soaPlanning;
	// @Autowired
	// private CartCheckerProcessor processor;
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
	// public void testCheckAndSyncPrice() throws Exception {
	// ProductViewDto menu =
	// test.getAllProductsView().get(TestScenarios.MENU_MOYEN);
	// boolean setted = false;
	// // TODO
	// // for (ProductPartViewDto part : menu.parts()) {
	// // part.setSelected(part.getProducts().get(0));
	// // if (part.getName().equals(TestScenarios.Dessert)) {
	// // for (ProductViewDto p : part.getProducts()) {
	// // if (p.name().startsWith("Chocolat")) {
	// // setted = true;
	// // part.setSelected(p);
	// // }
	// // }
	// // }
	// // }
	// assertTrue(setted);
	// //
	// CartBean cart = new CartBean();
	// cart.setType(OrderType.InPlace);
	// cart.setRestaurant(test.getAllRestaurants().get(TestScenarios.RESTO1));
	// for (int i = 0; i < 2; i++) {
	// CartProductBean cartP = new CartProductBean();
	// cartP.setProduct(menu);
	// cart.getDetails().add(cartP);
	// }
	// processor.process(cart, "fr");
	// //
	// assertFalse(cart.getDetails().get(0).getContext().isInError());
	// assertFalse(cart.getDetails().get(0).getContext().isInWarning());
	// System.out.println(cart.getDetails().get(0).getProduct().getPrice());
	// System.out.println(cart.getTotal());
	// assertEquals(10d,
	// cart.getDetails().get(0).getProduct().getPrice().doubleValue(), 0);
	// assertEquals(12.5d, cart.getDetails().get(0).getSubTotal().doubleValue(),
	// 0);
	// assertEquals(25d, cart.getTotal(), 0);
	// // Change type
	// cart.setType(OrderType.Delivered);
	// processor.process(cart, "fr");
	// //
	// assertFalse(cart.getDetails().get(0).getContext().isInError());
	// assertFalse(cart.getDetails().get(0).getContext().isInWarning());
	// System.out.println(cart.getDetails().get(0).getProduct().getPrice());
	// System.out.println(cart.getTotal());
	// assertEquals(15d,
	// cart.getDetails().get(0).getProduct().getPrice().doubleValue(), 0);
	// assertEquals(18.5d, cart.getDetails().get(0).getSubTotal().doubleValue(),
	// 0);
	// assertEquals(37d, cart.getTotal(), 0);
	// assertFalse(cart.isEmpty());
	// }
	//
	// //@Test
	// @Transactional
	// public void testCheckAndSyncPriceWithDefautOrderType() throws Exception {
	// ProductViewDto potatoes =
	// test.getAllProductsView().get(TestScenarios.POTATOES);
	// //
	// CartBean cart = new CartBean();
	// cart.setType(OrderType.InPlace);
	// cart.setRestaurant(test.getAllRestaurants().get(TestScenarios.RESTO1));
	// for (int i = 0; i < 2; i++) {
	// CartProductBean cartP = new CartProductBean();
	// cartP.setProduct(potatoes);
	// cart.getDetails().add(cartP);
	// }
	// processor.process(cart, "fr");
	// //
	// ProductCheckerContext cc = cart.getDetails().get(0).getContext();
	// assertFalse(cc.isInError());
	// assertFalse(cc.isInWarning());
	// System.out.println(cart.getDetails().get(0).getProduct().getPrice());
	// System.out.println(cart.getTotal());
	// assertEquals(1.75d,
	// cart.getDetails().get(0).getProduct().getPrice().doubleValue(), 0);
	// assertEquals(1.75d, cart.getDetails().get(0).getSubTotal().doubleValue(),
	// 0);
	// assertEquals(3.5d, cart.getTotal(), 0);
	// // Change type
	// cart.setType(OrderType.Delivered);
	// processor.process(cart, "fr");
	// //
	// assertFalse(cart.getDetails().get(0).getContext().isInError());
	// assertFalse(cart.getDetails().get(0).getContext().isInWarning());
	// System.out.println(cart.getDetails().get(0).getProduct().getPrice());
	// System.out.println(cart.getTotal());
	// assertEquals(1.75d,
	// cart.getDetails().get(0).getProduct().getPrice().doubleValue(), 0);
	// assertEquals(1.75d, cart.getDetails().get(0).getSubTotal().doubleValue(),
	// 0);
	// assertEquals(3.5d, cart.getTotal(), 0);
	// }
	//
	// //@Test
	// @Transactional
	// public void testUnavailableProduct() throws Exception {
	// ProductViewDto potatoes =
	// test.getAllProductsView().get(TestScenarios.CHOCOLAT);
	// //
	// CartBean cart = new CartBean();
	// CartProductBean cartP = new CartProductBean();
	// cartP.setProduct(potatoes);
	// cart.getDetails().add(cartP);
	// cart.setType(OrderType.OutPlace);
	// cart.setRestaurant(test.getAllRestaurants().get(TestScenarios.RESTO1));
	// processor.process(cart, "fr");
	// //
	// ProductCheckerContext cc = cart.getDetails().get(0).getContext();
	// assertTrue(cc.isInError());
	// assertTrue(cc.isUnavailable());
	// // Change type
	// cart.setType(OrderType.InPlace);
	// cart.setRestaurant(test.getAllRestaurants().get(TestScenarios.RESTO1));
	// processor.process(cart, "fr");
	// //
	// cc = cart.getDetails().get(0).getContext();
	// assertFalse(cc.isInError());
	// assertFalse(cc.isUnavailable());
	// }
	//
	// //@Test
	// @Transactional
	// public void testChangePriceByRestaurant() throws Exception {
	// ProductViewDto tacos =
	// test.getAllProductsView().get(TestScenarios.TACOS);
	// //
	// CartBean cart = new CartBean();
	// CartProductBean cartP = new CartProductBean();
	// cartP.setProduct(tacos);
	// cart.getDetails().add(cartP);
	// cart.setType(OrderType.OutPlace);
	// cart.setRestaurant(test.getAllRestaurants().get(TestScenarios.RESTO1));
	// processor.process(cart, "fr");
	// //
	// ProductCheckerContext cc = cart.getDetails().get(0).getContext();
	// assertFalse(cc.isInError());
	// assertFalse(cc.isUnavailable());
	// assertEquals(5d, cart.getDetails().get(0).getSubTotal(), 0d);
	// // Change type
	// cart.setType(OrderType.InPlace);
	// cart.setRestaurant(test.getAllRestaurants().get(TestScenarios.RESTO2));
	// processor.process(cart, "fr");
	// //
	// cc = cart.getDetails().get(0).getContext();
	// assertFalse(cc.isInError());
	// assertFalse(cc.isUnavailable());
	// assertEquals(6d, cart.getDetails().get(0).getSubTotal(), 0d);
	// }
	//
	// //@Test
	// @Transactional
	// public void testRestaurantIsOpen() throws Exception {
	// soaShopConfiguration.setBoolean(ShopConfigType.RestaurantClosedCanBuy,
	// false);
	// ShopViewDto resto = test.getAllRestaurants().get(TestScenarios.RESTO1);
	// PlanningBean planning = soaPlanning.getOrCreate(resto.getId(),
	// PlanningType.Restaurant);
	// // Le restaurant est ouvert et on ne peut commander que pendant les
	// // ouvertures
	// MutableDateTime start = new MutableDateTime();
	// start.addMinutes(-5);
	// MutableDateTime end = new MutableDateTime();
	// end.addMinutes(2);
	// TimeSlotBean bean =
	// TimeSlotFormBuilder.get().withHasNoEndPlan(true).withPlanningDays(PlanningDays.AllDays)
	// .withType(EventType.Recurrent).withTypeOfSlot(SlotType.RestaurantOpen)
	// .withDateBeginHoraire(start.toDate()).withDateEndHoraire(end.toDate()).build();
	// soaPlanning.saveOrUpdate(planning.getId(), bean);
	// //
	// CartBean cart = new CartBean();
	// cart.setType(OrderType.OutPlace);
	// cart.setRestaurant(test.getAllRestaurants().get(TestScenarios.RESTO1));
	// processor.process(cart, "fr");
	// assertTrue(cart.getRestaurant().getState().isCanBuy());
	// //
	// bean =
	// TimeSlotFormBuilder.get().withHasNoEndPlan(true).withPlanningDays(PlanningDays.AllDays)
	// .withType(EventType.Recurrent).withTypeOfSlot(SlotType.RestaurantClose)
	// .withDateBeginHoraire(start.toDate()).withDateEndHoraire(end.toDate()).build();
	// soaPlanning.saveOrUpdate(planning.getId(), bean);
	// processor.process(cart, "fr");
	// assertFalse(cart.getRestaurant().getState().isCanBuy());
	// //
	// }
	//
	// //@Test
	// @Transactional
	// public void testRestaurantOpenEveryTime() throws Exception {
	// soaShopConfiguration.setBoolean(ShopConfigType.RestaurantClosedCanBuy,
	// true);
	// ShopViewDto resto = test.getAllRestaurants().get(TestScenarios.RESTO1);
	// PlanningBean planning = soaPlanning.getOrCreate(resto.getId(),
	// PlanningType.Restaurant);
	// // Le restaurant est ouvert et on peut commander tout le temp
	// MutableDateTime start = new MutableDateTime();
	// start.addMinutes(-5);
	// MutableDateTime end = new MutableDateTime();
	// end.addMinutes(2);
	// TimeSlotBean bean =
	// TimeSlotFormBuilder.get().withHasNoEndPlan(true).withPlanningDays(PlanningDays.AllDays)
	// .withType(EventType.Recurrent).withTypeOfSlot(SlotType.RestaurantOpen)
	// .withDateBeginHoraire(start.toDate()).withDateEndHoraire(end.toDate()).build();
	// soaPlanning.saveOrUpdate(planning.getId(), bean);
	// //
	// CartBean cart = new CartBean();
	// cart.setType(OrderType.OutPlace);
	// cart.setRestaurant(test.getAllRestaurants().get(TestScenarios.RESTO1));
	// processor.process(cart, "fr");
	// assertTrue(cart.getRestaurant().getState().isCanBuy());
	// // Le restaurant est ferm� et on peut commander tout le temp
	// bean =
	// TimeSlotFormBuilder.get().withHasNoEndPlan(true).withPlanningDays(PlanningDays.AllDays)
	// .withType(EventType.Recurrent).withTypeOfSlot(SlotType.RestaurantClose)
	// .withDateBeginHoraire(start.toDate()).withDateEndHoraire(end.toDate()).build();
	// soaPlanning.saveOrUpdate(planning.getId(), bean);
	// processor.process(cart, "fr");
	// assertTrue(cart.getRestaurant().getState().isCanBuy());
	// //
	// soaShopConfiguration.setBoolean(ShopConfigType.RestaurantClosedCanBuy,
	// false);
	// processor.process(cart, "fr");
	// assertFalse(cart.getRestaurant().getState().isCanBuy());
	// }
	//
	// //@Test
	// @Transactional
	// public void testRestaurantBuyBeforeOpen() throws Exception {
	// soaShopConfiguration.setBoolean(ShopConfigType.RestaurantClosedCanBuy,
	// false);
	// soaShopConfiguration.setInt(ShopConfigType.RestaurantClosedCanBuyTimeBefore,
	// 15);
	// ShopViewDto resto = test.getAllRestaurants().get(TestScenarios.RESTO1);
	// PlanningBean planning = soaPlanning.getOrCreate(resto.getId(),
	// PlanningType.Restaurant);
	// // Le restaurant ouvre dans 14 minutes et on peut commander jusqu'� 15
	// // minutes avant
	// MutableDateTime start = new MutableDateTime();
	// start.addMinutes(14);
	// MutableDateTime end = new MutableDateTime();
	// end.addMinutes(20);
	// TimeSlotBean bean =
	// TimeSlotFormBuilder.get().withHasNoEndPlan(true).withPlanningDays(PlanningDays.AllDays)
	// .withType(EventType.Recurrent).withTypeOfSlot(SlotType.RestaurantOpen)
	// .withDateBeginHoraire(start.toDate()).withDateEndHoraire(end.toDate()).build();
	// soaPlanning.saveOrUpdate(planning.getId(), bean);
	// //
	// CartBean cart = new CartBean();
	// cart.setType(OrderType.OutPlace);
	// cart.setRestaurant(test.getAllRestaurants().get(TestScenarios.RESTO1));
	// processor.process(cart, "fr");
	// assertTrue(cart.getRestaurant().getState().isCanBuy());
	// // Le restaurant ouvre dans 14 minutes et on peut commander jusqu'� 5
	// // minutes avant
	// soaShopConfiguration.setInt(ShopConfigType.RestaurantClosedCanBuyTimeBefore,
	// 5);
	// bean =
	// TimeSlotFormBuilder.get().withHasNoEndPlan(true).withPlanningDays(PlanningDays.AllDays)
	// .withType(EventType.Recurrent).withTypeOfSlot(SlotType.RestaurantClose)
	// .withDateBeginHoraire(start.toDate()).withDateEndHoraire(end.toDate()).build();
	// soaPlanning.saveOrUpdate(planning.getId(), bean);
	// processor.process(cart, "fr");
	// assertFalse(cart.getRestaurant().getState().isCanBuy());
	// //
	// }
	//
	// //@Test
	// @Transactional
	// public void testRestaurantBuyUntilOpen() throws Exception {
	// soaShopConfiguration.setBoolean(ShopConfigType.RestaurantClosedCanBuy,
	// false);
	// soaShopConfiguration.setInt(ShopConfigType.RestaurantClosedCanBuyTimeUntil,
	// 15);
	// ShopViewDto resto = test.getAllRestaurants().get(TestScenarios.RESTO1);
	// PlanningBean planning = soaPlanning.getOrCreate(resto.getId(),
	// PlanningType.Restaurant);
	// // Le restaurant ferme dans 16 minutes et on peut commander jusqu'� 15
	// // minutes avant
	// MutableDateTime start = new MutableDateTime();
	// MutableDateTime end = new MutableDateTime();
	// end.addMinutes(16);
	// TimeSlotBean bean =
	// TimeSlotFormBuilder.get().withHasNoEndPlan(true).withPlanningDays(PlanningDays.AllDays)
	// .withType(EventType.Recurrent).withTypeOfSlot(SlotType.RestaurantOpen)
	// .withDateBeginHoraire(start.toDate()).withDateEndHoraire(end.toDate()).build();
	// soaPlanning.saveOrUpdate(planning.getId(), bean);
	// //
	// CartBean cart = new CartBean();
	// cart.setType(OrderType.OutPlace);
	// cart.setRestaurant(test.getAllRestaurants().get(TestScenarios.RESTO1));
	// processor.process(cart, "fr");
	// assertTrue(cart.getRestaurant().getState().isCanBuy());
	// // Le restaurant ferme dans 16 minutes et on peut commander jusqu'� 17
	// // minutes avant
	// soaShopConfiguration.setInt(ShopConfigType.RestaurantClosedCanBuyTimeUntil,
	// 17);
	// bean =
	// TimeSlotFormBuilder.get().withHasNoEndPlan(true).withPlanningDays(PlanningDays.AllDays)
	// .withType(EventType.Recurrent).withTypeOfSlot(SlotType.RestaurantClose)
	// .withDateBeginHoraire(start.toDate()).withDateEndHoraire(end.toDate()).build();
	// soaPlanning.saveOrUpdate(planning.getId(), bean);
	// processor.process(cart, "fr");
	// assertFalse(cart.getRestaurant().getState().isCanBuy());
	// //
	// }
	//
	// //@Test
	// @Transactional
	// public void testRestaurantBuyBeforeUntilOpen() throws Exception {
	// soaShopConfiguration.setBoolean(ShopConfigType.RestaurantClosedCanBuy,
	// false);
	// soaShopConfiguration.setInt(ShopConfigType.RestaurantClosedCanBuyTimeUntil,
	// 30);
	// soaShopConfiguration.setInt(ShopConfigType.RestaurantClosedCanBuyTimeBefore,
	// 15);
	// ShopViewDto resto = test.getAllRestaurants().get(TestScenarios.RESTO1);
	// PlanningBean planning = soaPlanning.getOrCreate(resto.getId(),
	// PlanningType.Restaurant);
	// // Le restaurant ouvre dans 10 min et ferme 45 minute
	// // On peut commander 15 minute avant et jusqu a 30 min avant fermeture
	// MutableDateTime start = new MutableDateTime();
	// start.addMinutes(10);
	// MutableDateTime end = new MutableDateTime();
	// end.addMinutes(45);
	// TimeSlotBean bean =
	// TimeSlotFormBuilder.get().withHasNoEndPlan(true).withPlanningDays(PlanningDays.AllDays)
	// .withType(EventType.Recurrent).withTypeOfSlot(SlotType.RestaurantOpen)
	// .withDateBeginHoraire(start.toDate()).withDateEndHoraire(end.toDate()).build();
	// soaPlanning.saveOrUpdate(planning.getId(), bean);
	// //
	// CartBean cart = new CartBean();
	// cart.setType(OrderType.OutPlace);
	// cart.setRestaurant(test.getAllRestaurants().get(TestScenarios.RESTO1));
	// processor.process(cart, "fr");
	// assertTrue(cart.getRestaurant().getState().isCanBuy());
	// // Le restaurant ouvre dans 10 min et ferme 45 minute
	// // On peut commander 15 minute avant et jusqu a 50 min avant fermeture
	// soaShopConfiguration.setInt(ShopConfigType.RestaurantClosedCanBuyTimeUntil,
	// 50);
	// bean =
	// TimeSlotFormBuilder.get().withHasNoEndPlan(true).withPlanningDays(PlanningDays.AllDays)
	// .withType(EventType.Recurrent).withTypeOfSlot(SlotType.RestaurantClose)
	// .withDateBeginHoraire(start.toDate()).withDateEndHoraire(end.toDate()).build();
	// soaPlanning.saveOrUpdate(planning.getId(), bean);
	// processor.process(cart, "fr");
	// assertFalse(cart.getRestaurant().getState().isCanBuy());
	//
	// // Le restaurant ouvre dans 10 min et ferme 45 minute
	// // On peut commander 5 minute avant et jusqu a 30 min avant fermeture
	// soaShopConfiguration.setInt(ShopConfigType.RestaurantClosedCanBuyTimeUntil,
	// 30);
	// soaShopConfiguration.setInt(ShopConfigType.RestaurantClosedCanBuyTimeBefore,
	// 5);
	// bean =
	// TimeSlotFormBuilder.get().withHasNoEndPlan(true).withPlanningDays(PlanningDays.AllDays)
	// .withType(EventType.Recurrent).withTypeOfSlot(SlotType.RestaurantClose)
	// .withDateBeginHoraire(start.toDate()).withDateEndHoraire(end.toDate()).build();
	// soaPlanning.saveOrUpdate(planning.getId(), bean);
	// processor.process(cart, "fr");
	// assertFalse(cart.getRestaurant().getState().isCanBuy());
	// }
	//
	// //@Test
	// @Transactional
	// public void testCartEmpty() throws Exception {
	// CartBean cart = new CartBean();
	// cart.setType(OrderType.OutPlace);
	// cart.setRestaurant(test.getAllRestaurants().get(TestScenarios.RESTO1));
	// processor.process(cart, "fr");
	// assertTrue(cart.isEmpty());
	// }
}
