package com.nm.orders.dtos.converters;

import com.nm.orders.dtos.impl.OrderViewDtoImpl;
import com.nm.orders.models.Order;
import com.nm.orders.models.TransactionSubjectOrder;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.IGenericDao;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/***
 * 
 * @author nabilmansouri
 *
 */
public class TransactionSubjectOrderConverterImpl
		extends DtoConverterDefault<OrderViewDtoImpl, TransactionSubjectOrder> {

	public OrderViewDtoImpl toDto(TransactionSubjectOrder entity, OptionsList options) throws DtoConvertException {
		return toDto(new OrderViewDtoImpl(), entity, options);
	}

	public TransactionSubjectOrder toEntity(OrderViewDtoImpl dto, OptionsList options) throws DtoConvertException {
		try {
			IGenericDao<Order, Long> daoOrder = AbstractGenericDao.get(Order.class);
			daoOrder.flush();
			Order order = daoOrder.get(dto.getId());
			daoOrder.refresh(order);
			if (order.getTransaction() == null) {
				TransactionSubjectOrder entity = new TransactionSubjectOrder();
				entity.setOrder(order);
				return entity;
			} else {
				return order.getTransaction();
			}
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

	public OrderViewDtoImpl toDto(OrderViewDtoImpl dto, TransactionSubjectOrder entity, OptionsList options)
			throws DtoConvertException {
		dto.setId(entity.getOrder().getId());
		return dto;
	}

}
