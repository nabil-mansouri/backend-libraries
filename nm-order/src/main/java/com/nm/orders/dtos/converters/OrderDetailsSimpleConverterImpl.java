package com.nm.orders.dtos.converters;

import java.util.ArrayList;
import java.util.Collection;

import com.nm.orders.dtos.impl.OrderDetailsDtoDefault;
import com.nm.orders.models.OrderDetailsSimple;
import com.nm.utils.dtos.DtoConvertException;
import com.nm.utils.dtos.DtoConverterDefault;
import com.nm.utils.dtos.OptionsList;
import com.nm.utils.hibernate.impl.AbstractGenericDao;

/***
 * 
 * @author nabilmansouri
 *
 */
public class OrderDetailsSimpleConverterImpl extends DtoConverterDefault<OrderDetailsDtoDefault, OrderDetailsSimple> {

	public OrderDetailsDtoDefault toDto(OrderDetailsDtoDefault dto, OrderDetailsSimple entity, OptionsList options)
			throws DtoConvertException {
		dto.setId(entity.getId());
		dto.setDetails(entity.getDetail());
		return dto;
	}

	public OrderDetailsDtoDefault toDto(OrderDetailsSimple entity, OptionsList options) throws DtoConvertException {
		return toDto(new OrderDetailsDtoDefault(), entity, options);
	}

	public Collection<OrderDetailsDtoDefault> toDto(Collection<OrderDetailsSimple> entity, OptionsList options)
			throws DtoConvertException {
		Collection<OrderDetailsDtoDefault> all = new ArrayList<OrderDetailsDtoDefault>();
		for (OrderDetailsSimple a : entity) {
			all.add(toDto(a, options));
		}
		return all;
	}

	public OrderDetailsSimple toEntity(OrderDetailsDtoDefault dto, OptionsList options) throws DtoConvertException {
		try {
			OrderDetailsSimple entity = new OrderDetailsSimple();
			if (dto.getId() != null) {
				entity = AbstractGenericDao.get(OrderDetailsSimple.class).get(dto.getId());
			}
			entity.setAmount(dto.getAmount());
			entity.setDetail(dto.getDetails());
			return entity;
		} catch (Exception e) {
			throw new DtoConvertException(e);
		}
	}

}
