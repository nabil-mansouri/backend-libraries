package com.nm.orders.soa;

import java.util.ArrayList;
import java.util.Collection;

import com.nm.contract.orders.beans.old.OrderCriteriaRulesBean;
import com.nm.orders.constants.OrderOptions;
import com.nm.orders.contracts.OrderAdapter;
import com.nm.orders.dao.DaoOrderAccount;
import com.nm.orders.dao.QueryOrderBuilder;
import com.nm.orders.dtos.OrderDto;
import com.nm.orders.dtos.OrderFilterDto;
import com.nm.orders.dtos.impl.OrderRequestDtoImpl;
import com.nm.orders.exceptions.OrderManagementException;
import com.nm.orders.models.Order;
import com.nm.orders.models.criterias.OrderCriterias;
import com.nm.orders.workflow.OrderStateComputerChain;
import com.nm.orders.workflow.OrderStateContext;
import com.nm.paiments.PaimentException;
import com.nm.paiments.daos.DaoTransactionSubject;
import com.nm.payment.soa.SoaPaiment;
import com.nm.utils.dtos.DtoConverter;
import com.nm.utils.dtos.DtoConverterRegistry;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.NoDataFoundException;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/**
 * 
 * @author Nabil
 * 
 */
public class SoaOrderImpl implements SoaOrder {

	private DaoOrderAccount daoAccount;
	private DtoConverterRegistry registry;
	private DaoTransactionSubject daoTransactionSubject;
	private SoaPaiment soaPaiment;

	public void setDaoAccount(DaoOrderAccount daoAccount) {
		this.daoAccount = daoAccount;
	}

	public void setDaoTransactionSubject(DaoTransactionSubject daoTransactionSubject) {
		this.daoTransactionSubject = daoTransactionSubject;
	}

	public void setRegistry(DtoConverterRegistry registry) {
		this.registry = registry;
	}

	public void setSoaPaiment(SoaPaiment soaPaiment) {
		this.soaPaiment = soaPaiment;
	}

	public OrderCriteriaRulesBean save(OrderCriteriaRulesBean dto) throws NoDataFoundException {
		OrderCriterias criterias = new OrderCriterias();
		IGenericDao<OrderCriterias, Long> daoOrderCriterias = AbstractGenericDao.get(OrderCriterias.class);
		if (dto.getId() != null) {
			criterias = daoOrderCriterias.loadById(dto.getId());
		}
		// TODO
		// criterias = orderConverter.convert(criterias, dto);
		daoOrderCriterias.saveOrUpdate(criterias);
		dto.setId(criterias.getId());
		return dto;
	}

	public OrderDto getOrCreate(OrderDto orderDto, OrderAdapter adapter, OptionsList options)
			throws OrderManagementException {
		try {
			options.put(OrderOptions.KEY_ADAPTER, adapter);
			DtoConverter<OrderDto, Order> converter = registry.search(orderDto, Order.class);
			//
			Order order = converter.toEntity(orderDto, options);
			if (order.getBuyer().getId() == null) {
				daoAccount.saveOrUpdate(order.getBuyer());
			}
			if (order.getSeller().getId() == null) {
				daoAccount.saveOrUpdate(order.getSeller());
			}
			AbstractGenericDao.get(Order.class).saveOrUpdate(order);
			orderDto.setIdOrder(order.getId());
			orderDto.getBuyer().setId(order.getBuyer().getId());
			orderDto.getSeller().setId(order.getSeller().getId());
			//
			try {
				soaPaiment.changeSubject(orderDto.getTransaction(), orderDto, adapter.paimentAdapter());
			} catch (PaimentException e) {
				soaPaiment.getOrCreate(orderDto, adapter.paimentAdapter());
			}
			return orderDto;
		} catch (Exception e) {
			throw new OrderManagementException(e);
		}
	}

