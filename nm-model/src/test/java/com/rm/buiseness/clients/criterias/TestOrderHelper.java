package com.rm.buiseness.clients.criterias;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rm.contract.clients.beans.ClientForm;
import com.rm.contract.orders.beans.old.CartBean;
import com.rm.contract.orders.beans.old.CartProductBean;
import com.rm.contract.orders.constants.OrderStateType;
import com.rm.contract.orders.exceptions.InvalidCartException;
import com.rm.contract.prices.constants.OrderType;
import com.rm.contract.products.views.ProductViewDto;
import com.rm.contract.restaurants.beans.ShopViewDto;
import com.rm.dao.orders.DaoOrder;
import com.rm.model.orders.Order;
import com.rm.model.orders.OrderState;
import com.rm.soa.orders.SoaOrder;
import com.rm.utils.dao.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
@Transactional
public class TestOrderHelper {

	@Autowired
	private SoaOrder soaOrder;
	@Autowired
	private DaoOrder daoOrder;
	protected Log log = LogFactory.getLog(getClass());

	public void deleteAllOrders() {
		daoOrder.flush();
		for (Order o : daoOrder.findAll()) {
			daoOrder.delete(o);
		}
	}

	public Collection<Order> addOrder(ClientForm client,ProductViewDto menu, ShopViewDto resto, int fin, int times) throws JsonProcessingException, NoDataFoundException,
			InvalidCartException {
		Collection<Order> orders = new ArrayList<Order>();
		for (int cpts = 0; cpts < fin; cpts++) {
			CartBean cart = new CartBean();
			cart.setClient(client);
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
			OrderState state = new OrderState();
			state.setType(OrderStateType.Paid);
			order.add(state);
			daoOrder.update(order);
			daoOrder.flush();
			orders.add(order);
		}
		return orders;
	}

}
