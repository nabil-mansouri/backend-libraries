package com.nm.orders.dtos.converters;

import java.util.Collection;

import com.nm.contract.orders.beans.old.CartBean;
import com.nm.contract.orders.beans.old.OrderCriteriaRulesBean;
import com.nm.contract.orders.beans.old.OrderStateBean;
import com.nm.orders.dtos.impl.OrderViewDtoImpl;
import com.nm.orders.models.Order;
import com.nm.orders.models.OrderState;
import com.nm.orders.models.criterias.OrderCriterias;
import com.nm.utils.dtos.ModelOptions;
import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
public class OldOrderConverterImpl {
	// TODO
	// @Autowired
	// private ProductInstanceConverter productConverter;
	// @Autowired
	// private DaoRestaurant daoRestaurant;
	// @Autowired
	// private RestaurantConverter restaurantConverter;
	// @Autowired
	// private TransactionComputer transactionComputer;
	// @Autowired
	// private ClientConverter clientConverter;
	// @Autowired
	// private StateRulesProcessor stateRulesProcessor;

	// @Transactional(readOnly = true)
	// public OrderCriterias convert(OrderCriterias criterias,
	// OrderCriteriaRulesBean bean) {
	// criterias.getRules().clear();
	// for (OrderCriteriaType t : bean.getRules().keySet()) {
	// OrderCriteriaRuleBean ruleBean = bean.getRules().get(t);
	// if (ruleBean.isEnable()) {
	// OrderCriteriaRules rules = new OrderCriteriaRules();
	// criterias.put(t, rules);
	// switch (t) {
	// case CountProducts:
	// if (ruleBean.isHasFromQty() || ruleBean.isHasToQty()) {
	// OrderCriteriaRuleRange rule = new OrderCriteriaRuleRange();
	// if (ruleBean.isHasFromQty()) {
	// rule.setFrom(ruleBean.getFromQty());
	// }
	// if (ruleBean.isHasToQty()) {
	// rule.setTo(ruleBean.getToQty());
	// }
	// rules.add(rule);
	// }
	// break;
	// case HavingProducts:
	// if (ruleBean.isHasProductRule()) {
	// for (OrderCriteriaProductRuleBean p : ruleBean.getProductId()) {
	// OrderCriteriaRuleProduct ruleP = new OrderCriteriaRuleProduct();
	// ruleP.setFrom(p.getFromQty());
	// ruleP.getProduct().addAll(p.getProductIds());
	// ruleP.setTo(p.getToQty());
	// rules.add(ruleP);
	// }
	// }
	// break;
	// case OrderType:
	// if (ruleBean.isHasType()) {
	// OrderCriteriaRuleType rule = new OrderCriteriaRuleType();
	// rule.setOperator(ruleBean.getTypeOp());
	// rule.getTypes().addAll(ruleBean.getTypes());
	// rules.add(rule);
	// }
	// break;
	// case Restaurant:
	// if (ruleBean.isHasRestaurantIds()) {
	// OrderCriteriaRuleShop rule = new OrderCriteriaRuleShop();
	// rule.setOperator(ruleBean.getRestaurantIdOp());
	// rule.getRestaurant().addAll(ruleBean.getRestaurantIds());
	// rules.add(rule);
	// }
	// break;
	// case TotalAmount:
	// if (ruleBean.isHasFromAmount() || ruleBean.isHasToAmount()) {
	// OrderCriteriaRuleRange rule = new OrderCriteriaRuleRange();
	// if (ruleBean.isHasFromAmount()) {
	// rule.setFrom(ruleBean.getFromAmount());
	// }
	// if (ruleBean.isHasToAmount()) {
	// rule.setTo(ruleBean.getToAmount());
	// }
	// rules.add(rule);
	// }
	// break;
	// case Cumulable:
	// if (ruleBean.isHasCumulable()) {
	// OrderCriteriaRuleCumulable rule = new OrderCriteriaRuleCumulable();
	// rule.setCumulable(ruleBean.getCumulable());
	// rules.add(rule);
	// }
	// break;
	// }
	// }
	// }
	// return criterias;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rm.soa.orders.converters.OrderConverter#convert(com.rm.model.orders
	 * .Order, com.rm.contract.orders.beans.CartBean)
	 */
	// @Transactional(readOnly = true)
	// public Order convert(Order order, CartBean bean) throws
	// NoDataFoundException {
	// order.setOrderType(bean.getType());
	// order.setIdCart(bean.getIdCart());
	// Restaurant restaurant =
	// daoRestaurant.loadById(bean.getRestaurant().getId());
	// order.setRestaurant(restaurant);
	// order.setTotal(bean.getTotal());
	// for (CartProductBean cart : bean.getDetails()) {
	// ProductInstance instance = productConverter.convert(cart.getProduct());
	// order.getDetails().add(instance);
	// }
	// return order;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.rm.soa.orders.converters.OrderConverter#convert(com.rm.model.orders
	 * .Order, java.lang.String)
	 */
	// @Transactional(readOnly = true)
	// public OrderViewBean convert(Order order, String lang,
	// Collection<ModelOptions> options)
	// throws NoDataFoundException {
	// OrderViewBean bean = new OrderViewBean();
	// bean.setIdOrder(order.getId());
	// bean.setId(order.getId());
	// bean.setType(order.getOrderType());
	// bean.setRestaurant(restaurantConverter.toDto(order.getRestaurant(), new
	// OptionsList(lang)));
	// bean.setTotal(order.getTotal());
	// bean.setUuid(order.getUuid());
	// bean.setCreated(order.getCreated());
	// //
	// if (options.contains(OrderOptions.Products)) {
	// for (ProductInstance instance : order.getDetails()) {
	// ProductInstanceDto instanceView = productConverter.convert(instance,
	// lang, options);
	// bean.getDetails().add(instanceView);
	// }
	// }
	// if (options.contains(OrderOptions.Cart)) {
	// CartBean cart = new CartBean();
	// cart.setType(order.getOrderType());
	// cart.setRestaurant(restaurantConverter.toDto(order.getRestaurant(), new
	// OptionsList(lang)));
	// for (ProductInstance instance : order.getDetails()) {
	// CartProductBean cartProductBean = new CartProductBean();
	// cartProductBean.setProduct(convert(instance, lang));
	// cart.getDetails().add(cartProductBean);
	// }
	// bean.setCart(cart);
	// }
	// //
	// bean.setLastState(convert(order.getLastState()));
	// for (OrderState state : order.getStates()) {
	// bean.getStates().add(convert(state));
	// }
	// //
	// bean.setTransaction(convert(order.getTransaction()));
	// if (order.getClient() != null) {
	// bean.setClient(clientConverter.convert(order.getClient(), options));
	// } else {
	// bean.getClient().setIgnore(true);
	// }
	// // MUST BE AFTER ALL
	// if (options.contains(OrderOptions.NextStates)) {
	// try {
	// stateRulesProcessor.nextStates(bean);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// return bean;
	// }

	// protected ProductViewDto convert(ProductInstance instance, String lang) {
	// ProductViewDto def = new ProductViewDto();
	// def.setId(instance.getIdProduct());
	// def.img(instance.getImage());
	// def.name(instance.getName(lang));
	// def.setPrice(instance.getPrice());
	// for (IngredientInstance ing : instance.getExcluded()) {
	// IngredientViewDto ingB = new IngredientViewDto();
	// ingB.setName(ing.getName(lang));
	// ingB.setImg(ing.getImage());
	// def.getExcluded().add(ingB);
	// }
	// for (ProductInstancePart part : instance.getParts()) {
	// ProductPartViewDto partBean = new ProductPartViewDto();
	// partBean.setId(part.getIdPart());
	// partBean.setMandatory(part.getMandatory());
	// partBean.setSelected(convert(part.getChild(), lang));
	// // partBean.add(partBean.getSelected());
	// // def.add(partBean);
	// }
	// return def;
	// }

	public OrderStateBean convert(OrderState state) {
		OrderStateBean bean = new OrderStateBean();
		bean.setCreated(state.getCreated());
		bean.setState(state.getType());
		return bean;
	}

	public OrderCriterias convert(OrderCriterias client, OrderCriteriaRulesBean bean) {
		// TODO Auto-generated method stub
		return null;
	}

	public Order convert(Order order, CartBean bean) throws NoDataFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	public OrderViewDtoImpl convert(Order order, String lang, Collection<ModelOptions> options)
			throws NoDataFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
