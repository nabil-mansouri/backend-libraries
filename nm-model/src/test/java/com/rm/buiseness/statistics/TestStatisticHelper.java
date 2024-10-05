package com.rm.buiseness.statistics;

import java.util.List;

import org.joda.time.MutableDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rm.contract.orders.beans.old.CartBean;
import com.rm.contract.orders.beans.old.CartProductBean;
import com.rm.contract.orders.constants.TransactionStateType;
import com.rm.contract.orders.exceptions.InvalidCartException;
import com.rm.contract.prices.constants.OrderType;
import com.rm.contract.products.views.ProductViewDto;
import com.rm.contract.restaurants.beans.ShopViewDto;
import com.rm.dao.orders.DaoOrder;
import com.rm.model.orders.Order;
import com.rm.model.orders.TransactionState;
import com.rm.soa.orders.SoaOrder;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@Transactional
public class TestStatisticHelper {

	@Autowired
	private SoaOrder soaOrder;
	@Autowired
	private DaoOrder daoOrder;

	public void deleteAllOrders() {
		daoOrder.flush();
		for (Order o : daoOrder.findAll()) {
			daoOrder.delete(o);
		}
	}

	public void addOrder(ProductViewDto menu, ShopViewDto resto, List<MutableDateTime> dates, int times) throws JsonProcessingException,
			NoDataFoundException, InvalidCartException {
		for (MutableDateTime d : dates) {
			CartBean cart = new CartBean();
			cart.getClient().setIgnore(true);
			cart.setType(OrderType.InPlace);
			cart.setRestaurant(resto);
			for (int i = 0; i < times; i++) {
				CartProductBean cartP = new CartProductBean();
				cartP.setProduct(menu);
				cart.getDetails().add(cartP);
			}
			//
			soaOrder.save(cart, "fr");
			//
			daoOrder.flush();
			Order order = daoOrder.loadById(cart.getIdOrder());
			TransactionState state = new TransactionState();
			state.setCreated(d.toDate());
			state.setType(TransactionStateType.Commit);
			order.getTransaction().add(state);
			daoOrder.update(order);
			daoOrder.flush();
		}

	}

	public void addOrder(ProductViewDto menu, ShopViewDto resto, List<MutableDateTime> dates, List<Integer> times)
			throws JsonProcessingException, NoDataFoundException, InvalidCartException {
		for (int cpts = 0; cpts < dates.size(); cpts++) {
			MutableDateTime d = dates.get(cpts);
			CartBean cart = new CartBean();
			cart.getClient().setIgnore(true);
			cart.setType(OrderType.InPlace);
			cart.setRestaurant(resto);
			for (int i = 0; i < times.get(cpts); i++) {
				CartProductBean cartP = new CartProductBean();
				cartP.setProduct(menu);
				cart.getDetails().add(cartP);
			}
			//
			soaOrder.save(cart, "fr");
			//
			daoOrder.flush();
			Order order = daoOrder.loadById(cart.getIdOrder());
			TransactionState state = new TransactionState();
			state.setCreated(d.toDate());
			state.setType(TransactionStateType.Commit);
			order.getTransaction().add(state);
			daoOrder.update(order);
			daoOrder.flush();
		}

	}

	public void addOrder(List<ProductViewDto> products, ShopViewDto resto, List<Integer> times) throws JsonProcessingException,
			NoDataFoundException, InvalidCartException {
		CartBean cart = new CartBean();
		cart.getClient().setIgnore(true);
		cart.setType(OrderType.InPlace);
		cart.setRestaurant(resto);
		for (int i = 0; i < products.size(); i++) {
			ProductViewDto prod = products.get(i);
			for (int j = 0; j < times.get(i); j++) {
				CartProductBean cartP = new CartProductBean();
				cartP.setProduct(prod);
				cart.getDetails().add(cartP);
			}
		}
		//
		soaOrder.save(cart, "fr");
		//
		daoOrder.flush();
		Order order = daoOrder.loadById(cart.getIdOrder());
		TransactionState state = new TransactionState();
		state.setType(TransactionStateType.Commit);
		order.getTransaction().add(state);
		daoOrder.update(order);
		daoOrder.flush();
	}
}