	public Collection<OrderDto> fetch(OrderFilterDto filter, OrderAdapter adapter, OptionsList options)
			throws OrderManagementException {
		try {
			options.put(OrderOptions.KEY_ADAPTER, adapter);
			DtoConverter<OrderDto, Order> converter = registry.search(adapter.dtoClass(), Order.class);
			Collection<Order> orders = AbstractGenericDao.get(Order.class)
					.find(QueryOrderBuilder.get().withFilter(filter));
			Collection<OrderDto> dtos = new ArrayList<OrderDto>();
			for (Order o : orders) {
				dtos.add(converter.toDto(o, options));
			}
			return dtos;
		} catch (Exception e) {
			throw new OrderManagementException(e);
		}
	}

	public Collection<OrderDto> fetchReferences(OrderFilterDto filter, OrderAdapter adapter)
			throws OrderManagementException {
		try {
			DtoConverter<OrderDto, Order> converter = registry.search(adapter.dtoClass(), Order.class);
			Collection<Order> orders = AbstractGenericDao.get(Order.class)
					.find(QueryOrderBuilder.get().withFilter(filter).withProjectionUUIDAndId(true));
			Collection<OrderDto> dtos = new ArrayList<OrderDto>();
			OptionsList options = new OptionsList().withOption(OrderOptions.OnlyReferences);
			options.put(OrderOptions.KEY_ADAPTER, adapter);
			for (Order o : orders) {
				dtos.add(converter.toDto(o, options));
			}
			return dtos;
		} catch (Exception e) {
			throw new OrderManagementException(e);
		}
	}

	public OrderDto operation(OrderDto dto, OrderRequestDtoImpl request, OrderAdapter adapter, OptionsList options)
			throws OrderManagementException {
		try {
			options.put(OrderOptions.KEY_ADAPTER, adapter);
			Order order = AbstractGenericDao.get(Order.class).loadById(dto.getIdOrder());
			OrderStateContext context = new OrderStateContext().setOrder(order).setOrderAdapter(adapter)
					.setOperation(request);
			OrderStateComputerChain.process(context);
			DtoConverter<OrderDto, OrderStateContext> converter = registry.search(dto, OrderStateContext.class);
			return converter.toDto(dto, context, options);
		} catch (Exception e) {
			throw new OrderManagementException(e);
		}
	}

	public Order remove(OrderDto dto) throws NoDataFoundException {
		IGenericDao<Order, Long> daoOrder = AbstractGenericDao.get(Order.class);
		Order order = daoOrder.loadById(dto.getIdOrder());
		daoOrder.refresh(order);
		daoTransactionSubject.delete(order.getTransaction());
		daoOrder.flush();
		daoOrder.delete(order);
		return order;
	}

	//
	// @Transactional()
	// public OrderViewBean save(CartBean cart, String lang)
	// throws NoDataFoundException, JsonProcessingException,
	// InvalidCartException {
	// try {
	// soaCart.save(cart);
	// } catch (Exception e) {
	// cart.setNotExists(true);
	// throw new InvalidCartException(cart);
	// }
	// // Check everytime if can save
	// soaCart.check(cart, lang);
	// if (!cart.getCanSubmit()) {
	// throw new InvalidCartException(cart);
	// }
	// Order order = new Order();
	// order.setUuid(UUID.randomUUID().toString().toUpperCase().replaceAll("-",
	// ""));
	// // Begin order only if this is new
	// transactionManager.beginOrder(cart, order);
	// if (cart.getIdOrder() != null) {
	// order = daoOrder.loadById(cart.getIdOrder());
	// order.getDetails().clear();
	// }
	// if (cart.getClient().isIgnore()) {
	// order.setClient(null);
	// } else {
	// soaClient.save(cart.getClient());
	// order.setClient(daoClient.loadById(cart.getClient().getId()));
	// }
	// orderConverter.convert(order, cart);
	// daoOrder.saveOrUpdate(order);
	// // Update cart
	// cart.setIdOrder(order.getId());
	// // DO NOT DELETE NOW (wait pay)
	// soaCart.save(cart);
	// OrderViewBean bean = this.orderConverter.convert(order, lang, new
	// ArrayList<ModelOptions>());
	// bean.setCart(cart);
	// return bean;
	// }
	//
	//
}
